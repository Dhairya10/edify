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
    val questGenerated: Boolean = false, // Flag to track if quest has been generated for this chapter
    val divergentQuestGenerated: Boolean = false, // Flag to track if divergent quest has been generated for this chapter
    val revisionHistory: List<RevisionEntry> = emptyList(),
    val chatHistory: List<ChatEntry> = emptyList(),
    val lastVisited: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    /**
     * Calculate Interest Score based on Quest scoring algorithm
     * Formula: InterestScore = (w_revision * RevisionScore) + (w_chat * ChatScore) + (w_notes * NoteScore) + (w_visits * VisitScore)
     * Weights: w_chat=0.4, w_notes=0.25, w_visits=0.2, w_revision=0.15
     */
    fun calculateInterestScore(): Double {
        val revisionScore = if (revisionHistory.isNotEmpty()) {
            revisionHistory.map { ((it.gemmaAnalysis?.correctnessScore ?: 0) + (it.gemmaAnalysis?.originalityScore ?: 0)) / 2.0 }.average()
        } else 0.0
        
        val chatScore = if (chatHistory.isNotEmpty()) {
            chatHistory.map { 
                // Use Gemma analysis if available, otherwise give base score for engagement
                it.gemmaAnalysis?.curiosityScore?.toDouble() ?: 2.5 // Base score when analysis disabled
            }.average()
        } else 0.0
        
        val noteScore = kotlin.math.min(noteCount.toDouble(), 5.0)
        val visitScore = kotlin.math.min(visitCount.toDouble(), 5.0)
        
        return (0.15 * revisionScore) + (0.4 * chatScore) + (0.25 * noteScore) + (0.2 * visitScore)
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
    val name: String = "",
    val languagePreference: String = "English",
    val hasCompletedOnboarding: Boolean = false,
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
