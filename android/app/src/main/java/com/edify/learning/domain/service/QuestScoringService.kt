package com.edify.learning.domain.service

import com.edify.learning.data.dao.ChapterStatsDao
import com.edify.learning.data.model.ChapterStats
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Service for calculating Quest Interest Scores based on the Quest scoring algorithm
 * Formula: InterestScore = (w_revision * RevisionScore) + (w_chat * ChatScore) + (w_notes * NoteScore) + (w_visits * VisitScore)
 * Weights: w_revision=0.4, w_chat=0.3, w_notes=0.2, w_visits=0.1
 */
@Singleton
class QuestScoringService @Inject constructor(
    private val chapterStatsDao: ChapterStatsDao
) {
    
    companion object {
        // Scoring weights as defined in Quest - Scoring Algo.md
        private const val WEIGHT_REVISION = 0.4
        private const val WEIGHT_CHAT = 0.3
        private const val WEIGHT_NOTES = 0.2
        private const val WEIGHT_VISITS = 0.1
    }
    
    /**
     * Calculate Interest Score for a specific chapter and user
     */
    suspend fun calculateInterestScore(chapterId: String, userId: String = "default_user"): Double {
        val stats = chapterStatsDao.getChapterStats(chapterId, userId)
        return stats?.calculateInterestScore() ?: 0.0
    }
    
    /**
     * Get all chapters with their Interest Scores for a user, sorted by score (highest first)
     */
    suspend fun getChaptersRankedByInterest(userId: String = "default_user"): List<ChapterInterestScore> {
        val allStats = chapterStatsDao.getChapterStatsForUser(userId)
        return allStats.map { stats ->
            ChapterInterestScore(
                chapterId = stats.chapterId,
                interestScore = stats.calculateInterestScore(),
                visitCount = stats.visitCount,
                noteCount = stats.noteCount,
                revisionCount = stats.revisionHistory.size,
                chatCount = stats.chatHistory.size
            )
        }.sortedByDescending { it.interestScore }
    }
    
    /**
     * Get top N chapters by Interest Score for Quest recommendations
     */
    suspend fun getTopChaptersForQuests(userId: String = "default_user", limit: Int = 5): List<ChapterInterestScore> {
        return getChaptersRankedByInterest(userId).take(limit)
    }
    
    /**
     * Check if a user is ready for personalized quests based on engagement thresholds
     * Uses the same simplified algorithm as QuestRepository.checkPersonalizationReadiness()
     * Simplified algorithm: 3+ chapters with meaningful engagement
     */
    suspend fun isUserReadyForPersonalizedQuests(userId: String = "default_user"): Boolean {
        // Step 1: Initialize a set to store unique engaged chapters
        val uniqueEngagedChapters = mutableSetOf<String>()
        
        // Step 2: Retrieve all chapter stats for the user
        val stats = chapterStatsDao.getChapterStatsForUser(userId)
        
        // Step 3: Iterate and identify engaged chapters
        for (chapterStat in stats) {
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
     * Get detailed scoring breakdown for debugging/developer mode
     */
    suspend fun getDetailedScoring(chapterId: String, userId: String = "default_user"): DetailedScoring? {
        val stats = chapterStatsDao.getChapterStats(chapterId, userId) ?: return null
        
        val revisionScore = if (stats.revisionHistory.isEmpty()) 0.0 else {
            val totalScores = stats.revisionHistory.mapNotNull { entry ->
                entry.gemmaAnalysis?.let { analysis ->
                    analysis.correctnessScore + analysis.originalityScore
                }
            }
            if (totalScores.isNotEmpty()) totalScores.average() else 0.0
        }
        
        val chatScore = if (stats.chatHistory.isEmpty()) 0.0 else {
            val curiosityScores = stats.chatHistory.mapNotNull { entry ->
                entry.gemmaAnalysis?.curiosityScore
            }
            if (curiosityScores.isNotEmpty()) curiosityScores.average() else 0.0
        }
        
        val noteScore = kotlin.math.min(stats.noteCount, 5).toDouble()
        val visitScore = kotlin.math.min(stats.visitCount, 5).toDouble()
        
        val totalScore = (WEIGHT_REVISION * revisionScore) + 
                        (WEIGHT_CHAT * chatScore) + 
                        (WEIGHT_NOTES * noteScore) + 
                        (WEIGHT_VISITS * visitScore)
        
        return DetailedScoring(
            chapterId = chapterId,
            userId = userId,
            revisionScore = revisionScore,
            chatScore = chatScore,
            noteScore = noteScore,
            visitScore = visitScore,
            totalInterestScore = totalScore,
            breakdown = mapOf(
                "Revision (40%)" to WEIGHT_REVISION * revisionScore,
                "Chat (30%)" to WEIGHT_CHAT * chatScore,
                "Notes (20%)" to WEIGHT_NOTES * noteScore,
                "Visits (10%)" to WEIGHT_VISITS * visitScore
            )
        )
    }
}

/**
 * Data class for chapter interest scores
 */
data class ChapterInterestScore(
    val chapterId: String,
    val interestScore: Double,
    val visitCount: Int,
    val noteCount: Int,
    val revisionCount: Int,
    val chatCount: Int
)

/**
 * Data class for detailed scoring breakdown
 */
data class DetailedScoring(
    val chapterId: String,
    val userId: String,
    val revisionScore: Double,
    val chatScore: Double,
    val noteScore: Double,
    val visitScore: Double,
    val totalInterestScore: Double,
    val breakdown: Map<String, Double>
)
