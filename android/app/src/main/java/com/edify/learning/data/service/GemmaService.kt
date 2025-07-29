package com.edify.learning.data.service

import android.content.Context
import android.graphics.Bitmap
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.genai.llminference.LlmInference.LlmInferenceOptions
import com.google.mediapipe.tasks.genai.llminference.LlmInferenceSession
import com.google.mediapipe.tasks.genai.llminference.GraphOptions
// TODO: Fix MediaPipe framework dependency issues
// import com.google.mediapipe.framework.image.BitmapImageBuilder
// import com.google.mediapipe.framework.image.MPImage
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
    private val context: Context
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
            
            // Use streaming approach and collect full response
            val fullResponse = StringBuilder()
            generateResponseStream(prompt).collect { chunk ->
                fullResponse.append(chunk)
            }
            
            val response = fullResponse.toString()
            if (response.isNotBlank()) {
                Result.success(response)
            } else {
                Result.failure(IllegalStateException("Generated empty response"))
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error generating response: ${e.message}")
            Result.failure(e)
        }
    }
    
    // TODO: Temporarily commented out due to MediaPipe framework dependency issues
    // Will be restored once BitmapImageBuilder and MPImage classes are available
    suspend fun generateResponseWithImage(prompt: String, image: Bitmap): Result<String> = withContext(Dispatchers.IO) {
        android.util.Log.w(TAG, "Image inference temporarily disabled due to MediaPipe framework issues")
        
        // Fallback to enhanced text-based approach
        val imageContextPrompt = createImageContextPrompt(prompt, image)
        
        return@withContext generateResponse(imageContextPrompt).fold(
            onSuccess = { response ->
                Result.success(response)
            },
            onFailure = { error ->
                android.util.Log.w(TAG, "Text-based fallback failed: ${error.message}")
                Result.success(getMockImageResponse(prompt))
            }
        )
    }
    
    /*
    // Original multimodal implementation - commented out due to dependency issues
    suspend fun generateResponseWithImage(prompt: String, image: Bitmap): Result<String> = withContext(Dispatchers.IO) {
        try {
            if (!isInitialized) {
                initializeModel().getOrThrow()
            }
            
            android.util.Log.d(TAG, "Generating multimodal response with image: ${prompt.take(50)}...")
            
            // Convert Bitmap to MPImage as per MediaPipe documentation
            val mpImage: MPImage = BitmapImageBuilder(image).build()
            android.util.Log.d(TAG, "Converted bitmap to MPImage successfully")
            
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
            
            // Create LLM inference options with max images = 1 for Gemma-3n
            val options = LlmInferenceOptions.builder()
                .setModelPath(llmInference?.let { "" } ?: throw IllegalStateException("Model not initialized"))
                .setMaxTokens(MAX_TOKENS)
                .setMaxTopK(TOP_K)
                .setMaxNumImages(1) // Gemma-3n accepts maximum of 1 image per session
                .build()
            
            // Use the existing llmInference instance with session-based approach
            llmInference?.let { inference ->
                LlmInferenceSession.createFromOptions(inference, sessionOptions).use { session ->
                    android.util.Log.d(TAG, "Created LlmInferenceSession with vision modality")
                    
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
            } ?: throw IllegalStateException("LLM inference not initialized")
            
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Multimodal response generation error: ${e.message}")
            e.printStackTrace()
            
            // Fallback to enhanced text-based approach if multimodal fails
            android.util.Log.w(TAG, "Falling back to enhanced text-based image analysis")
            val imageContextPrompt = createImageContextPrompt(prompt, image)
            
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
    */
    
    private fun createImageContextPrompt(originalPrompt: String, image: Bitmap): String {
        // Analyze basic image properties
        val imageInfo = "Image dimensions: ${image.width}x${image.height} pixels"
        val aspectRatio = String.format("%.2f", image.width.toFloat() / image.height.toFloat())
        val isLandscape = image.width > image.height
        val isPortrait = image.height > image.width
        val isSquare = Math.abs(image.width - image.height) < 50
        
        // Determine likely image type based on dimensions and user prompt
        val likelyContent = when {
            originalPrompt.contains("diagram", ignoreCase = true) -> "diagram or schematic"
            originalPrompt.contains("chart", ignoreCase = true) -> "chart or graph"
            originalPrompt.contains("equation", ignoreCase = true) -> "mathematical equation or formula"
            originalPrompt.contains("text", ignoreCase = true) -> "text or document"
            originalPrompt.contains("explain", ignoreCase = true) -> "educational content"
            isLandscape && image.width > 800 -> "screenshot or document"
            isPortrait && image.height > 1000 -> "mobile screenshot or document"
            else -> "image content"
        }
        
        return """
            I can see you've shared an image with the following properties:
            - Dimensions: ${image.width}x${image.height} pixels
            - Aspect ratio: $aspectRatio
            - Likely content type: $likelyContent
            
            Your question: "$originalPrompt"
            
            ${getContextualResponse(originalPrompt, likelyContent)}
            
            While I can't see the actual visual details, I can provide guidance based on your question and the image characteristics. Feel free to describe specific elements you see for more targeted help.
        """.trimIndent()
    }
    
    private fun getContextualResponse(prompt: String, likelyContent: String): String {
        return when {
            prompt.contains("explain", ignoreCase = true) -> {
                "I'd be happy to help explain the $likelyContent. While I can see you've shared an image, I can provide better explanations if you describe the key elements you'd like me to focus on."
            }
            prompt.contains("solve", ignoreCase = true) || prompt.contains("calculate", ignoreCase = true) -> {
                "I can help you solve problems! If this image contains equations, formulas, or numerical data, please type out the key information and I'll guide you through the solution step by step."
            }
            prompt.contains("diagram", ignoreCase = true) -> {
                "Diagrams are great learning tools! I can help explain concepts, processes, or relationships. Describe the main components or labels you see, and I'll provide detailed explanations."
            }
            prompt.contains("what", ignoreCase = true) -> {
                "I can help identify and explain concepts! Tell me what specific elements or text you see in the image, and I'll provide comprehensive information about them."
            }
            else -> {
                "I'm ready to help with your $likelyContent! Describe the key details you'd like assistance with, and I'll provide thorough explanations and guidance."
            }
        }
    }
    
    /**
     * Provides a mock response when the Gemma model is unavailable
     * This creates a more engaging user experience than just showing error messages
     */
    private fun getMockResponse(prompt: String): String {
        val promptLower = prompt.lowercase()
        
        // Check if this is a quest generation request expecting JSON response
        if (promptLower.contains("respond in json format") || 
            promptLower.contains("json format") ||
            promptLower.contains("curriculum designer") ||
            promptLower.contains("expert educator") ||
            promptLower.contains("learning coach")) {
            
            // Return a properly formatted JSON response for quest generation
            if (promptLower.contains("strategy") && (promptLower.contains("convergent") || promptLower.contains("divergent"))) {
                // Strategy selection mock response
                return """{"strategy": "Divergent", "chapter": "mock_chapter_id"}"""
            } else {
                // Quest generation mock response
                return """{"title": "Mock Quest - Model Unavailable", "questionText": "This is a placeholder quest generated because the Gemma model is not available. Please ensure the model file is properly installed."}"""
            }
        }
        
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
    
    private fun getMockImageResponse(prompt: String): String {
        // Provide contextual mock responses for image-based queries
        return when {
            prompt.contains("describe", ignoreCase = true) || prompt.contains("what", ignoreCase = true) -> {
                "I can see you've shared an image with me. While I can't analyze it right now due to the AI model being unavailable, I'd be happy to help once the Gemma model is properly configured. You can continue using other features of the app in the meantime."
            }
            prompt.contains("explain", ignoreCase = true) || prompt.contains("how", ignoreCase = true) -> {
                "I notice you've included an image for explanation. The visual analysis feature requires the Gemma model to be available. Please ensure the model file is properly installed, and then I'll be able to provide detailed explanations about your images."
            }
            prompt.contains("solve", ignoreCase = true) || prompt.contains("calculate", ignoreCase = true) -> {
                "I can see you've shared an image that might contain a problem to solve. Once the AI model is available, I'll be able to analyze images and help solve mathematical problems, read diagrams, and provide step-by-step solutions."
            }
            else -> {
                "I can see you've shared an image with your question. The image analysis feature is currently unavailable because the AI model needs to be properly configured. You can still use text-based chat and other app features while this is being resolved."
            }
        }
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
        return """
        Translate the following English text to $targetLanguage. Provide only the $targetLanguage translation without any additional text or explanations.
        
        English text: $text
        
        $targetLanguage translation:
        """.trimIndent()
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
