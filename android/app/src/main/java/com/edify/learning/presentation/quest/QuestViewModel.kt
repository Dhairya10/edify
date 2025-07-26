package com.edify.learning.presentation.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.GeneratedQuest
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
    val quests: List<GeneratedQuest> = emptyList(),
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
     */
    private fun loadQuests() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                questRepository.getAllQuests().collectLatest { quests ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        quests = quests,
                        hasQuests = quests.isNotEmpty(),
                        questCount = quests.size,
                        error = null
                    )
                }
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
    fun selectQuest(quest: GeneratedQuest) {
        _uiState.value = _uiState.value.copy(selectedQuest = quest)
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
     */
    fun onMeaningfulUserAction() {
        viewModelScope.launch {
            try {
                questGenerationService.checkAndGenerateQuests()
                loadQuests() // Refresh to show any newly generated quests
            } catch (e: Exception) {
                println("Error triggering quest generation: ${e.message}")
            }
        }
    }
    
    /**
     * Trigger quest generation manually
     */
    fun triggerQuestGeneration() {
        viewModelScope.launch {
            try {
                questRepository.triggerQuestGeneration()
                loadQuests() // Refresh to show newly generated quests
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
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
     * Submit answer for a quest
     */
    fun submitQuestAnswer(questId: String, answer: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSubmittingAnswer = true)
            
            try {
                questRepository.completeQuest(questId, answer)
                
                // Clear selected quest and refresh list
                _uiState.value = _uiState.value.copy(
                    isSubmittingAnswer = false,
                    selectedQuest = null,
                    error = null
                )
                
                // Reload quests to reflect completion
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
