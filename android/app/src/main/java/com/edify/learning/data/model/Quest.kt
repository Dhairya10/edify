package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Generated Quest entity based on user's high-interest chapters
 * Generated when InterestScore > 3.5 and questGenerated == false
 */



/**
 * Quest generation strategy as determined by LLM
 */
enum class QuestStrategy {
    CONVERGENT, // Connects two different chapters/subjects
    DIVERGENT   // Deep exploration of single chapter
}

/**
 * LLM strategy selection response
 */
data class LLMStrategyResponse(
    val strategy: String, // "Convergent" or "Divergent"
    val chapters: List<String>? = null, // For convergent strategy
    val chapter: String? = null // For divergent strategy
)

/**
 * LLM quest generation response
 */
data class LLMQuestResponse(
    val title: String,
    val questionText: String,
    val selectedChapterIds: List<String>? = null // For convergent quests where LLM selects chapters
)

/**
 * Chapter interaction data for LLM context
 */
data class ChapterInteractionData(
    val chapterId: String,
    val chapterTitle: String,
    val chapterDescription: String,
    val subject: String,
    val interestScore: Double,
    val notes: List<String> = emptyList(),
    val chatQuestions: List<String> = emptyList(),
    val revisionAnswers: List<String> = emptyList()
)

data class QuestQuestion(
    val id: String,
    val questId: String,
    val question: String,
    val subject: String
)

data class Quest(
    val id: String,
    val title: String,
    val question: String,
    val subject: String
)


