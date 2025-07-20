package com.edify.learning.data.dao

import androidx.room.*
import com.edify.learning.data.model.Chapter
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    
    @Query("SELECT * FROM chapters WHERE subjectId = :subjectId ORDER BY chapterNumber ASC")
    fun getChaptersBySubject(subjectId: String): Flow<List<Chapter>>
    
    @Query("SELECT * FROM chapters WHERE id = :id")
    suspend fun getChapterById(id: String): Chapter?
    
    @Query("SELECT * FROM chapters WHERE subjectId = :subjectId AND chapterNumber = :chapterNumber")
    suspend fun getChapterByNumber(subjectId: String, chapterNumber: Int): Chapter?
    
    @Query("SELECT * FROM chapters WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchChapters(query: String): Flow<List<Chapter>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: Chapter)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<Chapter>)
    
    @Update
    suspend fun updateChapter(chapter: Chapter)
    
    @Delete
    suspend fun deleteChapter(chapter: Chapter)
    
    @Query("DELETE FROM chapters")
    suspend fun deleteAllChapters()
    
    @Query("UPDATE chapters SET readingProgress = :progress, updatedAt = :timestamp WHERE id = :chapterId")
    suspend fun updateReadingProgress(chapterId: String, progress: Float, timestamp: Long = System.currentTimeMillis())
    
    @Query("UPDATE chapters SET isCompleted = :isCompleted, updatedAt = :timestamp WHERE id = :chapterId")
    suspend fun updateCompletionStatus(chapterId: String, isCompleted: Boolean, timestamp: Long = System.currentTimeMillis())
    
    @Query("SELECT COUNT(*) FROM chapters WHERE subjectId = :subjectId AND isCompleted = 1")
    suspend fun getCompletedChaptersCount(subjectId: String): Int
    
    @Query("SELECT COUNT(*) FROM chapters WHERE subjectId = :subjectId")
    suspend fun getTotalChaptersCount(subjectId: String): Int
}
