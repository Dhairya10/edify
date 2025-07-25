package com.edify.learning.presentation.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Quest
import com.edify.learning.data.model.QuestQuestion
import com.edify.learning.data.model.QuestDifficulty
import com.edify.learning.data.repository.QuestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuestUiState(
    val isLoading: Boolean = false,
    val hasUnlockedPersonalizedQuests: Boolean = false,
    val introductoryQuestions: List<QuestQuestion> = emptyList(),
    val personalizedQuests: List<Quest> = emptyList(),
    val topInterestedChapters: List<PersonalizedChapterQuest> = emptyList(),
    val progressData: QuestProgressData = QuestProgressData(),
    val isGeneratingQuestion: Boolean = false,
    val error: String? = null
)

data class QuestProgressData(
    val completedActions: Int = 0,
    val totalActions: Int = 3,
    val hasAddedNote: Boolean = false,
    val hasAnsweredQuestion: Boolean = false,
    val hasChatted: Boolean = false
) {
    val progressPercentage: Float
        get() = if (totalActions > 0) completedActions.toFloat() / totalActions.toFloat() else 0f
}

data class PersonalizedChapterQuest(
    val chapterId: String,
    val chapterTitle: String,
    val subject: String,
    val subjectIconRes: String,
    val interestScore: Double,
    val description: String,
    val isGenerating: Boolean = false,
    val generatedQuest: Quest? = null,
    val generatedQuestion: String? = null,
    val error: String? = null
)

