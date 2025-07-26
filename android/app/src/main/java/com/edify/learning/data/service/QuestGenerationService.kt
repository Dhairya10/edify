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
    
    /**
     * Main trigger method called after every meaningful user action
     * Checks for high-interest chapters and generates quests if needed
     * Uses GlobalScope to survive navigation and lifecycle changes
     */
    fun checkAndGenerateQuests(userId: String = "default_user") {
        // Use GlobalScope to ensure quest generation survives navigation/lifecycle changes
        GlobalScope.launch(Dispatchers.IO) {
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
            }
        }
    }
    
    /**
     * Internal quest generation logic with proper cancellation checks
     */
    private suspend fun generateQuestsInternal(userId: String) {
        // Step 1: Find all high-interest chapters that haven't had quests generated
        coroutineContext.ensureActive() // Check if coroutine is still active
        val highInterestChapters = findHighInterestChapters(userId)
        println("QUEST_GEN: Found ${highInterestChapters.size} high-interest chapters.")
        
        if (highInterestChapters.isEmpty()) {
            println("QUEST_GEN: No new high-interest chapters found. Exiting.")
            return
        }
        
        // Step 2: Get chapter interaction data for LLM context
        coroutineContext.ensureActive()
        val chapterInteractionData = gatherChapterInteractionData(highInterestChapters)
        
        // Step 3: Ask LLM to choose strategy
        coroutineContext.ensureActive()
        println("QUEST_GEN: Selecting quest strategy...")
        val strategyResponse = selectQuestStrategy(chapterInteractionData)
        println("QUEST_GEN: Strategy selected: ${strategyResponse.strategy}. Chapters: ${strategyResponse.chapters?.joinToString()}, Chapter: ${strategyResponse.chapter}")
        
        // Step 4: Generate quest based on strategy
        coroutineContext.ensureActive()
        println("QUEST_GEN: Generating quest...")
        val questResponse = generateQuest(strategyResponse, chapterInteractionData)
        println("QUEST_GEN: Quest generated: '${questResponse.title}'")
        
        // Step 5: Save quest and update flags
        coroutineContext.ensureActive()
        println("QUEST_GEN: Saving generated quest...")
        saveGeneratedQuest(strategyResponse, questResponse, userId)
        println("QUEST_GEN: Quest generation and saving completed successfully.")
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
     * Step 1: Find chapters with InterestScore > 3.5 and questGenerated == false
     */
    private suspend fun findHighInterestChapters(userId: String): List<ChapterStats> {
        val allStats = chapterStatsDao.getAllStats()
        return allStats.filter { stats ->
            stats.userId == userId && 
            stats.calculateInterestScore() > 1.0 && 
            !stats.questGenerated
        }
    }
    
    /**
     * Step 2: Gather interaction data for high-interest chapters
     */
    private suspend fun gatherChapterInteractionData(chapterStatsList: List<ChapterStats>): List<ChapterInteractionData> {
        return chapterStatsList.map { stats ->
            val chapter = chapterDao.getChapterById(stats.chapterId)
            val notes = noteDao.getNotesByChapter(stats.chapterId).first()
            val chatMessages = chatDao.getMessagesBySession(stats.chapterId).first()

            ChapterInteractionData(
                chapterId = stats.chapterId,
                chapterTitle = chapter?.title ?: "Unknown Chapter",
                subject = chapter?.let { subjectDao.getSubjectById(it.subjectId)?.name } ?: "Unknown Subject",
                interestScore = stats.calculateInterestScore(),
                notes = notes.map { note -> note.content },
                chatQuestions = chatMessages.filter { !it.isFromUser }.map { message -> message.content },
                revisionAnswers = stats.revisionHistory.map { answer -> answer.userAnswer }
            )
        }
    }
    
    /**
     * Step 3: LLM chooses between Convergent and Divergent strategy
     */
    private suspend fun selectQuestStrategy(chapterData: List<ChapterInteractionData>): LLMStrategyResponse {
        val chapterSummaries = chapterData.map { 
            "${it.chapterTitle} (${it.subject}) - Interest Score: ${String.format("%.2f", it.interestScore)}"
        }.joinToString(", ")
        
        val prompt = """
            You are an AI learning coach. The user is showing strong interest in the following topics: $chapterSummaries.
            
            Your goal is to foster advanced thinking skills. Choose one of the following strategies:
            
            'Convergent': If you see a clear and powerful opportunity to connect ideas between two different subjects.
            
            'Divergent': If it's better to generate a very deep and thought-provoking question about a single topic.
            
            Respond with only the chosen strategy name in JSON format: {"strategy": "Convergent", "chapters": ["chapterId_1", "chapterId_2"]} or {"strategy": "Divergent", "chapter": "chapterId_1"}.
        """.trimIndent()
        
        return try {
            // Add timeout for LLM call to prevent hanging
            withTimeout(120000) { // 2 minute timeout for strategy selection
                coroutineContext.ensureActive() // Check cancellation before LLM call
                val response = gemmaService.generateResponse(prompt)
                gson.fromJson(response.getOrThrow(), LLMStrategyResponse::class.java)
            }
        } catch (e: TimeoutCancellationException) {
            println("QUEST_GEN: Strategy selection timed out, using fallback")
            getFallbackStrategy(chapterData)
        } catch (e: CancellationException) {
            println("QUEST_GEN: Strategy selection cancelled, using fallback")
            throw e // Re-throw cancellation to stop the whole process
        } catch (e: Exception) {
            println("QUEST_GEN: Strategy selection failed: ${e.message}, using fallback")
            getFallbackStrategy(chapterData)
        }
    }
    
    /**
     * Get fallback strategy when LLM fails
     */
    private fun getFallbackStrategy(chapterData: List<ChapterInteractionData>): LLMStrategyResponse {
        // Fallback to divergent strategy with highest interest chapter
        val highestInterestChapter = chapterData.maxByOrNull { it.interestScore }
        return LLMStrategyResponse(
            strategy = "Divergent",
            chapter = highestInterestChapter?.chapterId
        )
    }
    
    /**
     * Step 4: Generate quest based on chosen strategy
     */
    private suspend fun generateQuest(
        strategyResponse: LLMStrategyResponse,
        chapterData: List<ChapterInteractionData>
    ): LLMQuestResponse {
        return when (strategyResponse.strategy.lowercase()) {
            "convergent" -> generateConvergentQuest(strategyResponse, chapterData)
            "divergent" -> generateDivergentQuest(strategyResponse, chapterData)
            else -> generateDivergentQuest(strategyResponse, chapterData) // Default fallback
        }
    }
    
    /**
     * Generate Convergent (cross-subject) quest
     */
    private suspend fun generateConvergentQuest(
        strategyResponse: LLMStrategyResponse,
        chapterData: List<ChapterInteractionData>
    ): LLMQuestResponse {
        val chapterIds = strategyResponse.chapters ?: emptyList()
        val relevantChapters = chapterData.filter { it.chapterId in chapterIds }.take(2)
        
        if (relevantChapters.size < 2) {
            // Fallback to divergent if we don't have 2 chapters
            return generateDivergentQuest(strategyResponse, chapterData)
        }
        
        val chapter1 = relevantChapters[0]
        val chapter2 = relevantChapters[1]
        
        val prompt = """
            You are a curriculum designer specializing in interdisciplinary studies. Generate a single, thought-provoking question that connects the core themes of ${chapter1.chapterTitle} (${chapter1.subject}) and ${chapter2.chapterTitle} (${chapter2.subject}).
            
            Here is the user's specific engagement data to guide you:
            
            Chapter 1 Data: 
            - Notes: ${chapter1.notes.joinToString("; ")}
            - Chat Questions: ${chapter1.chatQuestions.joinToString("; ")}
            - Revision Answers: ${chapter1.revisionAnswers.joinToString("; ")}

            Chapter 2 Data:
            - Notes: ${chapter2.notes.joinToString("; ")}
            - Chat Questions: ${chapter2.chatQuestions.joinToString("; ")}
            - Revision Answers: ${chapter2.revisionAnswers.joinToString("; ")}

        Respond in JSON format: {"title": "A Creative Title for the Quest", "questionText": "Your generated question."}
    """.trimIndent()
    
    return try {
        // Add timeout for LLM call to prevent hanging
        withTimeout(120000) { // 2 minute timeout for quest generation
            coroutineContext.ensureActive() // Check cancellation before LLM call
            val response = gemmaService.generateResponse(prompt)
            gson.fromJson(response.getOrThrow(), LLMQuestResponse::class.java)
        }
    } catch (e: TimeoutCancellationException) {
        println("QUEST_GEN: Convergent quest generation timed out, using fallback")
        getConvergentFallbackQuest(chapter1, chapter2)
    } catch (e: CancellationException) {
        println("QUEST_GEN: Convergent quest generation cancelled")
        throw e // Re-throw cancellation to stop the whole process
    } catch (e: Exception) {
        println("QUEST_GEN: Convergent quest generation failed: ${e.message}, using fallback")
        getConvergentFallbackQuest(chapter1, chapter2)
    }
}

/**
 * Get fallback convergent quest when LLM fails
 */
private fun getConvergentFallbackQuest(
    chapter1: ChapterInteractionData,
    chapter2: ChapterInteractionData
): LLMQuestResponse {
    return LLMQuestResponse(
        title = "Connecting ${chapter1.subject} and ${chapter2.subject}",
        questionText = "How do the themes from ${chapter1.chapterTitle} relate to the concepts in ${chapter2.chapterTitle}? Explore the connections and share your insights."
    )
}

/**
 * Generate Divergent (deep single-topic) quest
 */
private suspend fun generateDivergentQuest(
    strategyResponse: LLMStrategyResponse,
    chapterData: List<ChapterInteractionData>
): LLMQuestResponse {
    val chapterId = strategyResponse.chapter ?: chapterData.maxByOrNull { it.interestScore }?.chapterId
    val chapter = chapterData.find { it.chapterId == chapterId } 
        ?: chapterData.firstOrNull()
        ?: return LLMQuestResponse("Default Quest", "Reflect on your learning journey so far.")
    
    val prompt = """
        You are an expert educator. Your goal is to foster creativity and critical thinking. Based on the user's interactions with the chapter ${chapter.chapterTitle} (${chapter.subject}), generate a single, deep question that encourages them to think beyond the text.

        Here is the user's specific engagement data:
        - Notes: ${chapter.notes.joinToString("; ")}
        - Chat Questions: ${chapter.chatQuestions.joinToString("; ")}
        - Revision Answers: ${chapter.revisionAnswers.joinToString("; ")}

        The question could relate to a core theme, a character's motivation, or a 'what if' scenario.

        Respond in JSON format: {"title": "A Creative Title for the Quest", "questionText": "Your generated question."}
    """.trimIndent()
    
    return try {
        // Add timeout for LLM call to prevent hanging
        withTimeout(120000) { // 2 minute timeout for quest generation
            coroutineContext.ensureActive() // Check cancellation before LLM call
            val response = gemmaService.generateResponse(prompt)
            gson.fromJson(response.getOrThrow(), LLMQuestResponse::class.java)
        }
    } catch (e: TimeoutCancellationException) {
        println("QUEST_GEN: Divergent quest generation timed out, using fallback")
        getDivergentFallbackQuest(chapter)
    } catch (e: CancellationException) {
        println("QUEST_GEN: Divergent quest generation cancelled")
        throw e // Re-throw cancellation to stop the whole process
    } catch (e: Exception) {
        println("QUEST_GEN: Divergent quest generation failed: ${e.message}, using fallback")
        getDivergentFallbackQuest(chapter)
    }
}

/**
 * Get fallback divergent quest when LLM fails
 */
private fun getDivergentFallbackQuest(chapter: ChapterInteractionData): LLMQuestResponse {
    return LLMQuestResponse(
        title = "Deep Dive: ${chapter.chapterTitle}",
        questionText = "What deeper insights can you draw from ${chapter.chapterTitle}? Consider the broader implications and share your thoughtful analysis."
    )
}

/**
 * Step 5: Save generated quest and update questGenerated flags
 */
private suspend fun saveGeneratedQuest(
        strategyResponse: LLMStrategyResponse,
        questResponse: LLMQuestResponse,
        userId: String
    ) {
        // Determine involved chapters
        val involvedChapters = when (strategyResponse.strategy.lowercase()) {
            "convergent" -> strategyResponse.chapters ?: emptyList()
            "divergent" -> listOfNotNull(strategyResponse.chapter)
            else -> listOfNotNull(strategyResponse.chapter)
        }
        
        // Get primary chapter details for the quest
        val primaryChapterId = involvedChapters.firstOrNull() ?: return
        val chapter = chapterDao.getChapterById(primaryChapterId)
        val subject = chapter?.let { subjectDao.getSubjectById(it.subjectId) }

        // Create and save quest
        val quest = GeneratedQuest(
            chapterId = primaryChapterId,
            subjectName = subject?.name ?: "Unknown Subject",
            title = questResponse.title,
            question = questResponse.questionText
        )
        
        generatedQuestDao.insertQuest(quest)
        
        // Update questGenerated flags for involved chapters
        involvedChapters.forEach { chapterId ->
            val stats = chapterStatsDao.getChapterStats(chapterId, userId)
            stats?.let { currentStats ->
                val updatedStats = currentStats.copy(
                    questGenerated = true,
                    updatedAt = System.currentTimeMillis()
                )
                chapterStatsDao.insertOrUpdateChapterStats(updatedStats)
            }
        }
    }
}
