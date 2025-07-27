package com.edify.learning.data.model

/**
 * Quest states for personalized chapter quests
 */
enum class QuestState {
    LOCKED,     // User hasn't met requirements to unlock this quest
    UNLOCKED,   // Quest is available to start
    COMPLETED   // User has completed this quest
}

/**
 * Data model for personalized chapter-based quests
 * Used in the Quest UI to display chapter-specific deep exploration questions
 */
data class PersonalizedChapterQuest(
    val chapterId: String,
    val chapterTitle: String,
    val subject: String,
    val subjectIconRes: Int = 0,
    val generatedQuestion: String? = null,
    val interestScore: Double = 0.0,
    val lastVisited: Long = 0L,
    val state: QuestState = QuestState.UNLOCKED
)