@HiltViewModel
class QuestViewModel @Inject constructor(
    private val questRepository: QuestRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(QuestUiState())
    val uiState: StateFlow<QuestUiState> = _uiState.asStateFlow()
    
    init {
        loadQuestData()
    }
    
    fun refreshQuestData() {
        loadQuestData()
    }
    
    private fun loadQuestData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Check if user has unlocked personalized quests
                val hasUnlocked = questRepository.checkPersonalizationReadiness("default_user") // TODO: Get actual user ID
                
                val introQuestions = getIntroductoryQuestions()
                val topChapters = if (hasUnlocked) {
                    loadTopInterestedChapters("default_user")
                } else {
                    emptyList()
                }
                
                // Calculate progress data for 3 impactful actions
                val progressData = calculateProgressData("default_user")
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    hasUnlockedPersonalizedQuests = hasUnlocked,
                    introductoryQuestions = introQuestions,
                    topInterestedChapters = topChapters,
                    progressData = progressData,
                    personalizedQuests = emptyList(), // Keep empty for now
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    private fun getIntroductoryQuestions(): List<QuestQuestion> {
        return listOf(
            QuestQuestion(
                id = "intro_science_1",
                questId = "intro_quest_science",
                question = "Why do leaves change color in autumn? What's really happening inside the leaf that creates this beautiful transformation?",
                subject = "Science",
                curiosityLevel = 4,
                challenges = listOf(
                    "Research and explain the role of chlorophyll in leaf color changes.",
                    "Describe what happens to other pigments (carotenoids, anthocyanins) during autumn.",
                    "Explain how temperature and daylight affect this process."
                ),
                hints = listOf(
                    "Think about what leaves need to make food",
                    "Consider what happens when days get shorter",
                    "What chemicals might be hidden in green leaves?"
                )
            ),
            QuestQuestion(
                id = "intro_english_1",
                questId = "intro_quest_english",
                question = "How do authors make you feel emotions just through words on a page? What techniques create that magic connection between reader and story?",
                subject = "English",
                curiosityLevel = 5,
                challenges = listOf(
                    "Analyze a powerful scene from your favorite book and identify the emotional techniques used.",
                    "Write a short paragraph describing a simple event (like rain) in two different emotional tones.",
                    "Find examples of metaphors, imagery, and symbolism that create emotional impact."
                ),
                hints = listOf(
                    "Think about your favorite book scene",
                    "Consider how word choice affects mood",
                    "What role do metaphors and imagery play?"
                )
            ),
            QuestQuestion(
                id = "intro_maths_1",
                questId = "intro_quest_maths",
                question = "Why does the Fibonacci sequence appear everywhere in nature - from flower petals to seashells? Is this just coincidence or something deeper?",
                subject = "Maths",
                curiosityLevel = 5,
                challenges = listOf(
                    "Find and photograph 3 examples of Fibonacci numbers in nature around you.",
                    "Calculate the ratio between consecutive Fibonacci numbers and observe the pattern.",
                    "Research and explain why this sequence might be evolutionarily advantageous."
                ),
                hints = listOf(
                    "Look for patterns in nature around you",
                    "Think about efficiency in growth",
                    "Consider the golden ratio connection"
                )
            )
        )
    }
    
    fun startQuest(questId: String) {
        // TODO: Implement quest starting logic
        viewModelScope.launch {
            // Navigate to quest detail or start quest flow
        }
    }
    
    /**
     * Generate deep exploration question for a chapter and prepare for Q&A view
     */
    fun generateDeepExplorationQuestion(chapterId: String, onQuestionGenerated: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                // Set loading state
                _uiState.value = _uiState.value.copy(isGeneratingQuestion = true)
                
                // Generate the deep exploration question using Gemma
                val question = questRepository.generateDeepExplorationQuestion(chapterId)
                
                // Update the specific chapter with the generated question
                val updatedChapters = _uiState.value.topInterestedChapters.map { chapter ->
                    if (chapter.chapterId == chapterId) {
                        chapter.copy(generatedQuestion = question)
                    } else {
                        chapter
                    }
                }
                
                // Clear loading state and update chapters
                _uiState.value = _uiState.value.copy(
                    isGeneratingQuestion = false,
                    topInterestedChapters = updatedChapters
                )
                
                // Call the callback with the generated question and chapter ID
                onQuestionGenerated(chapterId, question)
                
            } catch (e: Exception) {
                // Handle error
                _uiState.value = _uiState.value.copy(
                    isGeneratingQuestion = false,
                    error = "Failed to generate question: ${e.message}"
                )
            }
        }
    }
    
    fun exploreQuestion(question: QuestQuestion) {
        // TODO: Implement question exploration
        // This could navigate to a chat screen with the question pre-filled
        // or open a dedicated exploration interface
        viewModelScope.launch {
            // For now, just log the interaction
            // Later this will contribute to the personalization algorithm
        }
    }
    
    fun checkPersonalizationReadiness(userId: String) {
        // TODO: Implement the readiness algorithm from the documentation
        viewModelScope.launch {
            // This will be called after meaningful user actions
            // and will update hasUnlockedPersonalizedQuests flag
        }
    }
    
    fun loadQuestDetail(questId: String) {
        // Load specific quest details
        viewModelScope.launch {
            // For now, the quest details are already loaded in introductoryQuestions
            // In a real implementation, this might fetch additional data
        }
    }
    
    fun submitQuestAnswer(questId: String, answer: String) {
        viewModelScope.launch {
            try {
                questRepository.submitQuestAnswer(questId, answer)
                // TODO: Handle successful submission (e.g., navigate back or show success)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to submit answer: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Submit answer for chapter-based deep exploration question
     */
    fun submitChapterQuestAnswer(chapterId: String, answer: String) {
        viewModelScope.launch {
            try {
                // For now, we'll use the same submission logic
                // In the future, this could be enhanced to track chapter-specific quest answers
                questRepository.submitQuestAnswer(chapterId, answer)
                // TODO: Handle successful submission and update chapter engagement stats
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to submit answer: ${e.message}"
                )
            }
        }
    }
    
    private suspend fun calculateProgressData(userId: String): QuestProgressData {
        return try {
            val allChapterStats = questRepository.getAllChapterStatsForUser(userId)
            
            val hasAddedNote = allChapterStats.any { it.noteCount > 0 }
            val hasAnsweredQuestion = allChapterStats.any { it.revisionHistory.isNotEmpty() }
            val hasChatted = allChapterStats.any { it.chatHistory.isNotEmpty() }
            
            val completedActions = listOf(hasAddedNote, hasAnsweredQuestion, hasChatted).count { it }
            
            QuestProgressData(
                completedActions = completedActions,
                totalActions = 3,
                hasAddedNote = hasAddedNote,
                hasAnsweredQuestion = hasAnsweredQuestion,
                hasChatted = hasChatted
            )
        } catch (e: Exception) {
            QuestProgressData()
        }
    }
    
    private suspend fun loadTopInterestedChapters(userId: String): List<PersonalizedChapterQuest> {
        return try {
            // Get all chapter stats for the user
            val chapterStats = questRepository.getAllChapterStatsForUser(userId)
            
            // Calculate interest scores and get top 3
            val topChapters = chapterStats
                .map { stats ->
                    val interestScore = questRepository.calculateInterestScore(stats)
                    stats to interestScore
                }
                .sortedByDescending { it.second }
                .take(3)
            
            // Convert to PersonalizedChapterQuest objects
            topChapters.mapNotNull { (stats, interestScore) ->
                val chapter = questRepository.getChapterById(stats.chapterId)
                val subject = chapter?.let { questRepository.getSubjectById(it.subjectId) }
                if (chapter != null && subject != null) {
                    PersonalizedChapterQuest(
                        chapterId = stats.chapterId,
                        chapterTitle = chapter.title,
                        subject = subject.name,
                        subjectIconRes = subject.iconRes,
                        interestScore = interestScore,
                        description = "Based on your notes and high-quality answers in the revision exercises."
                    )
                } else null
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private suspend fun getSubjectName(subjectId: String): String {
        return try {
            questRepository.getSubjectById(subjectId)?.name ?: "General"
        } catch (e: Exception) {
            "General"
        }
    }
    
    fun generateQuestForChapter(chapterId: String) {
        viewModelScope.launch {
            // Update the specific chapter to show generating state
            val currentChapters = _uiState.value.topInterestedChapters
            val updatedChapters = currentChapters.map { chapter ->
                if (chapter.chapterId == chapterId) {
                    chapter.copy(isGenerating = true, error = null)
                } else {
                    chapter
                }
            }
            
            _uiState.value = _uiState.value.copy(topInterestedChapters = updatedChapters)
            
            try {
                // Generate quest for this chapter
                val quest = questRepository.generateQuestForChapter(chapterId, "default_user")
                
                // Update the chapter with the generated quest
                val finalChapters = _uiState.value.topInterestedChapters.map { chapter ->
                    if (chapter.chapterId == chapterId) {
                        chapter.copy(isGenerating = false, generatedQuest = quest, error = null)
                    } else {
                        chapter
                    }
                }
                
                _uiState.value = _uiState.value.copy(topInterestedChapters = finalChapters)
                
            } catch (e: Exception) {
                // Update the chapter with error state
                val errorChapters = _uiState.value.topInterestedChapters.map { chapter ->
                    if (chapter.chapterId == chapterId) {
                        chapter.copy(isGenerating = false, error = e.message)
                    } else {
                        chapter
                    }
                }
                
                _uiState.value = _uiState.value.copy(topInterestedChapters = errorChapters)
            }
        }
    }
}
