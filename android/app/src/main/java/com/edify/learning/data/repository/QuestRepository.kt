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
     * Get quest by chapter ID (for navigation from PersonalizedChapterQuest)
     */
    suspend fun getQuestByChapterId(chapterId: String): GeneratedQuest? {
        val allQuests = generatedQuestDao.getAllQuests().first()
        return allQuests.find { it.chapterId == chapterId }
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
     * Unlock a quest by chapter ID
     */
    suspend fun unlockQuest(chapterId: String, userId: String = "default_user") {
        generatedQuestDao.unlockQuestByChapterId(chapterId, userId)
    }
    
    /**
     * Get personalized quests based on actual generated quests in the database
     * No longer duplicates filtering logic - displays only quests that were actually generated
     */
    suspend fun getTopInterestedChapters(userId: String = "default_user"): List<PersonalizedChapterQuest> {
        return try {
            // Get all generated quests (these were created by QuestGenerationService)
            val allGeneratedQuests = generatedQuestDao.getAllQuests(userId).first()
                .filter { !it.question.isNullOrBlank() } // Filter out quests without generated questions
            
            // Get chapter stats for interest scores and state determination
            val allStats = chapterStatsDao.getAllStats()
            val statsByChapter = allStats.associateBy { it.chapterId }
            
            // Convert generated quests to PersonalizedChapterQuest objects
            val personalizedQuests = mutableListOf<PersonalizedChapterQuest>()
            
            for (generatedQuest in allGeneratedQuests.take(5)) { // Limit to top 5
                try {
                    val chapter = chapterDao.getChapterById(generatedQuest.chapterId)
                    val subject = chapter?.let { subjectDao.getSubjectById(it.subjectId) }

                    
                    if (chapter != null && subject != null) {
                        // Determine quest state based on database fields
                        val questState = when {
                            generatedQuest.isCompleted -> QuestState.COMPLETED
                            generatedQuest.isUnlocked -> QuestState.UNLOCKED
                            else -> QuestState.LOCKED
                        }
                        
                        personalizedQuests.add(
                            PersonalizedChapterQuest(
                                chapterId = chapter.id,
                                chapterTitle = generatedQuest.title, // Use generated quest title instead of chapter title
                                subject = subject.name,
                                subjectIconRes = 0, // No subject icons needed
                                generatedQuestion = generatedQuest.question,
                                interestScore = 0.0, // Not displayed on UI
                                lastVisited = 0L, // Not needed
                                state = questState
                            )
                        )
                    }
                } catch (e: Exception) {
                    // Skip this quest if there's an error loading details
                    println("Error loading quest details for ${generatedQuest.id}: ${e.message}")
                }
            }
            
            // Return quests in order they were generated
            personalizedQuests
        } catch (e: Exception) {
            println("Error loading personalized quests: ${e.message}")
            emptyList()
        }
    }
}
