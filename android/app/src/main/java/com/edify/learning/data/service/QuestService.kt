package com.edify.learning.data.service

import com.edify.learning.data.dao.ChapterDao
import com.edify.learning.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestService @Inject constructor(
    private val chapterDao: ChapterDao
) {
    
    /**
     * Analyze chat question for curiosity score
     * Note: LLM integration temporarily disabled to prevent MediaPipe crashes
     */
    suspend fun analyzeChatCuriosity(question: String): Int {
        // TODO: Re-enable Gemma integration after fixing MediaPipe JNI issues
        // For now, return a mock score based on question characteristics
        return when {
            question.contains("why", ignoreCase = true) -> 4
            question.contains("how", ignoreCase = true) -> 3
            question.contains("what if", ignoreCase = true) -> 5
            question.contains("explain", ignoreCase = true) -> 2
            question.length > 50 -> 3
            else -> 2
        }
    }
    
    /**
     * Analyze revision answer for correctness and originality
     * Note: LLM integration temporarily disabled to prevent MediaPipe crashes
     */
    suspend fun analyzeRevisionAnswer(
        originalQuestion: String,
        userAnswer: String
    ): GemmaRevisionAnalysis {
        // TODO: Re-enable Gemma integration after fixing MediaPipe JNI issues
        // For now, return mock analysis
        val correctnessScore = when {
            userAnswer.length < 10 -> 1
            userAnswer.length < 30 -> 2
            userAnswer.length < 60 -> 3
            userAnswer.length < 100 -> 4
            else -> 5
        }
        
        val originalityScore = when {
            userAnswer.contains("because", ignoreCase = true) -> 3
            userAnswer.contains("I think", ignoreCase = true) -> 4
            userAnswer.contains("example", ignoreCase = true) -> 4
            userAnswer.split(" ").size > 20 -> 4
            else -> 2
        }
        
        return GemmaRevisionAnalysis(
            correctnessScore = correctnessScore,
            originalityScore = originalityScore
        )
    }
    
    /**
     * Generate personalized quest questions based on user's interests
     */
    suspend fun generatePersonalizedQuests(
        userId: String,
        chapterStats: List<ChapterStats>
    ): List<Quest> {
        try {
            // Step 1: Calculate InterestScore for each chapter and get top 5
            val topChapters = getTop5ChaptersByInterestScore(chapterStats)
            
            if (topChapters.isEmpty()) {
                return emptyList()
            }
            
            // Step 2: Generate curiosity-driven questions for each top chapter
            val personalizedQuests = mutableListOf<Quest>()
            
            topChapters.forEachIndexed { index, (chapterStats, interestScore) ->
                val chapter = chapterDao.getChapterById(chapterStats.chapterId)
                if (chapter != null) {
                    val questQuestion = generateCuriosityQuestionForChapter(
                        chapter = chapter,
                        interestScore = interestScore,
                        userEngagement = chapterStats
                    )
                    
                    val quest = Quest(
                        id = "personalized_quest_${userId}_${chapter.id}",
                        title = "${chapter.title} Deep Dive",
                        question = questQuestion,
                        subject = getSubjectNameFromChapter(chapter)
                    )
                    
                    personalizedQuests.add(quest)
                }
            }
            
            return personalizedQuests
            
        } catch (e: Exception) {
            // Fallback to empty list if generation fails
            return emptyList()
        }
    }
    
    /**
     * Get top 5 chapters by InterestScore
     */
    private suspend fun getTop5ChaptersByInterestScore(
        chapterStats: List<ChapterStats>
    ): List<Pair<ChapterStats, Double>> {
        return chapterStats
            .map { stats ->
                val interestScore = calculateInterestScore(stats)
                Pair(stats, interestScore)
            }
            .filter { it.second > 0.0 } // Only include chapters with some engagement
            .sortedByDescending { it.second } // Sort by InterestScore descending
            .take(5) // Take top 5
    }
    
    /**
     * Calculate InterestScore using the weighted formula from documentation
     */
    private fun calculateInterestScore(chapterStats: ChapterStats): Double {
        // Weights from documentation (updated to match Quest - Scoring Algo.md)
        val wRevision = 0.15
        val wChat = 0.4
        val wNotes = 0.25
        val wVisits = 0.2
        
        val revisionScore = calculateRevisionScoreInternal(chapterStats.revisionHistory)
        val chatScore = calculateChatScoreInternal(chapterStats.chatHistory)
        val noteScore = minOf(chapterStats.noteCount.toDouble(), 5.0)
        val visitScore = minOf(chapterStats.visitCount.toDouble(), 5.0)
        
        return (wRevision * revisionScore) + 
               (wChat * chatScore) + 
               (wNotes * noteScore) + 
               (wVisits * visitScore)
    }
    
    /**
     * Calculate revision score from revision history
     */
    private fun calculateRevisionScoreInternal(revisionHistory: List<RevisionEntry>): Double {
        if (revisionHistory.isEmpty()) return 0.0
        
        val scores = revisionHistory.mapNotNull { entry ->
            entry.gemmaAnalysis?.let { analysis ->
                (analysis.correctnessScore + analysis.originalityScore) / 2.0
            }
        }
        
        return if (scores.isNotEmpty()) scores.average() else 0.0
    }
    
    /**
     * Calculate chat score from chat history
     */
    private fun calculateChatScoreInternal(chatHistory: List<ChatEntry>): Double {
        if (chatHistory.isEmpty()) return 0.0
        
        val scores = chatHistory.mapNotNull { entry ->
            entry.gemmaAnalysis?.curiosityScore?.toDouble()
        }
        
        return if (scores.isNotEmpty()) scores.average() else 0.0
    }
    
    /**
     * Generate deep exploration question for a specific chapter
     * Note: LLM integration temporarily disabled to prevent MediaPipe crashes
     * This creates thoughtful, open-ended questions that encourage deep thinking
     */
    suspend fun generateDeepExplorationQuestion(
        chapter: Chapter,
        subject: String
    ): String {
        // TODO: Re-enable Gemma integration after fixing MediaPipe JNI issues
        // For now, return default deep exploration questions
        return getDefaultDeepExplorationQuestion(chapter, subject)
    }
    
    /**
     * Generate curiosity-driven question for a specific chapter
     * Note: LLM integration temporarily disabled to prevent MediaPipe crashes
     */
    suspend fun generateCuriosityQuestionForChapter(
        chapter: Chapter,
        interestScore: Double,
        userEngagement: ChapterStats
    ): String {
        // TODO: Re-enable Gemma integration after fixing MediaPipe JNI issues
        // For now, return a default question
        return getDefaultCuriosityQuestion(chapter)
    }
    
    /**
     * Build a sophisticated prompt for Gemma to generate curiosity-driven questions
     */
    private fun buildCuriosityPrompt(
        chapter: Chapter,
        interestScore: Double,
        userEngagement: ChapterStats
    ): String {
        val engagementContext = buildEngagementContext(userEngagement)
        
        return """
            You are an expert educational content creator specializing in curiosity-driven learning.
            
            CONTEXT:
            - Chapter: "${chapter.title}"
            - Description: "${chapter.description}"
            - User Interest Score: ${String.format("%.2f", interestScore)}/5.0 (High interest)
            - User Engagement: $engagementContext
            
            TASK:
            Generate ONE thought-provoking, curiosity-driven question that:
            1. Builds on the user's demonstrated interest in this chapter
            2. Encourages deeper exploration beyond the textbook content
            3. Connects to real-world applications or fascinating implications
            4. Is appropriate for a curious student who has engaged with this material
            5. Sparks "I wonder what would happen if..." or "Why does this work this way?" thinking
            
            QUESTION STYLE:
            - Start with curiosity triggers like "What if...", "Why do you think...", "How might..."
            - Be specific to the chapter content but encourage broader thinking
            - Avoid simple factual recall questions
            - Make it personally engaging and thought-provoking
            
            Generate only the question, no additional text or explanation.
        """.trimIndent()
    }
    
    /**
     * Build engagement context for the prompt
     */
    private fun buildEngagementContext(userEngagement: ChapterStats): String {
        val engagementParts = mutableListOf<String>()
        
        if (userEngagement.visitCount > 0) {
            engagementParts.add("${userEngagement.visitCount} visits")
        }
        if (userEngagement.noteCount > 0) {
            engagementParts.add("${userEngagement.noteCount} notes taken")
        }
        if (userEngagement.chatHistory.isNotEmpty()) {
            engagementParts.add("${userEngagement.chatHistory.size} chat interactions")
        }
        if (userEngagement.revisionHistory.isNotEmpty()) {
            engagementParts.add("${userEngagement.revisionHistory.size} revision attempts")
        }
        
        return if (engagementParts.isNotEmpty()) {
            engagementParts.joinToString(", ")
        } else {
            "Basic engagement"
        }
    }
    
    /**
     * Build a sophisticated prompt for generating deep exploration questions
     */
    private fun buildDeepExplorationPrompt(chapter: Chapter, subject: String): String {
        return """
        You are an expert educator creating thought-provoking questions for deep learning.
        
        Chapter: ${chapter.title}
        Subject: $subject
        Description: ${chapter.description}
        
        Create ONE thoughtful, open-ended question that:
        - Encourages deep exploration of the topic
        - Connects to real-world applications or implications
        - Promotes critical thinking and analysis
        - Is suitable for reflective, long-form answers
        - Avoids simple yes/no or factual recall questions
        
        The question should help students think beyond the basic concepts and explore the deeper meaning, connections, or implications of the topic.
        
        Return ONLY the question, nothing else.
        """.trimIndent()
    }
    
    /**
     * Extract the question from Gemma's response, removing any extra text
     */
    private fun extractQuestionFromResponse(response: String): String {
        // Clean up the response to get just the question
        val cleaned = response.trim()
            .replace("\n", " ")
            .replace("  ", " ")
        
        // If response contains multiple sentences, try to get the question
        val sentences = cleaned.split(".")
        val questionSentence = sentences.find { it.contains("?") }
        
        return if (questionSentence != null) {
            questionSentence.trim() + if (!questionSentence.endsWith("?")) "?" else ""
        } else {
            // If no question mark found, take the first meaningful sentence
            sentences.firstOrNull { it.length > 10 }?.trim() + "?" ?: cleaned
        }
    }
    
    /**
     * Get default deep exploration question if Gemma generation fails
     */
    private fun getDefaultDeepExplorationQuestion(chapter: Chapter, subject: String): String {
        return when (subject.lowercase()) {
            "science" -> "How might the concepts in '${chapter.title}' influence future scientific discoveries or technological innovations?"
            "english" -> "What deeper themes or human experiences does '${chapter.title}' reveal, and how do they connect to our modern world?"
            "maths", "mathematics" -> "How do the mathematical principles in '${chapter.title}' appear in unexpected places in our daily lives or natural world?"
            else -> "What aspects of '${chapter.title}' challenge your current understanding, and what questions does it raise for you?"
        }
    }
    
    /**
     * Get default curiosity question if Gemma generation fails
     */
    private fun getDefaultCuriosityQuestion(chapter: Chapter): String {
        return "What aspects of ${chapter.title} do you find most intriguing and why?"
    }
    
    /**
     * Get subject name from chapter (placeholder - would need subject lookup)
     */
    private fun getSubjectNameFromChapter(chapter: Chapter): String {
        // TODO: Implement proper subject lookup
        // For now, return a default based on common patterns
        return when {
            chapter.title.contains("physics", ignoreCase = true) || 
            chapter.title.contains("chemistry", ignoreCase = true) || 
            chapter.title.contains("biology", ignoreCase = true) -> "Science"
            chapter.title.contains("literature", ignoreCase = true) || 
            chapter.title.contains("grammar", ignoreCase = true) || 
            chapter.title.contains("writing", ignoreCase = true) -> "English"
            chapter.title.contains("algebra", ignoreCase = true) || 
            chapter.title.contains("geometry", ignoreCase = true) || 
            chapter.title.contains("calculus", ignoreCase = true) -> "Maths"
            else -> "General"
        }
    }
    
    
    /**
     * Create or update chapter stats entry
     */
    fun createChapterStatsId(chapterId: String, userId: String): String {
        return "${chapterId}_${userId}"
    }
    
    /**
     * Get sample quest questions for different subjects
     */
    fun getSampleQuestsBySubject(subject: String): List<QuestQuestion> {
        return when (subject.lowercase()) {
            "science" -> listOf(
                QuestQuestion(
                    id = "science_quest_1",
                    questId = "science_exploration",
                    question = "What would happen if gravity was half as strong on Earth?",
                    subject = "Science"
                ),
                QuestQuestion(
                    id = "science_quest_2",
                    questId = "science_exploration",
                    question = "Why do some materials conduct electricity while others don't?",
                    subject = "Science"
                )
            )
            "english" -> listOf(
                QuestQuestion(
                    id = "english_quest_1",
                    questId = "english_exploration",
                    question = "How do different cultures express the same emotion through language?",
                    subject = "English"
                ),
                QuestQuestion(
                    id = "english_quest_2",
                    questId = "english_exploration",
                    question = "What makes a story memorable across generations?",
                    subject = "English"
                )
            )
            "maths" -> listOf(
                QuestQuestion(
                    id = "maths_quest_1",
                    questId = "maths_exploration",
                    question = "How do mathematicians discover new mathematical truths?",
                    subject = "Maths"
                ),
                QuestQuestion(
                    id = "maths_quest_2",
                    questId = "maths_exploration",
                    question = "Why does mathematics work so well to describe the physical world?",
                    subject = "Maths"
                )
            )
            else -> emptyList()
        }
    }
}
