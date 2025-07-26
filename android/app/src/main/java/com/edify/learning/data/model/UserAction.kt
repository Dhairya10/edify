package com.edify.learning.data.model

/**
 * Sealed class representing different user actions that can trigger quest generation
 * Used to track meaningful user engagement for the Quest system
 */
sealed class UserAction {
    /**
     * User submitted a revision answer
     */
    data class SubmitRevision(
        val questionId: String,
        val answer: String,
        val correctnessScore: Int? = null,
        val originalityScore: Int? = null
    ) : UserAction()
    
    /**
     * User added a note
     */
    data class AddNote(
        val noteId: String,
        val content: String
    ) : UserAction()
    
    /**
     * User sent a chat message
     */
    data class SendChatMessage(
        val messageId: String,
        val message: String,
        val curiosityScore: Int? = null
    ) : UserAction()
    
    /**
     * User visited a chapter
     */
    data class VisitChapter(
        val timestamp: Long = System.currentTimeMillis()
    ) : UserAction()
}
