package com.edify.learning.data.dao

import androidx.room.*
import com.edify.learning.data.model.RevisionSubmission
import kotlinx.coroutines.flow.Flow

@Dao
interface RevisionSubmissionDao {
    
    @Query("SELECT * FROM revision_submissions WHERE chapterId = :chapterId ORDER BY questionIndex ASC, submittedAt DESC")
    fun getSubmissionsForChapter(chapterId: String): Flow<List<RevisionSubmission>>
    
    @Query("SELECT * FROM revision_submissions WHERE chapterId = :chapterId AND questionIndex = :questionIndex ORDER BY submittedAt DESC")
    fun getSubmissionsForQuestion(chapterId: String, questionIndex: Int): Flow<List<RevisionSubmission>>
    
    @Query("SELECT * FROM revision_submissions WHERE chapterId = :chapterId AND questionIndex = :questionIndex ORDER BY submittedAt DESC LIMIT 1")
    suspend fun getLatestSubmissionForQuestion(chapterId: String, questionIndex: Int): RevisionSubmission?
    
    @Query("SELECT * FROM revision_submissions WHERE id = :submissionId")
    suspend fun getSubmissionById(submissionId: Long): RevisionSubmission?
    
    @Insert
    suspend fun insertSubmission(submission: RevisionSubmission): Long
    
    @Update
    suspend fun updateSubmission(submission: RevisionSubmission)
    
    @Query("UPDATE revision_submissions SET gemmaFeedback = :feedback, gemmaGrade = :grade, isEvaluated = :isEvaluated, evaluatedAt = :evaluatedAt WHERE id = :submissionId")
    suspend fun updateSubmissionEvaluation(
        submissionId: Long,
        feedback: String,
        grade: String,
        isEvaluated: Boolean,
        evaluatedAt: Long
    )
    
    @Delete
    suspend fun deleteSubmission(submission: RevisionSubmission)
    
    @Query("DELETE FROM revision_submissions WHERE chapterId = :chapterId")
    suspend fun deleteSubmissionsForChapter(chapterId: String)
    
    @Query("SELECT COUNT(*) FROM revision_submissions WHERE chapterId = :chapterId AND isEvaluated = 1")
    suspend fun getEvaluatedSubmissionsCount(chapterId: String): Int
    
    @Query("SELECT COUNT(DISTINCT questionIndex) FROM revision_submissions WHERE chapterId = :chapterId")
    suspend fun getUniqueQuestionsAnswered(chapterId: String): Int
    
    @Query("SELECT * FROM revision_submissions WHERE isEvaluated = 0 ORDER BY submittedAt ASC")
    suspend fun getPendingEvaluations(): List<RevisionSubmission>
}