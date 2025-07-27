package com.edify.learning.data.dao

import androidx.room.*
import com.edify.learning.data.model.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    
    @Query("SELECT * FROM subjects ORDER BY name ASC")
    fun getAllSubjects(): Flow<List<Subject>>
    
    @Query("SELECT * FROM subjects WHERE id = :id")
    suspend fun getSubjectById(id: String): Subject?
    
    @Query("SELECT * FROM subjects WHERE name LIKE '%' || :query || '%'")
    fun searchSubjects(query: String): Flow<List<Subject>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<Subject>)
    
    @Update
    suspend fun updateSubject(subject: Subject)
    
    @Delete
    suspend fun deleteSubject(subject: Subject)
    
    @Query("DELETE FROM subjects WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("DELETE FROM subjects")
    suspend fun deleteAllSubjects()
    
    @Query("UPDATE subjects SET lastReadChapterId = :chapterId, updatedAt = :timestamp WHERE id = :subjectId")
    suspend fun updateLastReadChapter(subjectId: String, chapterId: String, timestamp: Long = System.currentTimeMillis())
    
    @Query("UPDATE subjects SET completedChapters = :completedChapters, updatedAt = :timestamp WHERE id = :subjectId")
    suspend fun updateProgress(subjectId: String, completedChapters: Int, timestamp: Long = System.currentTimeMillis())
}
