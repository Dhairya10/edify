package com.edify.learning.data.dao

import androidx.room.*
import com.edify.learning.data.model.RevisionResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface RevisionResponseDao {
    
    @Query("SELECT * FROM revision_responses WHERE chapterId = :chapterId ORDER BY questionIndex ASC")
    fun getRevisionResponsesForChapter(chapterId: String): Flow<List<RevisionResponse>>
    
    @Query("SELECT * FROM revision_responses WHERE chapterId = :chapterId AND questionIndex = :questionIndex")
    suspend fun getRevisionResponse(chapterId: String, questionIndex: Int): RevisionResponse?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRevisionResponse(response: RevisionResponse): Long
    
    @Update
    suspend fun updateRevisionResponse(response: RevisionResponse)
    
    @Delete
    suspend fun deleteRevisionResponse(response: RevisionResponse)
    
    @Query("DELETE FROM revision_responses WHERE chapterId = :chapterId")
    suspend fun deleteRevisionResponsesForChapter(chapterId: String)
    
    @Query("SELECT COUNT(*) FROM revision_responses WHERE chapterId = :chapterId AND isSubmitted = 1")
    suspend fun getSubmittedResponsesCount(chapterId: String): Int
    
    @Query("SELECT COUNT(*) FROM revision_responses WHERE chapterId = :chapterId")
    suspend fun getTotalResponsesCount(chapterId: String): Int
    
    @Query("SELECT * FROM revision_responses WHERE isSubmitted = 1 ORDER BY updatedAt DESC")
    fun getAllSubmittedResponses(): Flow<List<RevisionResponse>>
}