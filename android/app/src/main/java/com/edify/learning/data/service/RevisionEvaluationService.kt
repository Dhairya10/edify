package com.edify.learning.data.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.edify.learning.data.dao.RevisionSubmissionDao
import com.edify.learning.data.model.RevisionSubmission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RevisionEvaluationService @Inject constructor(
    private val context: Context,
    private val gemmaService: GemmaService,
    private val promptService: PromptService,
        private val revisionSubmissionDao: RevisionSubmissionDao
) {

    companion object {
        private const val TAG = "RevisionEvaluationSvc"
    }
    
            suspend fun evaluateTextResponse(
        chapterId: String,
        questionIndex: Int,
        question: String,
        expectedAnswer: String,
                studentResponse: String
    ): Result<RevisionSubmission> = withContext(Dispatchers.IO) {
        Log.d(TAG, "evaluateTextResponse called for chapterId: $chapterId, questionIndex: $questionIndex")
        try {
            // Create initial submission record
            val submission = RevisionSubmission(
                chapterId = chapterId,
                questionIndex = questionIndex,
                question = question,
                expectedAnswer = expectedAnswer,
                userTextResponse = studentResponse,
                isEvaluated = false
            )
            
            val submissionId = revisionSubmissionDao.insertSubmission(submission)
            
            // Evaluate with Gemma
            val evaluationResult = evaluateWithGemma(
                question = question,
                expectedAnswer = expectedAnswer,
                studentResponse = studentResponse,
                isImageResponse = false
            )
            
            return@withContext evaluationResult.fold(
                onSuccess = { feedback ->
                    Log.d(TAG, "Gemma evaluation successful for text response submissionId: $submissionId")
                    // Update submission with evaluation results
                    revisionSubmissionDao.updateSubmissionEvaluation(
                        submissionId = submissionId,
                        feedback = feedback,
                        isEvaluated = true,
                        evaluatedAt = System.currentTimeMillis()
                    )
                    
                    val updatedSubmission = submission.copy(
                        id = submissionId,
                        gemmaFeedback = feedback,
                        isEvaluated = true,
                        evaluatedAt = System.currentTimeMillis()
                    )
                    
                    Result.success(updatedSubmission)
                },
                onFailure = { exception ->
                    Log.e(TAG, "Gemma evaluation failed for text response submissionId: $submissionId", exception)
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "Exception in evaluateTextResponse for chapterId $chapterId", e)
            Result.failure(e)
        }
    }
    
        suspend fun evaluateImageResponse(
        chapterId: String,
        questionIndex: Int,
        question: String,
        expectedAnswer: String,
                imageUri: String
    ): Result<RevisionSubmission> = withContext(Dispatchers.IO) {
        Log.d(TAG, "evaluateImageResponse called for chapterId: $chapterId, questionIndex: $questionIndex")
        try {
            // Create initial submission record
            val submission = RevisionSubmission(
                chapterId = chapterId,
                questionIndex = questionIndex,
                question = question,
                expectedAnswer = expectedAnswer,
                userImageUri = imageUri,
                isEvaluated = false
            )
            
            val submissionId = revisionSubmissionDao.insertSubmission(submission)
            
            // Load and process image
                        val bitmap = loadBitmapFromUri(imageUri)
            Log.d(TAG, "Loaded bitmap for submissionId: $submissionId")
            if (bitmap == null) {
                return@withContext Result.failure(Exception("Failed to load image from URI: $imageUri"))
            }
            
            // Evaluate with Gemma
            val evaluationResult = evaluateImageWithGemma(
                question = question,
                expectedAnswer = expectedAnswer,
                imageBitmap = bitmap
            )
            
            return@withContext evaluationResult.fold(
                onSuccess = { feedback ->
                    Log.d(TAG, "Gemma evaluation successful for image response submissionId: $submissionId")
                    // Update submission with evaluation results
                    revisionSubmissionDao.updateSubmissionEvaluation(
                        submissionId = submissionId,
                        feedback = feedback,
                        isEvaluated = true,
                        evaluatedAt = System.currentTimeMillis()
                    )
                    
                    val updatedSubmission = submission.copy(
                        id = submissionId,
                        gemmaFeedback = feedback,
                        isEvaluated = true,
                        evaluatedAt = System.currentTimeMillis()
                    )
                    
                    Result.success(updatedSubmission)
                },
                onFailure = { exception ->
                    Log.e(TAG, "Gemma evaluation failed for image response submissionId: $submissionId", exception)
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "Exception in evaluateImageResponse for chapterId $chapterId", e)
            Result.failure(e)
        }
    }
    
    private suspend fun evaluateWithGemma(
        question: String,
        expectedAnswer: String,
        studentResponse: String,
        isImageResponse: Boolean
    ): Result<String> {
        return try {
            val promptKey = if (isImageResponse) "revision_image_evaluation" else "revision_text_evaluation"
            val prompt = promptService.getFormattedPrompt(
                promptKey,
                "question" to question,
                "expectedAnswer" to expectedAnswer,
                "studentResponse" to studentResponse
            )
            
            val gemmaResult = gemmaService.generateResponse(prompt)
            
            return gemmaResult.fold(
                onSuccess = { response ->
                    // Return the response directly as it's now plain text
                    Result.success(response.trim())
                },
                onFailure = { _ ->
                    // Fallback evaluation if Gemma fails
                    val fallbackFeedback = createFallbackEvaluation(studentResponse)
                    Result.success(fallbackFeedback)
                }
            )
        } catch (e: Exception) {
            // Create fallback evaluation
            val fallbackFeedback = createFallbackEvaluation(studentResponse)
            Result.success(fallbackFeedback)
        }
    }
    
    private suspend fun evaluateImageWithGemma(
        question: String,
        expectedAnswer: String,
        imageBitmap: Bitmap
    ): Result<String> {
        return try {
            val prompt = promptService.getFormattedPrompt(
                "revision_image_evaluation",
                "question" to question,
                "expectedAnswer" to expectedAnswer
            )
            
            val gemmaResult = gemmaService.generateResponseWithImage(prompt, imageBitmap)
            
            return gemmaResult.fold(
                onSuccess = { response ->
                    // Return the response directly as it's now plain text
                    Result.success(response.trim())
                },
                onFailure = { _ ->
                    // Fallback evaluation for image
                    val fallbackFeedback = "Thank you for submitting your handwritten work! Your effort shows dedication to learning. Please review the expected answer to ensure you've covered all key points."
                    Result.success(fallbackFeedback)
                }
            )
        } catch (e: Exception) {
            val fallbackFeedback = "Thank you for submitting your handwritten work! Your effort shows dedication to learning. Please review the expected answer to ensure you've covered all key points."
            Result.success(fallbackFeedback)
        }
    }
    
    private fun createFallbackEvaluation(studentResponse: String): String {
        return when {
            studentResponse.length < 20 -> "Your response shows effort, but could benefit from more detail. Try to elaborate on your key points and provide specific examples."
            studentResponse.length > 100 -> "Good work on your response! You've shown understanding of the topic. Review the expected answer to see if you missed any important points."
            else -> "Good work on your response! You've shown understanding of the topic. Review the expected answer to see if you missed any important points."
        }
    }
    
    private fun loadBitmapFromUri(imageUri: String): Bitmap? {
        return try {
            val file = File(imageUri)
            if (file.exists()) {
                BitmapFactory.decodeFile(imageUri)
            } else {
                // Try as URI
                val uri = Uri.parse(imageUri)
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream?.use { stream ->
                    BitmapFactory.decodeStream(stream)
                }
            }
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun getRevisionHistory(chapterId: String, exerciseIndex: Int): List<RevisionSubmission> {
        return try {
            revisionSubmissionDao.getSubmissionsForQuestion(chapterId, exerciseIndex)
                .first() // Convert Flow to List by taking the first emission
        } catch (e: Exception) {
            emptyList()
        }
    }
    
        suspend fun processAllPendingEvaluations() {
        Log.d(TAG, "Starting processAllPendingEvaluations")
                val pendingSubmissions = revisionSubmissionDao.getPendingEvaluations()
        Log.d(TAG, "Found ${pendingSubmissions.size} pending submissions to process.")
        
        pendingSubmissions.forEach { submission ->
            try {
                val feedback = if (submission.userTextResponse != null) {
                    evaluateWithGemma(
                        question = submission.question,
                        expectedAnswer = submission.expectedAnswer,
                        studentResponse = submission.userTextResponse,
                        isImageResponse = false
                    ).getOrElse { createFallbackEvaluation(submission.userTextResponse) }
                } else if (submission.userImageUri != null) {
                    val bitmap = loadBitmapFromUri(submission.userImageUri)
                    if (bitmap != null) {
                        evaluateImageWithGemma(
                            question = submission.question,
                            expectedAnswer = submission.expectedAnswer,
                            imageBitmap = bitmap
                        ).getOrElse { "Thank you for your submission! Please review the expected answer." }
                    } else {
                        "Thank you for your submission! Please review the expected answer."
                    }
                } else {
                    "Thank you for your submission! Please review the expected answer."
                }
                
                revisionSubmissionDao.updateSubmissionEvaluation(
                    submissionId = submission.id,
                    feedback = feedback,
                    isEvaluated = true,
                    evaluatedAt = System.currentTimeMillis()
                )
            } catch (e: Exception) {
                // Log error but continue processing other submissions
                e.printStackTrace()
            }
        }
    }
}