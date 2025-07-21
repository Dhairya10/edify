package com.edify.learning.presentation.chat

import android.content.Context
import androidx.lifecycle.ViewModel
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
    
    fun sendMessage() {
        val input = _messageInput.value.trim()
        if (input.isBlank()) return
        
        viewModelScope.launch {
            try {
                // Add user message
                val userMessage = ChatMessage(
                    id = UUID.randomUUID().toString(),
                    sessionId = currentSessionId,
                    content = input,
                    isFromUser = true,
                    messageType = MessageType.TEXT
                )
                
                repository.insertChatMessage(userMessage)
                _messageInput.value = ""
                
                // Show typing indicator
                _uiState.value = _uiState.value.copy(isGemmaTyping = true)
                
                // Generate Gemma response
                val response = repository.generateGemmaResponse(
                    prompt = input,
                    context = contextContent,
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
                        _uiState.value = _uiState.value.copy(isGemmaTyping = false)
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isGemmaTyping = false,
                            error = error.message ?: "Failed to generate response"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGemmaTyping = false,
                    error = e.message ?: "Failed to send message"
                )
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
                
                // Show typing indicator
                _uiState.value = _uiState.value.copy(isGemmaTyping = true)
                
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
                        _uiState.value = _uiState.value.copy(isGemmaTyping = false)
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isGemmaTyping = false,
                            error = error.message ?: "Failed to generate explanation"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGemmaTyping = false,
                    error = e.message ?: "Failed to send explanation request"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isGemmaTyping: Boolean = false,
    val error: String? = null
)
