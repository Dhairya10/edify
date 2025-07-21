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
        private const val MODEL_FILENAME = "gemma_3n_1b_4bit.task" // Model file in assets
        private const val MODEL_DIR = "models"
        private const val MAX_TOKENS = 1024
        private const val TOP_K = 64
        private const val TEMPERATURE = 0.7f
        private const val RANDOM_SEED = 42
        private const val TAG = "GemmaService"
        
        // These are the expected locations for the Gemma model file
        private val POSSIBLE_MODEL_LOCATIONS = listOf(
            "assets/$MODEL_DIR/$MODEL_FILENAME", // In assets/models directory
            "assets/$MODEL_FILENAME",           // Directly in assets
            "/data/local/tmp/llm/$MODEL_FILENAME" // External storage location per documentation
        )
    }
    
    suspend fun initializeModel(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (isInitialized) return@withContext Result.success(Unit)
            
            try {
                android.util.Log.d(TAG, "Initializing Gemma model")
                
                // Try all potential locations for the model file
                val modelFile = try {
                    // First check if we already have it in the cache
                    val cacheFile = context.cacheDir.resolve(MODEL_FILENAME)
                    if (cacheFile.exists() && cacheFile.length() > 0) {
                        android.util.Log.d(TAG, "Found existing model in cache: ${cacheFile.absolutePath}")
                        cacheFile
                    } else {
                        // Try to copy from assets/models directory to cache
                        try {
                            android.util.Log.d(TAG, "Attempting to copy model from assets/$MODEL_DIR/$MODEL_FILENAME")
                            context.assets.open("$MODEL_DIR/$MODEL_FILENAME").use { input ->
                                cacheFile.outputStream().use { output -> 
                                    input.copyTo(output)
                                }
                            }
                            android.util.Log.d(TAG, "Successfully copied model from assets: ${cacheFile.length()} bytes")
                            cacheFile
                        } catch (modelDirEx: Exception) {
                            android.util.Log.e(TAG, "Failed to access model in assets/$MODEL_DIR: ${modelDirEx.message}")
                            
                            // Try to copy directly from assets root
                            try {
                                android.util.Log.d(TAG, "Attempting to copy model directly from assets/$MODEL_FILENAME")
                                context.assets.open(MODEL_FILENAME).use { input ->
                                    cacheFile.outputStream().use { output -> 
                                        input.copyTo(output)
                                    }
                                }
                                android.util.Log.d(TAG, "Successfully copied model from assets root: ${cacheFile.length()} bytes")
                                cacheFile
                            } catch (assetRootEx: Exception) {
                                android.util.Log.e(TAG, "Failed to access model in assets root: ${assetRootEx.message}")
                                
                                // Try external storage location per MediaPipe documentation
                                val externalFile = java.io.File("/data/local/tmp/llm/$MODEL_FILENAME")
                                if (externalFile.exists() && externalFile.length() > 0) {
                                    android.util.Log.d(TAG, "Found model in external storage: ${externalFile.absolutePath}")
                                    externalFile
                                } else {
                                    // Finally check app's files directory
                                    val filesFile = context.filesDir.resolve(MODEL_FILENAME)
                                    if (filesFile.exists() && filesFile.length() > 0) {
                                        android.util.Log.d(TAG, "Found model in files directory: ${filesFile.absolutePath}")
                                        filesFile
                                    } else {
                                        android.util.Log.e(TAG, "Model file not found in any location")
                                        throw Exception("Gemma model not found: Looked in assets/models, assets root, external storage, and files directory")
                                    }
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "Failed to locate model file: ${e.message}")
                    throw Exception("Failed to locate Gemma model file. Please add it to assets/models directory.", e)
                }
                
                android.util.Log.d(TAG, "Using model at: ${modelFile.absolutePath} (${modelFile.length()} bytes)")
                
                if (!modelFile.exists() || modelFile.length() == 0L) {
                    throw Exception("Model file exists check failed or file is empty")
                }
                
                // Create LLM options
                val options = LlmInferenceOptions.builder()
                    .setModelPath(modelFile.absolutePath)
                    .setMaxTokens(MAX_TOKENS)
                    .setMaxTopK(TOP_K)
                    .build()
                
                // Initialize LlmInference
                llmInference = LlmInference.createFromOptions(context, options)
                isInitialized = true
                android.util.Log.d(TAG, "Successfully initialized Gemma model")
                Result.success(Unit)
            } catch (e: Exception) {
                android.util.Log.e(TAG, "Failed to load Gemma model: ${e.message}")
                Result.failure(e)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error initializing model: ${e.message}")
            Result.failure(e)
        }
    }
    
    suspend fun generateResponse(prompt: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            if (!isInitialized) {
                val initResult = initializeModel()
                if (initResult.isFailure) {
                    android.util.Log.e(TAG, "Model initialization failed with error: ${initResult.exceptionOrNull()?.message}")
                    // Use mock responses instead when the model is not available
                    return@withContext Result.success(getMockResponse(prompt))
                }
            }
            
            val response = llmInference?.generateResponse(prompt)
            if (response != null) {
                Result.success(response)
            } else {
                // Use mock response as fallback
                Result.success(getMockResponse(prompt))
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error generating response: ${e.message}")
            // Provide a mock response instead of just an error message
            Result.success(getMockResponse(prompt))
        }
    }
    
    /**
     * Provides a mock response when the Gemma model is unavailable
     * This creates a more engaging user experience than just showing error messages
     */
    private fun getMockResponse(prompt: String): String {
        val promptLower = prompt.lowercase()
        
        // Check if this is a greeting or introduction
        if (promptLower.contains("hello") || promptLower.contains("hi") || 
            promptLower.contains("hey") || promptLower.startsWith("introduce")) {
            return "Hello! I'm the Edify learning assistant. While my AI capabilities are currently limited (model not found), I can still help with basic questions. Feel free to explore the app's other features like notes, highlighting, and reading educational content!"
        }
        
        // Check if asking about capabilities
        if (promptLower.contains("what can you do") || promptLower.contains("capabilities") || 
            promptLower.contains("help me with")) {
            return "Normally, I can explain concepts, answer questions about your learning materials, and help you understand difficult topics. However, my AI capabilities are currently limited as the Gemma model file is missing. The app developer needs to include this file in the assets/models directory for full functionality."
        }
        
        // Check for math/science related questions
        if (promptLower.contains("math") || promptLower.contains("equation") || 
            promptLower.contains("calculate") || promptLower.contains("science") || 
            promptLower.contains("physics")) {
            return "I'd love to help with your math or science question! Unfortunately, my AI capabilities are currently limited because the Gemma model file is not installed. Please try the exercises and learning materials available in the app instead, or check back later when the AI model is properly configured."
        }
        
        // Default response for other questions
        return "I'm sorry, I can't provide a detailed response at the moment because the AI model is missing from this device. The Gemma model file needs to be included in the app's assets/models directory. You can continue using other app features like reading content, making notes, and studying exercises while this issue is resolved."
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
