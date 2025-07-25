package com.edify.learning.data.dao

import androidx.room.*
import com.edify.learning.data.model.UserResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface UserResponseDao {
    
    @Query("SELECT * FROM user_responses ORDER BY createdAt DESC")
    suspend fun getAllResponses(): List<UserResponse>
    
    @Query("SELECT * FROM user_responses WHERE chapterId = :chapterId ORDER BY exerciseIndex ASC")
    fun getResponsesForChapter(chapterId: String): Flow<List<UserResponse>>
    
    @Query("SELECT * FROM user_responses WHERE chapterId = :chapterId AND exerciseIndex = :exerciseIndex")
    suspend fun getResponse(chapterId: String, exerciseIndex: Int): UserResponse?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateResponse(response: UserResponse)
    
    @Update
    suspend fun updateResponse(response: UserResponse)
    
    @Delete
    suspend fun deleteResponse(response: UserResponse)
    
    @Query("DELETE FROM user_responses WHERE id = :id")
    suspend fun deleteById(id: Long)
    
    @Query("DELETE FROM user_responses WHERE chapterId = :chapterId")
    suspend fun deleteAllResponsesForChapter(chapterId: String)
    
    @Query("SELECT COUNT(*) FROM user_responses WHERE chapterId = :chapterId")
    suspend fun getResponseCountForChapter(chapterId: String): Int
}
