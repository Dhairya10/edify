package com.edify.learning.data.database

import androidx.room.*
import com.edify.learning.data.model.TranslatedChapter
import kotlinx.coroutines.flow.Flow

@Dao
interface TranslatedChapterDao {
    
    @Query("SELECT * FROM translated_chapters WHERE chapterId = :chapterId AND language = :language")
    suspend fun getTranslatedChapter(chapterId: String, language: String): TranslatedChapter?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslatedChapter(translatedChapter: TranslatedChapter)
    
    @Query("UPDATE translated_chapters SET translatedContent = :translatedContent, updatedAt = :updatedAt WHERE chapterId = :chapterId AND language = :language")
    suspend fun updateTranslatedContent(chapterId: String, language: String, translatedContent: String, updatedAt: Long)
    
    @Query("DELETE FROM translated_chapters WHERE chapterId = :chapterId AND language = :language")
    suspend fun deleteTranslatedChapter(chapterId: String, language: String)
    
    @Query("DELETE FROM translated_chapters WHERE chapterId = :chapterId")
    suspend fun deleteAllTranslationsForChapter(chapterId: String)
    
    @Query("SELECT * FROM translated_chapters")
    fun getAllTranslatedChapters(): Flow<List<TranslatedChapter>>
    
    @Query("DELETE FROM translated_chapters")
    suspend fun deleteAllTranslations()
    
    @Query("SELECT COUNT(*) FROM translated_chapters")
    suspend fun getTranslationCount(): Int
    
    @Query("SELECT * FROM translated_chapters WHERE chapterId = :chapterId")
    suspend fun getTranslationsForChapter(chapterId: String): List<TranslatedChapter>
}
