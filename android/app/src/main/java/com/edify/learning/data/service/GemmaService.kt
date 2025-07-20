package com.edify.learning.data.service

import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.genai.llminference.LlmInference.LlmInferenceOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GemmaService @Inject constructor(
    private val context: Context
) {
    
    private var llmInference: LlmInference? = null
    private var isInitialized = false
    
    companion object {
        private const val MODEL_PATH = "/data/local/tmp/llm/gemma_3n_1b_4bit.task"
        private const val MAX_TOKENS = 1024
        private const val TOP_K = 64
        private const val TEMPERATURE = 0.7f
        private const val RANDOM_SEED = 42
    }
    
    suspend fun initializeModel(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (isInitialized) return@withContext Result.success(Unit)
            
            val options = LlmInferenceOptions.builder()
                .setModelPath(MODEL_PATH)
                .setMaxTokens(MAX_TOKENS)
                .setMaxTopK(TOP_K)
                .build()
            
            llmInference = LlmInference.createFromOptions(context, options)
            isInitialized = true
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun generateResponse(prompt: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            if (!isInitialized) {
                initializeModel().getOrThrow()
            }
            
            val response = llmInference?.generateResponse(prompt)
            if (response != null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Failed to generate response"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun generateResponseStream(prompt: String): Flow<String> = flow {
        try {
            if (!isInitialized) {
                initializeModel().getOrThrow()
            }
            
            // For streaming responses, we'll use the synchronous method for now
            // since the current MediaPipe API doesn't support setResultListener
            
            // For streaming, we would use generateResponseAsync
            // For now, let's use the synchronous method and emit the full response
            val response = llmInference?.generateResponse(prompt)
            if (response != null) {
                emit(response)
            }
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(Dispatchers.IO)
    
    fun createEducationalPrompt(
        context: String,
        question: String,
        isExplanation: Boolean = false
    ): String {
        return if (isExplanation) {
            """
            You are an educational AI assistant helping students understand academic content.
            
            Context: $context
            
            Please explain the following in a clear, educational manner suitable for students:
            $question
            
            Provide:
            1. A clear explanation
            2. Key concepts
            3. Examples if applicable
            4. Why this is important to understand
            
            Keep your response concise but comprehensive.
            """.trimIndent()
        } else {
            """
            You are an educational AI assistant. Help the student with their question about the following content:
            
            Context: $context
            
            Question: $question
            
            Provide a helpful, educational response that aids learning.
            """.trimIndent()
        }
    }
    
    fun createSummaryPrompt(content: String): String {
        return """
        Please provide a concise summary of the following educational content:
        
        $content
        
        Focus on:
        1. Main concepts
        2. Key points
        3. Important definitions
        4. Core principles
        
        Keep the summary clear and student-friendly.
        """.trimIndent()
    }
    
    fun dispose() {
        llmInference?.close()
        llmInference = null
        isInitialized = false
    }
}
