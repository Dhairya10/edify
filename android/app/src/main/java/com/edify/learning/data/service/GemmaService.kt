package com.edify.learning.data.service

import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.genai.llminference.LlmInference.LlmInferenceOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GemmaService @Inject constructor(
    private val context: Context
) {
    
    private var llmInference: LlmInference? = null
    private var isInitialized = false
    
    companion object {
        // Custom Gemma model file used in this project
        private const val MODEL_FILENAME = "gemma-3n-E2B-it-int4.task" // Our specific model (smaller, faster)
        private const val MAX_TOKENS = 1024
        private const val TOP_K = 64
        private const val TEMPERATURE = 0.7f
        private const val RANDOM_SEED = 42
        private const val TAG = "GemmaService"
        
        // External storage paths to try
        private const val EXTERNAL_MODEL_DIR = "/data/local/tmp/llm/"
        private const val SDCARD_DOWNLOAD_DIR = "/sdcard/Download/"
        private const val SDCARD_MODELS_DIR = "/sdcard/Models/"
    }
    
    suspend fun initializeModel(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (isInitialized) return@withContext Result.success(Unit)
            
            android.util.Log.d(TAG, "Initializing Gemma model: $MODEL_FILENAME")
            
            // Try to find and copy model to app's internal storage
            val internalModelFile = prepareModelFile()
            if (internalModelFile == null) {
                android.util.Log.e(TAG, "Could not prepare model file for loading")
                return@withContext Result.failure(Exception("Model file not available"))
            }
            
            try {
                android.util.Log.d(TAG, "Using model at: ${internalModelFile.absolutePath} (${internalModelFile.length()} bytes)")
                
                // Verify file integrity before loading
                if (!verifyModelFile(internalModelFile)) {
                    android.util.Log.e(TAG, "Model file integrity check failed")
                    return@withContext Result.failure(Exception("Model file is corrupted"))
                }
                
                val options = LlmInferenceOptions.builder()
                    .setModelPath(internalModelFile.absolutePath)
                    .setMaxTokens(MAX_TOKENS)
                    .setMaxTopK(TOP_K)
                    .build()
                
                android.util.Log.d(TAG, "Creating LlmInference with verified model")
                llmInference = LlmInference.createFromOptions(context, options)
                isInitialized = true
                android.util.Log.d(TAG, "Gemma model successfully initialized")
                
                return@withContext Result.success(Unit)
            } catch (e: Exception) {
                android.util.Log.e(TAG, "Failed to load Gemma model: ${e.message}")
                android.util.Log.e(TAG, "Model initialization failed with error: ${e.message}")
                e.printStackTrace()
                return@withContext Result.failure(e)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error initializing model: ${e.message}")
            e.printStackTrace()
            return@withContext Result.failure(e)
        }
    }
    
    private suspend fun prepareModelFile(): java.io.File? = withContext(Dispatchers.IO) {
        // Create internal model directory
        val internalModelDir = java.io.File(context.filesDir, "models")
        if (!internalModelDir.exists()) {
            internalModelDir.mkdirs()
        }
        
        val internalModelFile = java.io.File(internalModelDir, MODEL_FILENAME)
        
        // If model already exists in internal storage and is valid, use it
        if (internalModelFile.exists()) {
            android.util.Log.d(TAG, "Found existing model in internal storage: ${internalModelFile.absolutePath} (${internalModelFile.length()} bytes)")
            if (verifyModelFile(internalModelFile)) {
                android.util.Log.d(TAG, "Using existing valid model in app storage")
                return@withContext internalModelFile
            } else {
                android.util.Log.w(TAG, "Existing model file is corrupted, deleting and will search for new one")
                internalModelFile.delete()
            }
        }
        
        // Option 1: Try to copy from assets (if bundled with app)
        try {
            context.assets.open(MODEL_FILENAME).use { assetStream ->
                android.util.Log.d(TAG, "Found model in assets, copying to internal storage")
                internalModelFile.outputStream().buffered().use { output ->
                    assetStream.copyTo(output)
                }
                if (verifyModelFile(internalModelFile)) {
                    android.util.Log.d(TAG, "Successfully copied model from assets")
                    return@withContext internalModelFile
                } else {
                    internalModelFile.delete()
                    android.util.Log.e(TAG, "Model from assets failed verification")
                }
            }
        } catch (e: Exception) {
            android.util.Log.d(TAG, "Model not found in assets: ${e.message}")
        }
        
        // Option 2: Try to find model in external locations and copy it
        // Prioritize app-specific directory (no permissions needed)
        val externalLocations = listOf(
            java.io.File("/sdcard/Android/data/${context.packageName}/files/", MODEL_FILENAME), // App-specific, no permissions needed
            java.io.File(EXTERNAL_MODEL_DIR, MODEL_FILENAME),
            java.io.File(SDCARD_MODELS_DIR, MODEL_FILENAME),
            java.io.File(SDCARD_DOWNLOAD_DIR, MODEL_FILENAME),
            java.io.File("/sdcard/", MODEL_FILENAME)
        )
        
        for (externalFile in externalLocations) {
            android.util.Log.d(TAG, "Checking location: ${externalFile.absolutePath}")
            android.util.Log.d(TAG, "  - Exists: ${externalFile.exists()}")
            android.util.Log.d(TAG, "  - Can read: ${externalFile.canRead()}")
            android.util.Log.d(TAG, "  - Size: ${if (externalFile.exists()) externalFile.length() else "N/A"}")
            
            if (externalFile.exists() && externalFile.canRead()) {
                android.util.Log.d(TAG, "Found model at: ${externalFile.absolutePath}")
                
                // For app-specific directory, try loading directly first (saves storage space)
                if (externalFile.absolutePath.contains("/Android/data/${context.packageName}/files/")) {
                    android.util.Log.d(TAG, "Using model directly from app-specific directory (no copy needed)")
                    if (verifyModelFile(externalFile)) {
                        return@withContext externalFile
                    } else {
                        android.util.Log.e(TAG, "Model file verification failed at ${externalFile.absolutePath}")
                    }
                }
                
                // For other locations, try to copy to internal storage
                try {
                    // Copy with integrity verification
                    copyFileWithVerification(externalFile, internalModelFile)
                    android.util.Log.d(TAG, "Successfully copied model to app storage")
                    return@withContext internalModelFile
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "Failed to copy model from ${externalFile.absolutePath}: ${e.message}")
                    
                    // If copy failed but file is accessible, try using it directly
                    if (verifyModelFile(externalFile)) {
                        android.util.Log.d(TAG, "Copy failed, but using model directly from: ${externalFile.absolutePath}")
                        return@withContext externalFile
                    }
                }
            } else {
                android.util.Log.d(TAG, "Model not accessible at: ${externalFile.absolutePath}")
            }
        }
        
        android.util.Log.e(TAG, "Model file not found in any expected location")
        android.util.Log.e(TAG, "To use the Gemma model, you can:")
        android.util.Log.e(TAG, "1. Bundle it in assets/models/ directory (recommended for production)")
        android.util.Log.e(TAG, "2. Place it in one of these accessible locations:")
        externalLocations.forEach { location ->
            android.util.Log.e(TAG, "   - ${location.absolutePath}")
        }
        return@withContext null
    }
    
    private fun verifyModelFile(file: java.io.File): Boolean {
        if (!file.exists() || !file.canRead()) {
            android.util.Log.e(TAG, "Model file does not exist or is not readable")
            return false
        }
        
        if (file.length() < 1000000) { // Model should be at least 1MB
            android.util.Log.e(TAG, "Model file is too small: ${file.length()} bytes")
            return false
        }
        
        // Check if it's a valid zip/task file by reading the header
        try {
            file.inputStream().use { input ->
                val header = ByteArray(4)
                val bytesRead = input.read(header)
                if (bytesRead == 4) {
                    // Check for ZIP signature (PK) or other expected signatures
                    val signature = String(header, 0, 2)
                    if (signature == "PK") {
                        android.util.Log.d(TAG, "Model file has valid ZIP signature")
                        return true
                    } else {
                        android.util.Log.d(TAG, "Model file signature: ${header.joinToString("") { "%02x".format(it) }}")
                        // For .task files, we might have different signatures
                        return true // Allow other formats for now
                    }
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error verifying model file: ${e.message}")
            return false
        }
        
        return false
    }
    
    private fun copyFileWithVerification(source: java.io.File, destination: java.io.File) {
        android.util.Log.d(TAG, "Copying model from ${source.absolutePath} to ${destination.absolutePath}")
        
        // Ensure destination directory exists
        destination.parentFile?.mkdirs()
        
        // Copy file in chunks to handle large files
        source.inputStream().buffered().use { input ->
            destination.outputStream().buffered().use { output ->
                val buffer = ByteArray(8192)
                var totalBytes = 0L
                var bytesRead: Int
                
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                    totalBytes += bytesRead
                }
                
                android.util.Log.d(TAG, "Copied $totalBytes bytes")
            }
        }
        
        // Verify the copy
        if (source.length() != destination.length()) {
            destination.delete()
            throw Exception("File copy verification failed: size mismatch (${source.length()} vs ${destination.length()})")
        }
        
        android.util.Log.d(TAG, "File copy verification successful")
    }
    
    private suspend fun initializeModelWithPath(modelFile: java.io.File): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (!modelFile.exists()) {
                android.util.Log.e(TAG, "Model file does not exist: ${modelFile.absolutePath}")
                return@withContext Result.failure(Exception("Model file does not exist: ${modelFile.absolutePath}"))
            }

            android.util.Log.d(TAG, "Initializing model from file: ${modelFile.absolutePath}")
            
            // Create options for the LlmInference
            val options = LlmInferenceOptions.builder()
                .setModelPath(modelFile.absolutePath) // Use the absolute path of the model file
                .setMaxTokens(MAX_TOKENS)
                .setMaxTopK(TOP_K)
//                .setTemperature(TEMPERATURE)
//                .setRandomSeed(RANDOM_SEED)
                .build()
            
            // Initialize LlmInference with the model
            llmInference = LlmInference.createFromOptions(context, options)
            isInitialized = true
            
            android.util.Log.d(TAG, "Gemma model initialized successfully from file")
            
            return@withContext Result.success(Unit)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error initializing model with path ${modelFile.absolutePath}: ${e.message}")
            e.printStackTrace()
            return@withContext Result.failure(e)
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
            
            // Use streaming approach and collect full response
            val fullResponse = StringBuilder()
            generateResponseStream(prompt).collect { chunk ->
                fullResponse.append(chunk)
            }
            
            val response = fullResponse.toString()
            if (response.isNotBlank()) {
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
            
            android.util.Log.d(TAG, "Starting response generation")
            
            val inference = llmInference
            if (inference != null) {
                try {
                    // Generate response on background thread (non-blocking for UI)
                    val fullResponse = inference.generateResponse(prompt)
                    
                    // Emit the complete response immediately when ready
                    if (fullResponse != null && fullResponse.isNotBlank()) {
                        emit(fullResponse)
                    } else {
                        emit(getMockResponse(prompt))
                    }
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "Error in response generation: ${e.message}")
                    emit(getMockResponse(prompt))
                }
            } else {
                emit(getMockResponse(prompt))
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Response generation error: ${e.message}")
            emit(getMockResponse(prompt))
        }
    }.flowOn(Dispatchers.IO)
    
    fun createEducationalPrompt(
        context: String,
        question: String,
        isExplanation: Boolean = false
    ): String {
        return if (isExplanation) {
            """
            You are a concise educational AI tutor. Explain clearly and briefly for mobile learning.
            
            Context: $context
            
            Explain: $question
            
            Requirements:
            - Keep response under 150 words
            - Use simple, clear language
            - Focus on key concepts only
            - Use bullet points if helpful
            """.trimIndent()
        } else {
            """
            You are a concise educational AI tutor. Answer briefly and clearly for mobile learning.
            
            Context: $context
            Question: $question
            
            Requirements:
            - Keep response under 100 words
            - Be direct and specific
            - Focus on the exact question asked
            - Use simple language suitable for students
            """.trimIndent()
        }
    }
    
    /**
     * Helper method to copy a file from source to destination
     */
    private fun copyFile(source: java.io.File, destination: java.io.File) {
        destination.parentFile?.mkdirs() // Make sure the directory exists
        source.inputStream().use { input ->
            destination.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }
    
    fun createSummaryPrompt(content: String): String {
        return """
        Create a brief summary for mobile learning. Be concise and focused.
        
        Content: $content
        
        Requirements:
        - Maximum 80 words
        - Use bullet points for key concepts
        - Include only essential information
        - Use simple, clear language
        - Focus on what students need to remember
        """.trimIndent()
    }
    
    fun dispose() {
        llmInference?.close()
        llmInference = null
        isInitialized = false
    }
}
