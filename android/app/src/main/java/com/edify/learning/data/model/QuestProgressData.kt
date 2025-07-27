package com.edify.learning.data.model

/**
 * Data model for Quest progress tracking
 * Tracks user engagement across 3 key actions: adding notes, answering questions, chatting
 */
data class QuestProgressData(
    val hasAddedNote: Boolean = false,
    val hasAnsweredQuestion: Boolean = false,
    val hasChatted: Boolean = false,
    val completedActions: Int = 0,
    val totalActions: Int = 3,
    val progressPercentage: Float = 0.0f
) {
    companion object {
        fun calculateProgress(
            hasAddedNote: Boolean,
            hasAnsweredQuestion: Boolean,
            hasChatted: Boolean
        ): QuestProgressData {
            val completed = listOf(hasAddedNote, hasAnsweredQuestion, hasChatted).count { it }
            val percentage = completed.toFloat() / 3f
            
            return QuestProgressData(
                hasAddedNote = hasAddedNote,
                hasAnsweredQuestion = hasAnsweredQuestion,
                hasChatted = hasChatted,
                completedActions = completed,
                totalActions = 3,
                progressPercentage = percentage
            )
        }
    }
}
