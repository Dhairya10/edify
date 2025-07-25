package com.edify.learning.data.repository

import com.edify.learning.data.dao.ChapterDao
import com.edify.learning.data.dao.ChapterStatsDao
import com.edify.learning.data.dao.SubjectDao
import com.edify.learning.data.dao.UserProfileDao
import com.edify.learning.data.model.*
import com.edify.learning.data.service.QuestService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestRepository @Inject constructor(
    private val chapterStatsDao: ChapterStatsDao,
    private val userProfileDao: UserProfileDao,
    private val chapterDao: ChapterDao,
    private val subjectDao: SubjectDao,
    private val questService: QuestService
) {
    
    /**
     * Check if user has unlocked personalized quests
     * Simplified algorithm: 3+ chapters with meaningful engagement
     */
    suspend fun checkPersonalizationReadiness(userId: String): Boolean {
        // Check if already unlocked
        val userProfile = userProfileDao.getUserProfile(userId)
        if (userProfile?.hasUnlockedPersonalizedQuests == true) {
            return true
        }
        
        // Step 1: Initialize a set to store unique engaged chapters
        val uniqueEngagedChapters = mutableSetOf<String>()
        
        // Step 2: Retrieve all chapter stats for the user
        val allChapterStats = chapterStatsDao.getChapterStatsForUser(userId)
        
        // Step 3: Iterate and identify engaged chapters
        for (chapterStat in allChapterStats) {
            // Check if there has been at least one meaningful action in this chapter
            val hasMeaningfulAction = (chapterStat.noteCount > 0) ||
                                    (chapterStat.chatHistory.isNotEmpty()) ||
                                    (chapterStat.revisionHistory.isNotEmpty())
            
            // If engaged, add the chapter's ID to the set
            if (hasMeaningfulAction) {
                uniqueEngagedChapters.add(chapterStat.chapterId)
            }
        }
        
        // Step 4: Check against the threshold
        val hasSufficientBreadth = uniqueEngagedChapters.size >= 3
        
        // Step 5: Return the final result
        return hasSufficientBreadth
    }
    
    /**
     * Check quest readiness status - optimized for one-time use
     * Only checks if user hasn't already unlocked personalized quests
     */
    suspend fun getQuestReadinessStatus(userId: String): Boolean {
        // If already unlocked, return true immediately
        val userProfile = userProfileDao.getUserProfile(userId)
        if (userProfile?.hasUnlockedPersonalizedQuests == true) {
            return true
        }
        
        // Otherwise, check current readiness
        return checkPersonalizationReadiness(userId)
    }
    
    /**
     * Update user's personalization status
     */
    suspend fun updatePersonalizationStatus(userId: String, hasUnlocked: Boolean) {
        val existingProfile = userProfileDao.getUserProfile(userId)
        if (existingProfile != null) {
            userProfileDao.updatePersonalizationStatus(userId, hasUnlocked)
        } else {
            val newProfile = UserProfile(
                userId = userId,
                hasUnlockedPersonalizedQuests = hasUnlocked
            )
            userProfileDao.insertOrUpdateUserProfile(newProfile)
        }
    }
    
    /**
     * Calculate interest score for a chapter
     * Based on the scoring algorithm from documentation
     */
    suspend fun calculateInterestScore(chapterStats: ChapterStats): Double {
        // Weights from documentation
        val wRevision = 0.4
        val wChat = 0.3
        val wNotes = 0.2
        val wVisits = 0.1
        
        val revisionScore = calculateRevisionScore(chapterStats.revisionHistory)
        val chatScore = calculateChatScore(chapterStats.chatHistory)
        val noteScore = minOf(chapterStats.noteCount.toDouble(), 5.0)
        val visitScore = minOf(chapterStats.visitCount.toDouble(), 5.0)
        
        return (wRevision * revisionScore) + 
               (wChat * chatScore) + 
               (wNotes * noteScore) + 
               (wVisits * visitScore)
    }
    
    private fun calculateRevisionScore(revisionHistory: List<RevisionEntry>): Double {
        if (revisionHistory.isEmpty()) return 0.0
        
        val scores = revisionHistory.mapNotNull { entry ->
            entry.gemmaAnalysis?.let { analysis ->
                (analysis.correctnessScore + analysis.originalityScore) / 2.0
            }
        }
        
        return if (scores.isNotEmpty()) scores.average() else 0.0
    }
    
    private fun calculateChatScore(chatHistory: List<ChatEntry>): Double {
        if (chatHistory.isEmpty()) return 0.0
        
        val scores = chatHistory.mapNotNull { entry ->
            entry.gemmaAnalysis?.curiosityScore?.toDouble()
        }
        
        return if (scores.isNotEmpty()) scores.average() else 0.0
    }
    
    /**
     * Get personalized quests based on user's interests
     */
    suspend fun getPersonalizedQuests(userId: String): List<Quest> {
        val chapterStats = chapterStatsDao.getChapterStatsForUser(userId)
        return questService.generatePersonalizedQuests(userId, chapterStats)
    }
    
    /**
     * Submit quest answer and track progress
     */
    suspend fun submitQuestAnswer(questId: String, answer: String) {
        // TODO: Store quest answer and analyze for personalization
        // For now, this is a placeholder that could:
        // 1. Store the answer in a quest answers table
        // 2. Analyze the answer quality using Gemma
        // 3. Update user engagement metrics
        // 4. Contribute to personalization algorithm
        
        // Placeholder implementation - in a real app this would:
        // - Save to database
        // - Analyze answer quality
        // - Update user progress
    }
    
    /**
     * Get all chapter stats for a user
     */
    suspend fun getAllChapterStatsForUser(userId: String): List<ChapterStats> {
        return chapterStatsDao.getChapterStatsForUser(userId)
    }
    
    /**
     * Get chapter by ID
     */
    suspend fun getChapterById(chapterId: String): Chapter? {
        return chapterDao.getChapterById(chapterId)
    }
    
    /**
     * Get subject by ID
     */
    suspend fun getSubjectById(subjectId: String): Subject? {
        return subjectDao.getSubjectById(subjectId)
    }
    
    /**
     * Generate deep exploration question for a specific chapter using Gemma
     */
    suspend fun generateDeepExplorationQuestion(chapterId: String): String {
        val chapter = chapterDao.getChapterById(chapterId) ?: return "What interests you most about this topic?"
        val subject = subjectDao.getSubjectById(chapter.subjectId)
        
        return questService.generateDeepExplorationQuestion(
            chapter = chapter,
            subject = subject?.name ?: "General"
        )
    }
    
    /**
     * Generate quest for a specific chapter
     */
    suspend fun generateQuestForChapter(chapterId: String, userId: String): Quest? {
        val chapter = chapterDao.getChapterById(chapterId) ?: return null
        val chapterStats = chapterStatsDao.getChapterStats(chapterId, userId)
        
        // Calculate interest score
        val interestScore = if (chapterStats != null) {
            calculateInterestScore(chapterStats)
        } else {
            0.0
        }
        
        // Generate curiosity question using QuestService
        val question = questService.generateCuriosityQuestionForChapter(
            chapter = chapter,
            interestScore = interestScore,
            userEngagement = chapterStats ?: ChapterStats(
                id = 0L,
                chapterId = chapterId,
                userId = userId
            )
        )
        
        return Quest(
            id = "quest_${chapterId}_${System.currentTimeMillis()}",
            title = "${chapter.title} Deep Dive",
            description = question,
            subject = subjectDao.getSubjectById(chapter.subjectId)?.name ?: "General",
            difficulty = when {
                interestScore >= 4.0 -> QuestDifficulty.HARD
                interestScore >= 2.5 -> QuestDifficulty.MEDIUM
                else -> QuestDifficulty.EASY
            },
            estimatedTime = 15 + (interestScore * 5).toInt()
        )
    }

    /**
     * Update chapter stats after user actions
     */
    suspend fun updateChapterStats(
        chapterId: String,
        userId: String,
        action: UserAction
    ) {
        val statsId = questService.createChapterStatsId(chapterId, userId)
        val existingStats = chapterStatsDao.getChapterStats(chapterId, userId)
        
        when (action) {
            is UserAction.Visit -> {
                if (existingStats == null) {
                    val newStats = ChapterStats(
                        id = 0L,
                        chapterId = chapterId,
                        userId = userId,
                        visitCount = 1
                    )
                    chapterStatsDao.insertOrUpdateChapterStats(newStats)
                } else {
                    chapterStatsDao.incrementVisitCount(chapterId, userId)
                }
            }
            is UserAction.SaveNote -> {
                if (existingStats == null) {
                    val newStats = ChapterStats(
                        id = 0L,
                        chapterId = chapterId,
                        userId = userId,
                        noteCount = 1
                    )
                    chapterStatsDao.insertOrUpdateChapterStats(newStats)
                } else {
                    chapterStatsDao.incrementNoteCount(chapterId, userId)
                }
            }
            is UserAction.SendChat -> {
                val curiosityScore = action.curiosityScore ?: questService.analyzeChatCuriosity(action.question)
                val chatEntry = ChatEntry(
                    chatId = "chat_${System.currentTimeMillis()}",
                    question = action.question,
                    gemmaAnalysis = GemmaChatAnalysis(curiosityScore)
                )
                
                val updatedStats = existingStats?.copy(
                    chatHistory = existingStats.chatHistory + chatEntry,
                    updatedAt = System.currentTimeMillis()
                ) ?: ChapterStats(
                    id = 0L,
                    chapterId = chapterId,
                    userId = userId,
                    chatHistory = listOf(chatEntry)
                )
                
                chapterStatsDao.insertOrUpdateChapterStats(updatedStats)
            }
            is UserAction.SubmitRevision -> {
                val analysis = if (action.correctnessScore != null && action.originalityScore != null) {
                    GemmaRevisionAnalysis(action.correctnessScore, action.originalityScore)
                } else {
                    questService.analyzeRevisionAnswer("Question", action.answer)
                }
                
                val revisionEntry = RevisionEntry(
                    questionId = action.questionId,
                    userAnswer = action.answer,
                    gemmaAnalysis = analysis
                )
                
                val updatedStats = existingStats?.copy(
                    revisionHistory = existingStats.revisionHistory + revisionEntry,
                    updatedAt = System.currentTimeMillis()
                ) ?: ChapterStats(
                    id = 0L,
                    chapterId = chapterId,
                    userId = userId,
                    revisionHistory = listOf(revisionEntry)
                )
                
                chapterStatsDao.insertOrUpdateChapterStats(updatedStats)
            }
        }
        
        // After updating, check if user should unlock personalized quests
        val userProfile = userProfileDao.getUserProfile(userId)
        if (userProfile?.hasUnlockedPersonalizedQuests != true) {
            val isReady = checkPersonalizationReadiness(userId)
            if (isReady) {
                updatePersonalizationStatus(userId, true)
            }
        }
    }
}

sealed class UserAction {
    object Visit : UserAction()
    object SaveNote : UserAction()
    data class SendChat(val question: String, val curiosityScore: Int? = null) : UserAction()
    data class SubmitRevision(
        val questionId: String, 
        val answer: String,
        val correctnessScore: Int? = null,
        val originalityScore: Int? = null
    ) : UserAction()
}
