package com.edify.learning.presentation.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.GeneratedQuest
import com.edify.learning.data.model.PersonalizedChapterQuest
import com.edify.learning.data.model.QuestState
import com.edify.learning.data.repository.QuestRepository
import com.edify.learning.data.service.QuestGenerationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuestUiState(
    val isLoading: Boolean = false,
    val quests: List<PersonalizedChapterQuest> = emptyList(),
    val selectedQuest: GeneratedQuest? = null,
    val isSubmittingAnswer: Boolean = false,
    val error: String? = null,
    val hasQuests: Boolean = false,
    val questCount: Int = 0
)

data class QuestDisplayItem(
    val quest: GeneratedQuest,
    val chapterTitle: String,
    val subjectName: String,
    val subjectColor: String,
    val questState: QuestState
)

@HiltViewModel
class QuestViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val questGenerationService: QuestGenerationService
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(QuestUiState())
    val uiState: StateFlow<QuestUiState> = _uiState.asStateFlow()
    
    init {
        loadQuests()
    }
    
    /**
     * Load all quests for the user
     * Displays only actual generated quests from the database
     */
    private fun loadQuests() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Get personalized quests (based on actual generated quests)
                val personalizedQuests = questRepository.getTopInterestedChapters()
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    quests = personalizedQuests,
                    hasQuests = personalizedQuests.isNotEmpty(),
                    questCount = personalizedQuests.size,
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
    
    /**
     * Select a quest to view details
     */
    fun selectQuest(questId: String) {
        viewModelScope.launch {
            try {
                val quest = questRepository.getQuestById(questId)
                _uiState.value = _uiState.value.copy(selectedQuest = quest)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    /**
     * Clear selected quest
     */
    fun clearSelectedQuest() {
        _uiState.value = _uiState.value.copy(selectedQuest = null)
    }
    

    
    /**
     * Called after meaningful user actions (notes, chats, revision answers)
     * Triggers quest generation based on updated interest scores
     * Quest generation runs in background and survives navigation
     */
    fun onMeaningfulUserAction() {
        try {
            // Quest generation now runs in GlobalScope, so no need for viewModelScope
            questGenerationService.checkAndGenerateQuests()
            
            // Refresh quests after a short delay to catch any quick completions
            viewModelScope.launch {
                kotlinx.coroutines.delay(1000) // Wait 1 second
                loadQuests() // Refresh to show any newly generated quests
            }
        } catch (e: Exception) {
            println("Error triggering quest generation: ${e.message}")
            _uiState.value = _uiState.value.copy(
                error = "Failed to trigger quest generation: ${e.message}"
            )
        }
    }
    
    /**
     * Trigger quest generation manually
     * This is a more immediate trigger for testing/debugging
     */
    fun triggerQuestGeneration() {
        try {
            // Use the quest generation service directly for manual triggers
            questGenerationService.checkAndGenerateQuests()
            
            // Show loading state briefly
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            
            // Refresh quests after a delay to catch completions
            viewModelScope.launch {
                kotlinx.coroutines.delay(2000) // Wait 2 seconds for manual trigger
                loadQuests() // Refresh to show newly generated quests
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = "Failed to trigger quest generation: ${e.message}"
            )
        }
    }
    
    /**
     * Refresh quest data - called when QuestScreen becomes visible
     */
    fun refreshQuestData() {
        viewModelScope.launch {
            try {
                loadQuests()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    /**
     * Unlock a quest by updating its state in the database
     */
    fun unlockQuest(chapterId: String) {
        viewModelScope.launch {
            try {
                // Update the quest state in the database
                questRepository.unlockQuest(chapterId)
                
                // Reload quests to reflect the updated state
                loadQuests()
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to unlock quest: ${e.message}"
                )
            }
        }
    }

    /**
     * Load quest detail for a specific quest ID
     */
    fun loadQuestDetail(questId: String) {
        viewModelScope.launch {
            try {
                val quest = questRepository.getQuestById(questId)
                _uiState.value = _uiState.value.copy(
                    selectedQuest = quest,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
        }
    }
    
    /**
     * Load quest detail by chapter ID (for PersonalizedChapterQuest navigation)
     */
    fun loadQuestDetailByChapterId(chapterId: String) {
        viewModelScope.launch {
            try {
                // Show loading state while fetching
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                // Get fresh quest data from repository to ensure latest completion state
                val quest = questRepository.getQuestByChapterId(chapterId)
                
                _uiState.value = _uiState.value.copy(
                    selectedQuest = quest,
                    error = if (quest == null) "No quest found for this chapter" else null,
                    isLoading = false
                )
                
                // Also refresh the quests list to ensure UI state is consistent
                if (quest != null) {
                    loadQuests()
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    /**
     * Submit answer for a quest
     */
    fun submitQuestAnswer(questId: String, answer: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSubmittingAnswer = true)
            
            try {
                // Complete the quest in the database
                questRepository.completeQuest(questId, answer)
                
                // Get the updated quest with completion status
                val updatedQuest = questRepository.getQuestById(questId)
                
                // Update the selected quest to reflect completion immediately
                _uiState.value = _uiState.value.copy(
                    isSubmittingAnswer = false,
                    selectedQuest = updatedQuest,
                    error = null
                )
                
                // Reload the quest list to ensure home screen shows updated state
                loadQuests()
                
                // Trigger quest generation after meaningful user action
                onMeaningfulUserAction()
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSubmittingAnswer = false,
                    error = e.message
                )
            }
        }
    }

    /**
     * Get display information for a quest
     */
    suspend fun getQuestDisplayItem(quest: GeneratedQuest): QuestDisplayItem? {
        return try {
            val (chapter, subject) = questRepository.getChapterDetails(quest.chapterId)
            
            val questState = if (quest.isCompleted) {
                QuestState.COMPLETED
            } else {
                QuestState.UNLOCKED // All generated quests are unlocked
            }
            
            QuestDisplayItem(
                quest = quest,
                chapterTitle = chapter?.title ?: "Unknown Chapter",
                subjectName = quest.subjectName,
                subjectColor = when (quest.subjectName.lowercase()) {
                    "science" -> "#28a745"
                    "english" -> "#007bff"
                    "maths", "mathematics" -> "#fd7e14"
                    else -> "#6c757d"
                },
                questState = questState
            )
        } catch (e: Exception) {
            null
        }
    }
}
