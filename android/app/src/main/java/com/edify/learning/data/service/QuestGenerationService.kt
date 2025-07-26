package com.edify.learning.data.service

import com.edify.learning.data.dao.ChapterStatsDao
import com.edify.learning.data.dao.GeneratedQuestDao
import com.edify.learning.data.dao.ChapterDao
import com.edify.learning.data.dao.NoteDao
import com.edify.learning.data.dao.ChatDao
import com.edify.learning.data.dao.SubjectDao
import com.edify.learning.data.model.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.first
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
     */
    suspend fun checkAndGenerateQuests(userId: String = "default_user") {
        withContext(Dispatchers.IO) {
            try {
                // Step 1: Find all high-interest chapters that haven't had quests generated
                val highInterestChapters = findHighInterestChapters(userId)
                
                if (highInterestChapters.isEmpty()) {
                    return@withContext
                }
                
                // Step 2: Get chapter interaction data for LLM context
                val chapterInteractionData = gatherChapterInteractionData(highInterestChapters)
                
                // Step 3: Ask LLM to choose strategy
                val strategyResponse = selectQuestStrategy(chapterInteractionData)
                
                // Step 4: Generate quest based on strategy
                val questResponse = generateQuest(strategyResponse, chapterInteractionData)
                
                // Step 5: Save quest and update flags
                saveGeneratedQuest(strategyResponse, questResponse, userId)
                
            } catch (e: Exception) {
                println("Error in quest generation: ${e.message}")
                e.printStackTrace()
            }
        }
    }
    
    /**
     * Step 1: Find chapters with InterestScore > 3.5 and questGenerated == false
     */
    private suspend fun findHighInterestChapters(userId: String): List<ChapterStats> {
        val allStats = chapterStatsDao.getAllStats()
        return allStats.filter { stats ->
            stats.userId == userId && 
            stats.calculateInterestScore() > 3.5 && 
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
            val response = gemmaService.generateResponse(prompt)
            gson.fromJson(response.getOrThrow(), LLMStrategyResponse::class.java)
        } catch (e: Exception) {
            // Fallback to divergent strategy with highest interest chapter
            val highestInterestChapter = chapterData.maxByOrNull { it.interestScore }
            LLMStrategyResponse(
                strategy = "Divergent",
                chapter = highestInterestChapter?.chapterId
            )
        }
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
            val response = gemmaService.generateResponse(prompt)
            gson.fromJson(response.getOrThrow(), LLMQuestResponse::class.java)
        } catch (e: Exception) {
            // Fallback quest
            LLMQuestResponse(
                title = "Connecting ${chapter1.subject} and ${chapter2.subject}",
                questionText = "How do the themes from ${chapter1.chapterTitle} relate to the concepts in ${chapter2.chapterTitle}? Explore the connections and share your insights."
            )
        }
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
            val response = gemmaService.generateResponse(prompt)
            gson.fromJson(response.getOrThrow(), LLMQuestResponse::class.java)
        } catch (e: Exception) {
            // Fallback quest
            LLMQuestResponse(
                title = "Deep Dive: ${chapter.chapterTitle}",
                questionText = "What deeper insights can you draw from ${chapter.chapterTitle}? Consider the broader implications and share your thoughtful analysis."
            )
        }
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
