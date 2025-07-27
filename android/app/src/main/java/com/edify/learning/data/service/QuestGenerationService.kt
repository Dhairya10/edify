package com.edify.learning.data.service

import com.edify.learning.data.dao.ChapterStatsDao
import com.edify.learning.data.dao.GeneratedQuestDao
import com.edify.learning.data.dao.ChapterDao
import com.edify.learning.data.dao.NoteDao
import com.edify.learning.data.dao.ChatDao
import com.edify.learning.data.dao.SubjectDao
import com.edify.learning.data.model.*
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.coroutineContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestGenerationService @Inject constructor(
    private val chapterStatsDao: ChapterStatsDao,
    private val generatedQuestDao: GeneratedQuestDao,
    private val chapterDao: ChapterDao,
    private val noteDao: NoteDao,
    private val chatDao: ChatDao,
    private val subjectDao: SubjectDao,
    private val gemmaService: GemmaService
) {
    
    private val gson = Gson()
    
    // Synchronization to prevent concurrent quest generation
    private val questGenerationMutex = kotlinx.coroutines.sync.Mutex()
    private var isGenerating = false
    
    /**
     * Main trigger method called after every meaningful user action
     * Implements new algorithm: Generate divergent quests first, then convergent quests
     * Uses GlobalScope to survive navigation and lifecycle changes
     * Synchronized to prevent concurrent quest generation causing duplicates
     */
    fun checkAndGenerateQuests(userId: String = "default_user") {
        // Use GlobalScope to ensure quest generation survives navigation/lifecycle changes
        GlobalScope.launch(Dispatchers.IO) {
            // Check if quest generation is already in progress
            if (isGenerating) {
                println("QUEST_GEN: Quest generation already in progress, skipping...")
                return@launch
            }
            
            // Use mutex to ensure only one quest generation process runs at a time
            questGenerationMutex.withLock {
                isGenerating = true
                println("QUEST_GEN: Starting quest generation check...")
                try {
                    // Add timeout to prevent indefinite hanging
                    withTimeout(300000) { // 5 minute timeout
                        generateQuestsInternal(userId)
                    }
                } catch (e: TimeoutCancellationException) {
                    println("QUEST_GEN: Quest generation timed out after 5 minutes")
                    handleQuestGenerationFailure(userId, "Timeout")
                } catch (e: CancellationException) {
                    println("QUEST_GEN: Quest generation was cancelled: ${e.message}")
                    // Don't treat cancellation as an error - it's intentional
                } catch (e: Exception) {
                    println("QUEST_GEN: Error in quest generation: ${e.message}")
                    e.printStackTrace()
                    handleQuestGenerationFailure(userId, e.message ?: "Unknown error")
                } finally {
                    isGenerating = false
                    println("QUEST_GEN: Quest generation process completed.")
                }
            }
        }
    }
    
    /**
     * Internal quest generation logic implementing new algorithm:
     * 1. Generate divergent quests for eligible chapters (one per chapter)
     * 2. After all divergent quests are created, generate convergent quests
     */
    private suspend fun generateQuestsInternal(userId: String) {
        coroutineContext.ensureActive()
        
        // Step 1: Generate divergent quests for eligible chapters that don't have them yet
        val divergentQuestsGenerated = generateDivergentQuests(userId)
        
        // Step 2: If no new divergent quests were generated, try convergent quests
        if (divergentQuestsGenerated == 0) {
            coroutineContext.ensureActive()
            generateConvergentQuest(userId)
        }
        
        println("QUEST_GEN: Quest generation completed. Generated $divergentQuestsGenerated divergent quests.")
    }
    
    /**
     * Handle quest generation failures gracefully
     */
    private suspend fun handleQuestGenerationFailure(userId: String, reason: String) {
        try {
            println("QUEST_GEN: Handling failure - $reason")
            // Could implement fallback logic here, such as:
            // - Creating a simple default quest
            // - Scheduling retry for later
            // - Logging for analytics
        } catch (e: Exception) {
            println("QUEST_GEN: Error in failure handling: ${e.message}")
        }
    }
    
    /**
     * Generate divergent quests for all eligible chapters that don't have them yet
     * Returns the number of divergent quests generated
     */
    private suspend fun generateDivergentQuests(userId: String): Int {
        // Find chapters with InterestScore > 1.0 and no divergent quest generated yet
        val eligibleChapters = chapterStatsDao.getChapterStatsForUser(userId)
            .filter { stats ->
                stats.calculateInterestScore() > 1.0 && !stats.divergentQuestGenerated
            }
        
        println("QUEST_GEN: Found ${eligibleChapters.size} chapters eligible for divergent quests.")
        
        var questsGenerated = 0
        
        for (chapterStats in eligibleChapters) {
            try {
                coroutineContext.ensureActive()
                
                // Generate divergent quest for this chapter
                val chapterInteractionData = gatherChapterInteractionData(listOf(chapterStats))
                if (chapterInteractionData.isNotEmpty()) {
                    val questResponse = generateDivergentQuestForChapter(chapterInteractionData.first())
                    saveDivergentQuest(questResponse, chapterStats, userId)
                    questsGenerated++
                    println("QUEST_GEN: Generated divergent quest for chapter ${chapterStats.chapterId}: '${questResponse.title}'")
                }
            } catch (e: Exception) {
                println("QUEST_GEN: Failed to generate divergent quest for chapter ${chapterStats.chapterId}: ${e.message}")
                // Continue with next chapter instead of failing completely
            }
        }
        
        return questsGenerated
    }
    
    /**
     * Gather interaction data for chapters
     */
    private suspend fun gatherChapterInteractionData(chapterStatsList: List<ChapterStats>): List<ChapterInteractionData> {
        return chapterStatsList.map { stats ->
            val chapter = chapterDao.getChapterById(stats.chapterId)
            val subject = chapter?.let { subjectDao.getSubjectById(it.subjectId) }
            val notes = noteDao.getNotesByChapter(stats.chapterId).first()
            val chatMessages = chatDao.getMessagesByChapter(stats.chapterId).first()
            
            ChapterInteractionData(
                chapterId = stats.chapterId,
                chapterTitle = chapter?.title ?: "Unknown Chapter",
                chapterDescription = chapter?.description ?: "No description available",
                subject = subject?.name ?: "Unknown Subject",
                interestScore = stats.calculateInterestScore(),
                notes = notes.map { it.content },
                chatQuestions = chatMessages.filter { !it.isFromUser }.map { it.content },
                revisionAnswers = stats.revisionHistory.map { "${it.userAnswer} (Score: ${it.gemmaAnalysis?.correctnessScore ?: 0})" }
            )
        }
    }
    
    /**
     * Generate a divergent quest for a specific chapter
     */
    private suspend fun generateDivergentQuestForChapter(chapterData: ChapterInteractionData): LLMQuestResponse {
        val prompt = """
            You are an expert educator. Your goal is to foster creativity and critical thinking. Generate a single, deep question for the chapter "${chapterData.chapterTitle}" (${chapterData.subject}) that encourages students to think beyond the text.
            
            Chapter Summary: ${chapterData.chapterDescription}

            The question should be a DIVERGENT quest that encourages deep exploration of this specific topic. It could relate to a core theme, implications, or a 'what if' scenario.

            IMPORTANT: You MUST respond with ONLY valid JSON in this exact format:
            {"title": "A Creative Title for the Quest", "questionText": "Your generated question."}
            
            Do not include any other text, explanations, or formatting. Only return the JSON object.
        """.trimIndent()
        
        return try {
            withTimeout(300000) { // 5 minute timeout for quest generation
                coroutineContext.ensureActive()
                val response = gemmaService.generateResponse(prompt)
                val responseText = response.getOrThrow()
                println("QUEST_GEN: Divergent quest RAW response from Gemma: '$responseText'")
                
                // Clean response by removing markdown code blocks if present
                val cleanedResponse = responseText
                    .trim()
                    .removePrefix("```json")
                    .removePrefix("```")
                    .removeSuffix("```")
                    .trim()
                
                println("QUEST_GEN: Cleaned response: '$cleanedResponse'")
                gson.fromJson(cleanedResponse, LLMQuestResponse::class.java)
            }
        } catch (e: TimeoutCancellationException) {
            println("QUEST_GEN: Divergent quest generation timed out - no quest will be generated")
            throw e
        } catch (e: CancellationException) {
            println("QUEST_GEN: Divergent quest generation cancelled")
            throw e
        } catch (e: Exception) {
            println("QUEST_GEN: Divergent quest generation failed: ${e.message} - no quest will be generated")
            throw e
        }
    }
    

    
    /**
     * Save a divergent quest and update chapter stats
     */
    private suspend fun saveDivergentQuest(
        questResponse: LLMQuestResponse,
        chapterStats: ChapterStats,
        userId: String
    ) {
        // Get chapter and subject details
        val chapter = chapterDao.getChapterById(chapterStats.chapterId)
        val subject = chapter?.let { subjectDao.getSubjectById(it.subjectId) }
        
        // Create and save the divergent quest
        val quest = GeneratedQuest(
            chapterId = chapterStats.chapterId,
            subjectName = subject?.name ?: "Unknown Subject",
            title = questResponse.title,
            question = questResponse.questionText,
            questType = "divergent",
            involvedChapterIds = chapterStats.chapterId, // Single chapter for divergent
            userId = userId
        )
        
        generatedQuestDao.insertQuest(quest)
        
        // Update chapter stats to mark divergent quest as generated
        val updatedStats = chapterStats.copy(
            divergentQuestGenerated = true,
            questGenerated = true,
            updatedAt = System.currentTimeMillis()
        )
        chapterStatsDao.insertOrUpdateChapterStats(updatedStats)
    }
    
    /**
     * Generate convergent quest when all eligible divergent quests are created
     * Uses past quest data to create new, different questions
     */
    private suspend fun generateConvergentQuest(userId: String) {
        // Find chapters with high interest scores (eligible for convergent quests)
        val eligibleChapters = chapterStatsDao.getChapterStatsForUser(userId)
            .filter { stats -> stats.calculateInterestScore() > 1.0 }
            .sortedByDescending { it.calculateInterestScore() }
        
        if (eligibleChapters.size < 2) {
            println("QUEST_GEN: Not enough eligible chapters for convergent quest (${eligibleChapters.size})")
            return
        }
        
        // Select up to 10 chapters for LLM to choose from (randomly sample if more than 10)
        val chaptersForSelection = if (eligibleChapters.size <= 10) {
            eligibleChapters
        } else {
            eligibleChapters.shuffled().take(10)
        }
        
        // Generate chapter interaction data for all candidate chapters
        val chapterInteractionData = gatherChapterInteractionData(chaptersForSelection)
        
        if (chapterInteractionData.size >= 2) {
            try {
                coroutineContext.ensureActive()
                
                // Get all chapter IDs for past quest lookup
                val allChapterIds = chaptersForSelection.map { it.chapterId }
                val pastQuests = generatedQuestDao.getPastQuestsForChapters(allChapterIds, allChapterIds.first(), userId)
                
                val questResponse = generateConvergentQuestWithLLMSelection(chapterInteractionData, pastQuests, userId)
                
                // Find the selected chapters based on LLM response
                val selectedChapterIds = questResponse.selectedChapterIds ?: emptyList()
                val selectedChapters = chaptersForSelection.filter { it.chapterId in selectedChapterIds }
                
                if (selectedChapters.size >= 2) {
                    saveConvergentQuest(questResponse, selectedChapters, userId)
                    println("QUEST_GEN: Generated convergent quest: '${questResponse.title}' with chapters: ${selectedChapterIds.joinToString()}")
                } else {
                    println("QUEST_GEN: LLM did not select valid chapters for convergent quest")
                }
            } catch (e: Exception) {
                println("QUEST_GEN: Failed to generate convergent quest: ${e.message}")
            }
        }
    }
    
    /**
     * Generate convergent quest with LLM selecting 2 chapters from provided options
     */
    private suspend fun generateConvergentQuestWithLLMSelection(
        chapterData: List<ChapterInteractionData>,
        pastQuests: List<GeneratedQuest>,
        userId: String
    ): LLMQuestResponse {
        // Create context about past quests to avoid repetition
        val pastQuestContext = if (pastQuests.isNotEmpty()) {
            "\n\nPrevious questions asked about these topics (avoid similar themes):\n" +
            pastQuests.take(5).joinToString("\n") { "- ${it.title}: ${it.question}" }
        } else {
            ""
        }
        
        // Create chapter options for LLM to choose from
        val chapterOptions = chapterData.mapIndexed { index, data ->
            "${index + 1}. Chapter ID: ${data.chapterId} | Title: ${data.chapterTitle} | Subject: ${data.subject} | Interest Score: ${String.format("%.2f", data.interestScore)}\n   Summary: ${data.chapterDescription}"
        }.joinToString("\n\n")
        
        val prompt = """
            You are a curriculum designer specializing in interdisciplinary studies. Your task is to:
            1. Choose exactly 2 chapters from the list below that would create the most meaningful cross-subject connection
            2. Generate a single, thought-provoking CONVERGENT question that connects the core themes across these 2 chapters
            
            Available chapters to choose from:
            $chapterOptions
            $pastQuestContext
            
            Instructions:
            - Select exactly 2 chapters that offer the best opportunity for interdisciplinary synthesis
            - Create a question that connects ideas across the selected subjects
            - Ensure the question is different from any previous questions listed above
            
            IMPORTANT: You MUST respond with ONLY valid JSON in this exact format:
            {
                "selectedChapterIds": ["chapter_id_1", "chapter_id_2"],
                "title": "A Creative Title for the Quest",
                "questionText": "Your generated convergent question connecting the two selected chapters."
            }
            
            Use the exact Chapter IDs from the list above. Do not include any other text, explanations, or formatting. Only return the JSON object. 
            Do not mention chapter IDs anywhere in the text. Try to come up with an innovative title that can combine the topics creatively.
        """.trimIndent()
        
        return try {
            withTimeout(300000) { // 5 minute timeout for quest generation
                coroutineContext.ensureActive()
                val response = gemmaService.generateResponse(prompt)
                val responseText = response.getOrThrow()
                println("QUEST_GEN: Convergent quest with LLM selection RAW response from Gemma: '$responseText'")
                
                // Clean response by removing markdown code blocks if present
                val cleanedResponse = responseText
                    .trim()
                    .removePrefix("```json")
                    .removePrefix("```")
                    .removeSuffix("```")
                    .trim()
                
                println("QUEST_GEN: Cleaned response: '$cleanedResponse'")
                gson.fromJson(cleanedResponse, LLMQuestResponse::class.java)
            }
        } catch (e: TimeoutCancellationException) {
            println("QUEST_GEN: Convergent quest generation with LLM selection timed out - no quest will be generated")
            throw e
        } catch (e: CancellationException) {
            println("QUEST_GEN: Convergent quest generation with LLM selection cancelled")
            throw e
        } catch (e: Exception) {
            println("QUEST_GEN: Convergent quest generation with LLM selection failed: ${e.message} - no quest will be generated")
            throw e
        }
    }
    
    /**
     * Save a convergent quest and update chapter stats
     */
    private suspend fun saveConvergentQuest(
        questResponse: LLMQuestResponse,
        chapterStatsList: List<ChapterStats>,
        userId: String
    ) {
        // Use the first chapter as the primary chapter for the quest
        val primaryChapter = chapterStatsList.first()
        val chapter = chapterDao.getChapterById(primaryChapter.chapterId)
        val subject = chapter?.let { subjectDao.getSubjectById(it.subjectId) }
        
        // Get all involved chapter IDs
        val involvedChapterIds = chapterStatsList.map { it.chapterId }.joinToString(",")
        
        // Create and save the convergent quest
        val quest = GeneratedQuest(
            chapterId = primaryChapter.chapterId,
            subjectName = "Cross-Subject", // Convergent quests span multiple subjects
            title = questResponse.title,
            question = questResponse.questionText,
            questType = "convergent",
            involvedChapterIds = involvedChapterIds,
            userId = userId
        )
        
        generatedQuestDao.insertQuest(quest)
        
        // Update chapter stats for all involved chapters
        chapterStatsList.forEach { chapterStats ->
            val updatedStats = chapterStats.copy(
                questGenerated = true,
                updatedAt = System.currentTimeMillis()
            )
            chapterStatsDao.insertOrUpdateChapterStats(updatedStats)
        }
    }
}
