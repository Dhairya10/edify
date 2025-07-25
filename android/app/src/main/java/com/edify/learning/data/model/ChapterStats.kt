package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "chapter_stats")
@TypeConverters(ChapterStatsConverters::class)
data class ChapterStats(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterId: String,
    val userId: String = "default_user", // Default user for now
    val visitCount: Int = 0,
    val noteCount: Int = 0,
    val revisionHistory: List<RevisionEntry> = emptyList(),
    val chatHistory: List<ChatEntry> = emptyList(),
    val lastVisited: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    /**
     * Calculate Interest Score based on Quest scoring algorithm
     * Formula: InterestScore = (w_revision * RevisionScore) + (w_chat * ChatScore) + (w_notes * NoteScore) + (w_visits * VisitScore)
     * Weights: w_revision=0.4, w_chat=0.3, w_notes=0.2, w_visits=0.1
     */
    fun calculateInterestScore(): Double {
        val revisionScore = calculateRevisionScore()
        val chatScore = calculateChatScore()
        val noteScore = kotlin.math.min(noteCount, 5).toDouble()
        val visitScore = kotlin.math.min(visitCount, 5).toDouble()
        
        return (0.4 * revisionScore) + (0.3 * chatScore) + (0.2 * noteScore) + (0.1 * visitScore)
    }
    
    /**
     * Calculate Revision Score: Average of correctnessScore and originalityScore across all revision entries
     */
    private fun calculateRevisionScore(): Double {
        if (revisionHistory.isEmpty()) return 0.0
        
        val totalScores = revisionHistory.mapNotNull { entry ->
            entry.gemmaAnalysis?.let { analysis ->
                analysis.correctnessScore + analysis.originalityScore
            }
        }
        
        return if (totalScores.isNotEmpty()) {
            totalScores.average()
        } else {
            0.0
        }
    }
    
    /**
     * Calculate Chat Score: Average curiosityScore from all chat entries
     */
    private fun calculateChatScore(): Double {
        if (chatHistory.isEmpty()) return 0.0
        
        val curiosityScores = chatHistory.mapNotNull { entry ->
            entry.gemmaAnalysis?.curiosityScore
        }
        
        return if (curiosityScores.isNotEmpty()) {
            curiosityScores.average()
        } else {
            0.0
        }
    }
}

data class RevisionEntry(
    val questionId: String,
    val userAnswer: String,
    val gemmaAnalysis: GemmaRevisionAnalysis? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class ChatEntry(
    val chatId: String,
    val question: String,
    val gemmaAnalysis: GemmaChatAnalysis? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class GemmaRevisionAnalysis(
    val correctnessScore: Int, // 1-5 scale
    val originalityScore: Int // 1-5 scale
)

data class GemmaChatAnalysis(
    val curiosityScore: Int // 1-5 scale
)

// User profile for Quest personalization
@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val userId: String,
    val hasUnlockedPersonalizedQuests: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

class ChapterStatsConverters {
    private val gson = Gson()
    
    @TypeConverter
    fun fromRevisionEntryList(value: List<RevisionEntry>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toRevisionEntryList(value: String): List<RevisionEntry> {
        val listType = object : TypeToken<List<RevisionEntry>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
    
    @TypeConverter
    fun fromChatEntryList(value: List<ChatEntry>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toChatEntryList(value: String): List<ChatEntry> {
        val listType = object : TypeToken<List<ChatEntry>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
}
