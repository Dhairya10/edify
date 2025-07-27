package com.edify.learning.data.service

import com.edify.learning.data.dao.ChatDao
import com.edify.learning.data.model.ChatMessage
import com.edify.learning.data.model.MessageType
import com.edify.learning.data.repository.LearningRepository
import com.edify.learning.data.repository.QuestRepository
import android.graphics.Bitmap
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.coroutineContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatResponseService @Inject constructor(
    private val repository: LearningRepository,
    private val questRepository: QuestRepository,
    private val chatDao: ChatDao,
    private val gemmaService: GemmaService
) {
    
    // Synchronization to prevent concurrent response generation
    private val responseGenerationMutex = Mutex()
    private var isGenerating = false
    
    // Track pending responses for UI state management
    private val pendingResponses = mutableMapOf<String, String>()
    
    /**
     * Generate chat response in background that survives navigation/lifecycle changes
     * Uses same robust pattern as QuestGenerationService
     */
    fun generateChatResponse(
        userMessage: ChatMessage,
        prompt: String,
        context: String?,
        image: Bitmap? = null,
        isExplanation: Boolean = false,
        onLoadingStateChange: ((Boolean) -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        // Use GlobalScope to ensure response generation survives navigation/lifecycle changes
        GlobalScope.launch(Dispatchers.IO) {
            // Check if response generation is already in progress for this message
            if (pendingResponses.containsKey(userMessage.id)) {
                println("CHAT_RESPONSE: Response already generating for message ${userMessage.id}, skipping...")
                return@launch
            }
            
            // Use mutex to ensure synchronized access to generation state
            responseGenerationMutex.withLock {
                isGenerating = true
                pendingResponses[userMessage.id] = "generating"
                
                // Notify UI of loading state change
                onLoadingStateChange?.invoke(true)
                
                println("CHAT_RESPONSE: Starting response generation for message ${userMessage.id}")
                try {
                    // Add timeout to prevent indefinite hanging (same as QuestGenerationService)
                    withTimeout(300000) { // 5 minute timeout
                        generateResponseInternal(userMessage, prompt, context, image, isExplanation)
                    }
                } catch (e: TimeoutCancellationException) {
                    println("CHAT_RESPONSE: Response generation timed out after 5 minutes")
                    handleResponseGenerationFailure(userMessage.id, "Response generation timed out", onError)
                } catch (e: CancellationException) {
                    println("CHAT_RESPONSE: Response generation was cancelled: ${e.message}")
                    // Don't treat cancellation as an error - it's intentional
                    cleanupPendingResponse(userMessage.id)
                } catch (e: Exception) {
                    println("CHAT_RESPONSE: Error in response generation: ${e.message}")
                    e.printStackTrace()
                    handleResponseGenerationFailure(userMessage.id, e.message ?: "Failed to generate response", onError)
                } finally {
                    isGenerating = false
                    onLoadingStateChange?.invoke(false)
                    println("CHAT_RESPONSE: Response generation process completed for message ${userMessage.id}")
                }
            }
        }
    }
    
    /**
     * Internal response generation logic with proper coroutine context checks
     */
    private suspend fun generateResponseInternal(
        userMessage: ChatMessage,
        prompt: String,
        context: String?,
        image: Bitmap?,
        isExplanation: Boolean
    ) {
        coroutineContext.ensureActive()
        
        // Generate response using repository
        val result = if (image != null) {
            repository.generateGemmaResponseWithImage(
                prompt = prompt,
                context = context,
                image = image,
                isExplanation = isExplanation
            )
        } else {
            repository.generateGemmaResponse(
                prompt = prompt,
                context = context,
                isExplanation = isExplanation
            )
        }
        
        coroutineContext.ensureActive()
        
        result.fold(
            onSuccess = { response ->
                // Create and save Gemma response message
                val gemmaMessage = ChatMessage(
                    id = UUID.randomUUID().toString(),
                    sessionId = userMessage.sessionId,
                    chapterId = userMessage.chapterId,
                    content = response,
                    isFromUser = false,
                    messageType = MessageType.TEXT
                )
                
                // Save response to database
                chatDao.insertMessage(gemmaMessage)
                
                // Clean up pending response tracking
                cleanupPendingResponse(userMessage.id)
                
                println("CHAT_RESPONSE: Successfully generated and saved response for message ${userMessage.id}")
            },
            onFailure = { error ->
                throw error
            }
        )
    }
    
    /**
     * Handle response generation failures gracefully
     */
    private fun handleResponseGenerationFailure(
        messageId: String,
        reason: String,
        onError: ((String) -> Unit)?
    ) {
        cleanupPendingResponse(messageId)
        onError?.invoke(reason)
        println("CHAT_RESPONSE: Response generation failed for message $messageId: $reason")
    }
    
    /**
     * Clean up pending response tracking
     */
    private fun cleanupPendingResponse(messageId: String) {
        pendingResponses.remove(messageId)
    }
    
    /**
     * Check if a response is currently being generated for a message
     */
    fun isResponsePending(messageId: String): Boolean {
        return pendingResponses.containsKey(messageId)
    }
    
    /**
     * Check if any response generation is currently in progress
     */
    fun isAnyResponseGenerating(): Boolean {
        return isGenerating
    }
    
    /**
     * Get all pending response message IDs
     */
    fun getPendingResponseIds(): Set<String> {
        return pendingResponses.keys.toSet()
    }
}
