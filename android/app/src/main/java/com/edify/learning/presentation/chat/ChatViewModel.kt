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
import com.edify.learning.data.repository.QuestRepository
import com.edify.learning.data.repository.UserProfileRepository
import com.edify.learning.data.service.ChatResponseService
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
    private val repository: LearningRepository,
    private val questRepository: QuestRepository,
    private val userProfileRepository: UserProfileRepository,
    private val chatResponseService: ChatResponseService,
    @ApplicationContext private val context: Context
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    
    private val _messageInput = MutableStateFlow("")
    val messageInput: StateFlow<String> = _messageInput.asStateFlow()
    
    private val _selectedImage = MutableStateFlow<Bitmap?>(null)
    val selectedImage: StateFlow<Bitmap?> = _selectedImage.asStateFlow()
    
    private val _userInitial = MutableStateFlow("U")
    val userInitial: StateFlow<String> = _userInitial.asStateFlow()
    
    private var currentSessionId: String = ""
    private var currentChapterId: String = ""
    private var contextContent: String? = null
    private var currentSubject: String? = null
    
    fun initializeChat(chapterId: String, selectedText: String?) {
        currentChapterId = chapterId
        currentSessionId = UUID.randomUUID().toString() // Generate session ID for new messages
        
        // Load user profile data for personalization
        loadUserProfile()
        
        viewModelScope.launch {
            try {
                // Load chapter and subject information for subject-specific query classification
                val chapter = repository.getChapterById(chapterId)
                if (chapter != null) {
                    val subject = repository.getSubjectById(chapter.subjectId)
                    currentSubject = subject?.name?.lowercase()
                }
                
                // Load chapter summary context from JSON files
                val chapterSummary = repository.getChapterSummaryForChat(chapterId)
                contextContent = if (selectedText != null) {
                    "$chapterSummary\n\nSelected Text: $selectedText"
                } else {
                    chapterSummary
                }
                
                // Load existing messages for this chapter (not session-based)
                repository.getChatMessages(chapterId).collect { messages ->
                    _uiState.value = _uiState.value.copy(messages = messages)
                    
                    // Check if there's an ongoing response generation using the service
                    val lastMessage = messages.lastOrNull()
                    if (lastMessage?.isFromUser == true) {
                        // Check if we have a pending response for this message
                        val isResponsePending = chatResponseService.isResponsePending(lastMessage.id)
                        if (isResponsePending) {
                            // Show loading state for pending response with proper callbacks
                            startLoadingWithProgress()
                            
                            // Set up a monitoring coroutine to stop loading when response completes
                            viewModelScope.launch {
                                // Poll until the response is no longer pending
                                while (chatResponseService.isResponsePending(lastMessage.id)) {
                                    kotlinx.coroutines.delay(500) // Check every 500ms
                                }
                                // Response completed, stop loading
                                stopLoading()
                            }
                        }
                        // Note: We don't auto-generate responses for messages without responses
                        // This prevents duplicate response generation when returning to chat
                    }
                }
                
                // If there's selected text, pre-populate the input field with quoted text
                selectedText?.let { text ->
                    val quotedText = "❝ $text ❞\n\n"
                    _messageInput.value = quotedText
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
        
        // Pre-populate default prompt when image is selected
        if (image != null && _messageInput.value.trim().isEmpty()) {
            _messageInput.value = "Please explain what you see in this image"
        } else if (image == null && _messageInput.value.trim() == "Please explain what you see in this image") {
            // Clear default prompt if image is removed and input hasn't been modified
            _messageInput.value = ""
        }
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
                    chapterId = currentChapterId,
                    content = input,
                    isFromUser = true,
                    messageType = if (image != null) MessageType.IMAGE else MessageType.TEXT,
                    attachmentPath = imagePath
                )
                
                repository.insertChatMessage(userMessage)
                
                // Clear input and image
                _messageInput.value = ""
                _selectedImage.value = null
                
                // Use ChatResponseService for robust background response generation with subject-specific classification
                // Loading state is managed by the service callbacks
                chatResponseService.generateChatResponse(
                    userMessage = userMessage,
                    prompt = input,
                    context = contextContent,
                    subject = currentSubject,
                    image = image,
                    onLoadingStateChange = { isLoading ->
                        viewModelScope.launch {
                            if (isLoading) {
                                startLoadingWithProgress()
                            } else {
                                stopLoading()
                            }
                        }
                    },
                    onError = { errorMessage ->
                        viewModelScope.launch {
                            setError(errorMessage)
                        }
                    }
                )
            } catch (e: Exception) {
                setError(e.message ?: "Failed to send message")
            }
        }
    }
    
    fun sendExplanationRequest(selectedText: String) {
        // Pre-populate input field with formatted explanation request
        _messageInput.value = "❝ $selectedText ❞\n\nPlease explain this."
        
        // Use the generic sendMessage method
        sendMessage()
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
    
    private fun loadUserProfile() {
        viewModelScope.launch {
            try {
                val userName = userProfileRepository.getUserName()
                val initial = if (userName != "there" && userName.isNotBlank()) {
                    userName.first().uppercaseChar().toString()
                } else {
                    "U" // Default to "U" for User
                }
                _userInitial.value = initial
            } catch (e: Exception) {
                _userInitial.value = "U" // Fallback to default
            }
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
