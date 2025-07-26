package com.edify.learning.data.dao

import androidx.room.*
import com.edify.learning.data.model.ChapterStats

@Dao
interface ChapterStatsDao {
    
    @Query("SELECT * FROM chapter_stats ORDER BY lastVisited DESC")
    suspend fun getAllStats(): List<ChapterStats>
    
    @Query("SELECT * FROM chapter_stats WHERE userId = :userId")
    suspend fun getChapterStatsForUser(userId: String): List<ChapterStats>
    
    @Query("SELECT * FROM chapter_stats WHERE chapterId = :chapterId AND userId = :userId")
    suspend fun getChapterStats(chapterId: String, userId: String = "default_user"): ChapterStats?
    
    @Query("SELECT * FROM chapter_stats WHERE chapterId = :chapterId")
    suspend fun getChapterStatsByChapter(chapterId: String): ChapterStats?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateChapterStats(chapterStats: ChapterStats)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(chapterStats: ChapterStats)
    
    @Query("UPDATE chapter_stats SET visitCount = visitCount + 1, lastVisited = :timestamp, updatedAt = :timestamp WHERE chapterId = :chapterId AND userId = :userId")
    suspend fun incrementVisitCount(chapterId: String, userId: String = "default_user", timestamp: Long = System.currentTimeMillis())
    
    @Query("UPDATE chapter_stats SET noteCount = noteCount + 1, updatedAt = :timestamp WHERE chapterId = :chapterId AND userId = :userId")
    suspend fun incrementNoteCount(chapterId: String, userId: String = "default_user", timestamp: Long = System.currentTimeMillis())
    
    @Delete
    suspend fun deleteChapterStats(chapterStats: ChapterStats)
    
    @Query("DELETE FROM chapter_stats WHERE id = :id")
    suspend fun deleteById(id: Long)
    
    @Query("DELETE FROM chapter_stats")
    suspend fun deleteAll()
}


