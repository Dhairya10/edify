package com.edify.learning.data.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.edify.learning.data.dao.RevisionSubmissionDao
import com.edify.learning.data.model.FeedbackCategory
import com.edify.learning.data.model.RevisionSubmission
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

data class RevisionEvaluationResult(
    val grade: FeedbackCategory,
    val feedback: String
)

data class EvaluationResponse(
    val grade: String,
    val feedback: String
)

@Singleton
class RevisionEvaluationService @Inject constructor(
    private val context: Context,
    private val gemmaService: GemmaService,
    private val promptService: PromptService,
    private val revisionSubmissionDao: RevisionSubmissionDao
) {
    
    private val gson = Gson()
    
    suspend fun evaluateTextResponse(
        chapterId: String,
        questionIndex: Int,
        question: String,
        expectedAnswer: String,
        studentResponse: String
    ): Result<RevisionSubmission> = withContext(Dispatchers.IO) {
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
                onSuccess = { result ->
                    // Update submission with evaluation results
                    revisionSubmissionDao.updateSubmissionEvaluation(
                        submissionId = submissionId,
                        feedback = result.feedback,
                        grade = result.grade.name,
                        isEvaluated = true,
                        evaluatedAt = System.currentTimeMillis()
                    )
                    
                    val updatedSubmission = submission.copy(
                        id = submissionId,
                        gemmaFeedback = result.feedback,
                        gemmaGrade = result.grade,
                        isEvaluated = true,
                        evaluatedAt = System.currentTimeMillis()
                    )
                    
                    Result.success(updatedSubmission)
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
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
                onSuccess = { result ->
                    // Update submission with evaluation results
                    revisionSubmissionDao.updateSubmissionEvaluation(
                        submissionId = submissionId,
                        feedback = result.feedback,
                        grade = result.grade.name,
                        isEvaluated = true,
                        evaluatedAt = System.currentTimeMillis()
                    )
                    
                    val updatedSubmission = submission.copy(
                        id = submissionId,
                        gemmaFeedback = result.feedback,
                        gemmaGrade = result.grade,
                        isEvaluated = true,
                        evaluatedAt = System.currentTimeMillis()
                    )
                    
                    Result.success(updatedSubmission)
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private suspend fun evaluateWithGemma(
        question: String,
        expectedAnswer: String,
        studentResponse: String,
        isImageResponse: Boolean
    ): Result<RevisionEvaluationResult> {
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
                    parseEvaluationResponse(response)
                },
                onFailure = { _ ->
                    // Fallback evaluation if Gemma fails
                    val fallbackResult = createFallbackEvaluation(studentResponse)
                    Result.success(fallbackResult)
                }
            )
        } catch (e: Exception) {
            // Create fallback evaluation
            val fallbackResult = createFallbackEvaluation(studentResponse)
            Result.success(fallbackResult)
        }
    }
    
    private suspend fun evaluateImageWithGemma(
        question: String,
        expectedAnswer: String,
        imageBitmap: Bitmap
    ): Result<RevisionEvaluationResult> {
        return try {
            val prompt = promptService.getFormattedPrompt(
                "revision_image_evaluation",
                "question" to question,
                "expectedAnswer" to expectedAnswer
            )
            
            val gemmaResult = gemmaService.generateResponseWithImage(prompt, imageBitmap)
            
            return gemmaResult.fold(
                onSuccess = { response ->
                    parseEvaluationResponse(response)
                },
                onFailure = { _ ->
                    // Fallback evaluation for image
                    val fallbackResult = RevisionEvaluationResult(
                        grade = FeedbackCategory.GOOD_JOB,
                        feedback = "Thank you for submitting your handwritten work! Your effort shows dedication to learning. Please review the expected answer to ensure you've covered all key points."
                    )
                    Result.success(fallbackResult)
                }
            )
        } catch (e: Exception) {
            val fallbackResult = RevisionEvaluationResult(
                grade = FeedbackCategory.GOOD_JOB,
                feedback = "Thank you for submitting your handwritten work! Your effort shows dedication to learning. Please review the expected answer to ensure you've covered all key points."
            )
            Result.success(fallbackResult)
        }
    }
    
    private fun parseEvaluationResponse(gemmaResponse: String): Result<RevisionEvaluationResult> {
        return try {
            // Try to extract JSON from the response
            val jsonStart = gemmaResponse.indexOf('{')
            val jsonEnd = gemmaResponse.lastIndexOf('}') + 1
            
            if (jsonStart == -1 || jsonEnd <= jsonStart) {
                throw JsonSyntaxException("No valid JSON found in response")
            }
            
            val jsonString = gemmaResponse.substring(jsonStart, jsonEnd)
            val evaluationResponse = gson.fromJson(jsonString, EvaluationResponse::class.java)
            
            val grade = when (evaluationResponse.grade.uppercase()) {
                "EXCELLENT" -> FeedbackCategory.EXCELLENT
                "GOOD_JOB" -> FeedbackCategory.GOOD_JOB
                "NEEDS_IMPROVEMENT" -> FeedbackCategory.NEEDS_IMPROVEMENT
                else -> FeedbackCategory.GOOD_JOB // Default fallback
            }
            
            val result = RevisionEvaluationResult(
                grade = grade,
                feedback = evaluationResponse.feedback.take(200) // Limit feedback length
            )
            
            Result.success(result)
        } catch (e: Exception) {
            // If JSON parsing fails, try to extract grade and feedback using regex
            try {
                val gradePattern = "(?i)grade[\"\\s:]*[\"\\s]*([A-Z_]+)".toRegex()
                val feedbackPattern = "(?i)feedback[\"\\s:]*[\"\\s]*([^\"\\n]+)".toRegex()
                
                val gradeMatch = gradePattern.find(gemmaResponse)
                val feedbackMatch = feedbackPattern.find(gemmaResponse)
                
                val grade = when (gradeMatch?.groupValues?.get(1)?.uppercase()) {
                    "EXCELLENT" -> FeedbackCategory.EXCELLENT
                    "GOOD_JOB" -> FeedbackCategory.GOOD_JOB
                    "NEEDS_IMPROVEMENT" -> FeedbackCategory.NEEDS_IMPROVEMENT
                    else -> FeedbackCategory.GOOD_JOB
                }
                
                val feedback = feedbackMatch?.groupValues?.get(1)?.trim()?.take(200) 
                    ?: "Great effort on your response! Keep practicing to improve your understanding."
                
                val result = RevisionEvaluationResult(grade = grade, feedback = feedback)
                Result.success(result)
            } catch (regexException: Exception) {
                Result.failure(Exception("Failed to parse evaluation response: ${e.message}"))
            }
        }
    }
    
    private fun createFallbackEvaluation(studentResponse: String): RevisionEvaluationResult {
        val grade = when {
            studentResponse.length < 20 -> FeedbackCategory.NEEDS_IMPROVEMENT
            studentResponse.length > 100 -> FeedbackCategory.GOOD_JOB
            else -> FeedbackCategory.GOOD_JOB
        }
        
        val feedback = when (grade) {
            FeedbackCategory.NEEDS_IMPROVEMENT -> "Your response shows effort, but could benefit from more detail. Try to elaborate on your key points and provide specific examples."
            FeedbackCategory.GOOD_JOB -> "Good work on your response! You've shown understanding of the topic. Review the expected answer to see if you missed any important points."
            FeedbackCategory.EXCELLENT -> "Excellent response! You've demonstrated strong understanding and provided thoughtful analysis."
        }
        
        return RevisionEvaluationResult(grade = grade, feedback = feedback)
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
    
    suspend fun getRevisionHistory(chapterId: String, questionIndex: Int): List<RevisionSubmission> {
        return revisionSubmissionDao.getSubmissionsForQuestion(chapterId, questionIndex)
            .let { flow ->
                // For now, return empty list. In a real implementation, you'd collect from the flow
                emptyList()
            }
    }
    
    suspend fun processAllPendingEvaluations() {
        val pendingSubmissions = revisionSubmissionDao.getPendingEvaluations()
        
        pendingSubmissions.forEach { submission ->
            try {
                when {
                    !submission.userTextResponse.isNullOrBlank() -> {
                        evaluateWithGemma(
                            question = submission.question,
                            expectedAnswer = submission.expectedAnswer,
                            studentResponse = submission.userTextResponse,
                            isImageResponse = false
                        ).fold(
                            onSuccess = { result ->
                                revisionSubmissionDao.updateSubmissionEvaluation(
                                    submissionId = submission.id,
                                    feedback = result.feedback,
                                    grade = result.grade.name,
                                    isEvaluated = true,
                                    evaluatedAt = System.currentTimeMillis()
                                )
                            },
                            onFailure = { /* Handle error - maybe log it */ }
                        )
                    }
                    !submission.userImageUri.isNullOrBlank() -> {
                        val bitmap = loadBitmapFromUri(submission.userImageUri)
                        bitmap?.let { bmp ->
                            evaluateImageWithGemma(
                                question = submission.question,
                                expectedAnswer = submission.expectedAnswer,
                                imageBitmap = bmp
                            ).fold(
                                onSuccess = { result ->
                                    revisionSubmissionDao.updateSubmissionEvaluation(
                                        submissionId = submission.id,
                                        feedback = result.feedback,
                                        grade = result.grade.name,
                                        isEvaluated = true,
                                        evaluatedAt = System.currentTimeMillis()
                                    )
                                },
                                onFailure = { /* Handle error */ }
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                // Log error and continue with next submission
            }
        }
    }
}