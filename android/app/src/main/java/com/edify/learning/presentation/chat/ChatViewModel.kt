package com.edify.learning.presentation.chat

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.ChatMessage
import com.edify.learning.data.model.ChatSession
import com.edify.learning.data.model.MessageType
import com.edify.learning.data.repository.LearningRepository
import com.edify.learning.data.util.ContentLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: LearningRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    
    private val _messageInput = MutableStateFlow("")
    val messageInput: StateFlow<String> = _messageInput.asStateFlow()
    
    private val _selectedImage = MutableStateFlow<Bitmap?>(null)
    val selectedImage: StateFlow<Bitmap?> = _selectedImage.asStateFlow()
    
    private var currentSessionId: String = ""
    private var currentChapterId: String = ""
    private var contextContent: String? = null
    
    fun initializeChat(chapterId: String, selectedText: String?) {
        currentChapterId = chapterId
        currentSessionId = UUID.randomUUID().toString()
        
        viewModelScope.launch {
            try {
                // Load chapter summary context from JSON files
                val chapterSummary = repository.getChapterSummaryForChat(chapterId)
                contextContent = if (selectedText != null) {
                    "$chapterSummary\n\nSelected Text: $selectedText"
                } else {
                    chapterSummary
                }
                
                // Load existing messages for this session
                repository.getChatMessages(currentSessionId).collect { messages ->
                    _uiState.value = _uiState.value.copy(messages = messages)
                }
                
                // If there's selected text, automatically start explanation
                selectedText?.let { text ->
                    sendExplanationRequest(text)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to initialize chat"
                )
            }
        }
    }
    
    fun onMessageInputChanged(input: String) {
        _messageInput.value = input
    }
    
    fun onImageSelected(image: Bitmap?) {
        _selectedImage.value = image
    }
    
    fun sendMessage() {
        val input = _messageInput.value.trim()
        val image = _selectedImage.value
        if (input.isBlank() && image == null) return
        
        viewModelScope.launch {
            try {
                // Save image if present and get path
                val imagePath = image?.let { bitmap ->
                    saveImageToInternalStorage(bitmap)
                }
                
                // Add user message
                val userMessage = ChatMessage(
                    id = UUID.randomUUID().toString(),
                    sessionId = currentSessionId,
                    content = input,
                    isFromUser = true,
                    messageType = if (image != null) MessageType.IMAGE else MessageType.TEXT,
                    attachmentPath = imagePath
                )
                
                repository.insertChatMessage(userMessage)
                _messageInput.value = ""
                _selectedImage.value = null // Clear selected image
                
                // Start enhanced loading with progress
                startLoadingWithProgress(isExplanation = false)
                
                // Generate Gemma response with image support
                val response = repository.generateGemmaResponseWithImage(
                    prompt = input,
                    context = contextContent,
                    image = image,
                    isExplanation = false
                )
                
                response.fold(
                    onSuccess = { gemmaResponse ->
                        val gemmaMessage = ChatMessage(
                            id = UUID.randomUUID().toString(),
                            sessionId = currentSessionId,
                            content = gemmaResponse,
                            isFromUser = false,
                            messageType = MessageType.TEXT
                        )
                        
                        repository.insertChatMessage(gemmaMessage)
                        stopLoading()
                    },
                    onFailure = { error ->
                        setError(error.message ?: "Failed to generate response")
                    }
                )
            } catch (e: Exception) {
                setError(e.message ?: "Failed to send message")
            }
        }
    }
    
    private fun sendExplanationRequest(selectedText: String) {
        viewModelScope.launch {
            try {
                // Add user message with selected text
                val userMessage = ChatMessage(
                    id = UUID.randomUUID().toString(),
                    sessionId = currentSessionId,
                    content = "Explain: \"$selectedText\"",
                    isFromUser = true,
                    messageType = MessageType.TEXT
                )
                
                repository.insertChatMessage(userMessage)
                
                // Start enhanced loading with progress for explanation
                startLoadingWithProgress(isExplanation = true)
                
                // Generate explanation
                val response = repository.generateGemmaResponse(
                    prompt = selectedText,
                    context = contextContent,
                    isExplanation = true
                )
                
                response.fold(
                    onSuccess = { explanation ->
                        val gemmaMessage = ChatMessage(
                            id = UUID.randomUUID().toString(),
                            sessionId = currentSessionId,
                            content = explanation,
                            isFromUser = false,
                            messageType = MessageType.TEXT
                        )
                        
                        repository.insertChatMessage(gemmaMessage)
                        stopLoading()
                    },
                    onFailure = { error ->
                        setError(error.message ?: "Failed to generate explanation")
                    }
                )
            } catch (e: Exception) {
                setError(e.message ?: "Failed to send explanation request")
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null, canRetry = false)
    }
    
    private fun startLoadingWithProgress(isExplanation: Boolean = false) {
        val loadingMessages = if (isExplanation) {
            listOf(
                "Analyzing the selected text...",
                "Understanding the context...",
                "Generating detailed explanation...",
                "Finalizing response..."
            )
        } else {
            listOf(
                "Processing your question...",
                "Thinking through the answer...",
                "Generating response...",
                "Almost ready..."
            )
        }
        
        _uiState.value = _uiState.value.copy(
            isGemmaTyping = true,
            loadingProgress = 0f,
            loadingMessage = loadingMessages[0],
            estimatedTimeRemaining = 0,
            error = null,
            canRetry = false
        )
        
        // Cycle through loading messages without time constraints
        viewModelScope.launch {
            val messageInterval = 8000L // Change message every 8 seconds
            var currentMessageIndex = 0
            
            while (_uiState.value.isGemmaTyping && currentMessageIndex < loadingMessages.size - 1) {
                delay(messageInterval)
                
                // Only update if still loading
                if (_uiState.value.isGemmaTyping) {
                    currentMessageIndex++
                    _uiState.value = _uiState.value.copy(
                        loadingMessage = loadingMessages[currentMessageIndex]
                    )
                }
            }
        }
    }
    
    private fun stopLoading() {
        _uiState.value = _uiState.value.copy(
            isGemmaTyping = false,
            loadingProgress = 0f,
            loadingMessage = "",
            estimatedTimeRemaining = 0
        )
    }
    
    private fun setError(message: String) {
        _uiState.value = _uiState.value.copy(
            isGemmaTyping = false,
            loadingProgress = 0f,
            loadingMessage = "",
            estimatedTimeRemaining = 0,
            error = message,
            canRetry = true
        )
    }
    
    private fun saveImageToInternalStorage(bitmap: Bitmap): String? {
        return try {
            val filename = "chat_image_${UUID.randomUUID()}.jpg"
            val file = File(context.filesDir, filename)
            
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
            
            file.absolutePath
        } catch (e: IOException) {
            android.util.Log.e("ChatViewModel", "Failed to save image: ${e.message}")
            null
        }
    }
    
    fun retryLastMessage() {
        val lastUserMessage = _uiState.value.messages.lastOrNull { it.isFromUser }
        if (lastUserMessage != null) {
            clearError()
            _messageInput.value = lastUserMessage.content
            sendMessage()
        }
    }
}

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isGemmaTyping: Boolean = false,
    val loadingProgress: Float = 0f,
    val loadingMessage: String = "",
    val estimatedTimeRemaining: Int = 0, // in seconds
    val error: String? = null,
    val canRetry: Boolean = false
)
