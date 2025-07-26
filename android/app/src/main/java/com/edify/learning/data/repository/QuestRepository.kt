package com.edify.learning.data.repository

import com.edify.learning.data.dao.GeneratedQuestDao
import com.edify.learning.data.dao.ChapterStatsDao
import com.edify.learning.data.dao.ChapterDao
import com.edify.learning.data.dao.SubjectDao
import com.edify.learning.data.model.*
import com.edify.learning.data.service.QuestGenerationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestRepository @Inject constructor(
    private val generatedQuestDao: GeneratedQuestDao,
    private val chapterStatsDao: ChapterStatsDao,
    private val chapterDao: ChapterDao,
    private val subjectDao: SubjectDao,
    private val questGenerationService: QuestGenerationService
) {
    
    /**
     * Get all generated quests for the user
     */
    fun getAllQuests(userId: String = "default_user"): Flow<List<GeneratedQuest>> {
        return generatedQuestDao.getAllQuests(userId)
    }
    
    /**
     * Get incomplete quests for the user
     */
    fun getIncompleteQuests(userId: String = "default_user"): Flow<List<GeneratedQuest>> {
        return generatedQuestDao.getIncompleteQuests(userId)
    }
    
    /**
     * Get completed quests for the user
     */
    fun getCompletedQuests(userId: String = "default_user"): Flow<List<GeneratedQuest>> {
        return generatedQuestDao.getCompletedQuests(userId)
    }
    
    /**
     * Get quest by ID
     */
    suspend fun getQuestById(questId: String): GeneratedQuest? {
        return generatedQuestDao.getQuestById(questId)
    }
    
    /**
     * Complete a quest with user's answer
     */
    suspend fun completeQuest(questId: String, answer: String, userId: String = "default_user") {
        generatedQuestDao.completeQuest(questId, answer)
    }
    
    /**
     * Trigger quest generation manually (for testing or manual triggers)
     */
    suspend fun triggerQuestGeneration(userId: String = "default_user") {
        questGenerationService.checkAndGenerateQuests(userId)
    }
    
    /**
     * Get chapter details for quest display
     */
    suspend fun getChapterDetails(chapterId: String): Pair<Chapter?, Subject?> {
        val chapter = chapterDao.getChapterById(chapterId)
        val subject = chapter?.let { subjectDao.getSubjectById(it.subjectId) }
        return Pair(chapter, subject)
    }
    
    /**
     * Get quest count for user
     */
    suspend fun getQuestCount(userId: String = "default_user"): Int {
        return generatedQuestDao.getQuestCount(userId)
    }
    
    /**
     * Get top interested chapters for personalized quests
     */
    suspend fun getTopInterestedChapters(userId: String = "default_user"): List<PersonalizedChapterQuest> {
        return try {
            // Get all chapter stats with interest scores above threshold
            val allStats = chapterStatsDao.getAllStats()
            val highInterestChapters = allStats.filter { stats: ChapterStats ->
                stats.calculateInterestScore() > 3.5 // Threshold from documentation
            }.sortedByDescending { stats -> stats.calculateInterestScore() }
            
            // Get all completed quests to determine quest states
            val completedQuests = generatedQuestDao.getCompletedQuests(userId)
            val completedChapterIds = completedQuests.first().map { quest -> quest.chapterId }.toSet()
            
            // Convert to PersonalizedChapterQuest objects
            val personalizedQuests = mutableListOf<PersonalizedChapterQuest>()
            
            for (stats in highInterestChapters.take(5)) { // Limit to top 5
                try {
                    val chapter = chapterDao.getChapterById(stats.chapterId)
                    val subject = chapter?.let { subjectDao.getSubjectById(it.subjectId) }
                    
                    if (chapter != null && subject != null) {
                        val subjectIconRes = when (subject.name.lowercase()) {
                            "science" -> android.R.drawable.ic_dialog_info
                            "english" -> android.R.drawable.ic_menu_edit
                            "maths", "math" -> android.R.drawable.ic_input_add
                            else -> android.R.drawable.ic_dialog_info
                        }
                        
                        // Determine quest state based on completion and interest score
                        val questState = when {
                            completedChapterIds.contains(chapter.id) -> QuestState.COMPLETED
                            stats.calculateInterestScore() >= 4.0 -> QuestState.UNLOCKED
                            else -> QuestState.LOCKED
                        }
                        
                        personalizedQuests.add(
                            PersonalizedChapterQuest(
                                chapterId = chapter.id,
                                chapterTitle = chapter.title,
                                subject = subject.name,
                                subjectIconRes = subjectIconRes,
                                generatedQuestion = null,
                                interestScore = stats.calculateInterestScore(),
                                lastVisited = stats.lastVisited,
                                state = questState
                            )
                        )
                    }
                } catch (e: Exception) {
                    // Skip this chapter if there's an error loading details
                    println("Error loading chapter details for ${stats.chapterId}: ${e.message}")
                }
            }
            
            personalizedQuests
        } catch (e: Exception) {
            println("Error loading top interested chapters: ${e.message}")
            emptyList()
        }
    }
}
