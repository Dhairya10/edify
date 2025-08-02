package com.edify.learning.data.service

import com.edify.learning.data.database.TranslatedChapterDao
import com.edify.learning.data.model.TranslatedChapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationCacheService @Inject constructor(
    private val translatedChapterDao: TranslatedChapterDao
) {
    
    /**
     * Get cached translation for a chapter if it exists and is valid
     */
    suspend fun getCachedTranslation(
        chapterId: String,
        language: String,
        originalContent: String
    ): String? = withContext(Dispatchers.IO) {
        try {
            val cachedTranslation = translatedChapterDao.getTranslatedChapter(chapterId, language)
            
            if (cachedTranslation != null) {
                val currentContentHash = generateContentHash(originalContent)
                
                // Check if the original content has changed
                if (cachedTranslation.originalContentHash == currentContentHash) {
                    return@withContext cachedTranslation.translatedContent
                } else {
                    // Content has changed, remove outdated cache
                    translatedChapterDao.deleteTranslatedChapter(chapterId, language)
                }
            }
            
            null
        } catch (e: Exception) {
            // Log error but don't crash - just return null to trigger fresh translation
            println("Error retrieving cached translation: ${e.message}")
            null
        }
    }
    
    /**
     * Cache a new translation
     */
    suspend fun cacheTranslation(
        chapterId: String,
        language: String,
        originalContent: String,
        translatedContent: String
    ) = withContext(Dispatchers.IO) {
        try {
            val contentHash = generateContentHash(originalContent)
            val currentTime = System.currentTimeMillis()
            
            val translatedChapter = TranslatedChapter(
                chapterId = chapterId,
                language = language,
                originalContentHash = contentHash,
                translatedContent = translatedContent,
                createdAt = currentTime,
                updatedAt = currentTime
            )
            
            translatedChapterDao.insertTranslatedChapter(translatedChapter)
        } catch (e: Exception) {
            // Log error but don't crash - translation will work without caching
            println("Error caching translation: ${e.message}")
        }
    }
    
    /**
     * Update an existing cached translation
     */
    suspend fun updateCachedTranslation(
        chapterId: String,
        language: String,
        translatedContent: String
    ) = withContext(Dispatchers.IO) {
        try {
            val currentTime = System.currentTimeMillis()
            translatedChapterDao.updateTranslatedContent(
                chapterId = chapterId,
                language = language,
                translatedContent = translatedContent,
                updatedAt = currentTime
            )
        } catch (e: Exception) {
            println("Error updating cached translation: ${e.message}")
        }
    }
    
    /**
     * Clear all cached translations for a specific chapter
     */
    suspend fun clearChapterTranslations(chapterId: String) = withContext(Dispatchers.IO) {
        try {
            translatedChapterDao.deleteAllTranslationsForChapter(chapterId)
        } catch (e: Exception) {
            println("Error clearing chapter translations: ${e.message}")
        }
    }
    
    /**
     * Clear all cached translations
     */
    suspend fun clearAllTranslations() = withContext(Dispatchers.IO) {
        try {
            translatedChapterDao.deleteAllTranslations()
        } catch (e: Exception) {
            println("Error clearing all translations: ${e.message}")
        }
    }
    
    /**
     * Get cache statistics
     */
    suspend fun getCacheStats(): CacheStats = withContext(Dispatchers.IO) {
        try {
            val totalTranslations = translatedChapterDao.getTranslationCount()
            CacheStats(totalTranslations = totalTranslations)
        } catch (e: Exception) {
            println("Error getting cache stats: ${e.message}")
            CacheStats(totalTranslations = 0)
        }
    }
    
    /**
     * Generate a hash of the content to detect changes
     */
    private fun generateContentHash(content: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hashBytes = digest.digest(content.toByteArray())
            hashBytes.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            // Fallback to simple hash if SHA-256 fails
            content.hashCode().toString()
        }
    }
    
    data class CacheStats(
        val totalTranslations: Int
    )
}
