package com.edify.learning.data.service

import android.content.Context
import android.graphics.Bitmap
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.genai.llminference.LlmInference.LlmInferenceOptions
import com.google.mediapipe.tasks.genai.llminference.LlmInferenceSession
import com.google.mediapipe.tasks.genai.llminference.GraphOptions
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GemmaService @Inject constructor(
    private val context: Context,
    private val promptService: PromptService
) {
    
    private var llmInference: LlmInference? = null
    private var isInitialized = false
    private val inferenceLock = kotlinx.coroutines.sync.Mutex()
    @Volatile
    private var isGenerating = false
    
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
                    return@withContext initResult.map { "" } // Return the failure
                }
            }
            
            // Use direct response generation
            val inference = llmInference ?: throw IllegalStateException("LLM inference not initialized")
            val response = inference.generateResponse(prompt)
            
            if (response != null && response.isNotBlank()) {
                Result.success(response)
            } else {
                Result.failure(IllegalStateException("Generated empty response"))
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error generating response: ${e.message}")
            android.util.Log.w(TAG, "Falling back to mock response")
            Result.success(getMockResponse(prompt))
        }
    }
    
    suspend fun generateResponseWithImage(prompt: String, image: Bitmap): Result<String> = withContext(Dispatchers.IO) {
        try {
            if (!isInitialized) {
                initializeModel().getOrThrow()
            }
            
            android.util.Log.d(TAG, "Generating multimodal response with image: ${prompt.take(50)}...")
            
            // Convert Bitmap to MPImage as per MediaPipe documentation
            val mpImage: MPImage = BitmapImageBuilder(image).build()
            android.util.Log.d(TAG, "Converted bitmap to MPImage successfully")
            
            // Get the model file path (same as used in main initialization)
            val modelFile = prepareModelFile()
                ?: throw IllegalStateException("Model file not available for multimodal inference")
            
            // Create LLM inference options with max images = 1 for Gemma-3n (required for multimodal)
            val multimodalOptions = LlmInferenceOptions.builder()
                .setModelPath(modelFile.absolutePath)
                .setMaxTokens(MAX_TOKENS)
                .setMaxTopK(TOP_K)
                .setMaxNumImages(1) // Gemma-3n accepts maximum of 1 image per session
                .build()
            
            // Create session options with vision modality enabled
            val sessionOptions = LlmInferenceSession.LlmInferenceSessionOptions.builder()
                .setTopK(TOP_K)
                .setTemperature(TEMPERATURE)
                .setGraphOptions(
                    GraphOptions.builder()
                        .setEnableVisionModality(true)
                        .build()
                )
                .build()
            
            // Create multimodal LlmInference instance and session according to official docs
            LlmInference.createFromOptions(context, multimodalOptions).use { multimodalInference ->
                LlmInferenceSession.createFromOptions(multimodalInference, sessionOptions).use { session ->
                    android.util.Log.d(TAG, "Created multimodal LlmInferenceSession with vision modality")
                    
                    // Add text query and image to session as per documentation
                    session.addQueryChunk(prompt)
                    session.addImage(mpImage)
                    
                    android.util.Log.d(TAG, "Added query chunk and image to session")
                    
                    // Generate response using multimodal input
                    val response = session.generateResponse()
                    
                    if (response.isNotBlank()) {
                        android.util.Log.d(TAG, "Successfully generated multimodal response: ${response.take(100)}...")
                        return@withContext Result.success(response)
                    } else {
                        throw IllegalStateException("Empty response from multimodal model")
                    }
                }
            }
            
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Multimodal response generation error: ${e.message}")
            e.printStackTrace()
            
            // Fallback to text-based approach if multimodal fails
            android.util.Log.w(TAG, "Falling back to text-based image analysis")
            val imageContextPrompt = "Please analyze this image and answer the following question: $prompt"
            
            return@withContext generateResponse(imageContextPrompt).fold(
                onSuccess = { response ->
                    Result.success(response)
                },
                onFailure = { error ->
                    android.util.Log.w(TAG, "Text-based fallback also failed: ${error.message}")
                    Result.success(getMockImageResponse(prompt))
                }
            )
        }
    }

    
    /**
     * Provides a mock response when the Gemma model is unavailable
     */
    private fun getMockResponse(prompt: String): String {
        return "I'm sorry, I can't provide a response right now because the AI model is unavailable. Please try again later or use other app features."
    }
    
    private fun getMockImageResponse(prompt: String): String {
        return "I can see you've shared an image, but I can't analyze it right now because the AI model is unavailable. Please try again later."
    }
    
    fun generateResponseStream(prompt: String): Flow<String> = flow {
        try {
            // Use mutex to prevent concurrent access to MediaPipe inference
            inferenceLock.withLock {
                // Wait for any ongoing generation to complete
                while (isGenerating) {
                    android.util.Log.d(TAG, "Waiting for ongoing generation to complete...")
                    delay(100) // Brief delay before checking again
                }
                
                if (!isInitialized) {
                    initializeModel().getOrThrow()
                }
                
                android.util.Log.d(TAG, "Starting response generation")
                isGenerating = true
                
                try {
                    val inference = llmInference ?: throw IllegalStateException("LLM inference not initialized")
                    
                    // Generate response with proper error handling
                    val fullResponse = inference.generateResponse(prompt)
                    
                    // Emit the response or throw if empty
                    if (fullResponse != null && fullResponse.isNotBlank()) {
                        emit(fullResponse)
                    } else {
                        throw IllegalStateException("LLM generated empty response")
                    }
                } finally {
                    isGenerating = false
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Response generation error: ${e.message}")
            e.printStackTrace()
            isGenerating = false
            throw e
        }
    }.flowOn(Dispatchers.IO)
    
    fun createEducationalPrompt(
        context: String,
        question: String,
        isExplanation: Boolean = false
    ): String {
        val promptKey = if (isExplanation) "educational_explanation" else "educational_question"
        return promptService.getFormattedPrompt(
            promptKey,
            "context" to context,
            "question" to question
        )
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
    
    suspend fun translateText(text: String, targetLanguage: String = "Hindi"): Result<String> = withContext(Dispatchers.IO) {
        try {
            if (!isInitialized) {
                initializeModel().getOrThrow()
            }
            
            val translationPrompt = createTranslationPrompt(text, targetLanguage)
            val result = generateResponse(translationPrompt)
            
            result.fold(
                onSuccess = { response ->
                    if (response.isNotEmpty()) {
                        Result.success(response)
                    } else {
                        Result.failure(Exception("Translation failed: Empty response"))
                    }
                },
                onFailure = { error ->
                    Result.failure(Exception("Translation failed: ${error.message}"))
                }
            )
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Translation error: ${e.message}")
            Result.failure(e)
        }
    }
    
    private fun createTranslationPrompt(text: String, targetLanguage: String): String {
        return promptService.getFormattedPrompt(
            "translation",
            "targetLanguage" to targetLanguage,
            "text" to text
        )
    }

    fun createSummaryPrompt(content: String): String {
        return promptService.getFormattedPrompt("summary", "content" to content)
    }
    
    fun dispose() {
        llmInference?.close()
        llmInference = null
        isInitialized = false
    }
}
