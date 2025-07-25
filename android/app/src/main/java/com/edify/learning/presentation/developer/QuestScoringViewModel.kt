package com.edify.learning.presentation.developer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.domain.service.ChapterInterestScore
import com.edify.learning.domain.service.DetailedScoring
import com.edify.learning.domain.service.QuestScoringService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestScoringViewModel @Inject constructor(
    private val questScoringService: QuestScoringService
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(QuestScoringUiState())
    val uiState: StateFlow<QuestScoringUiState> = _uiState.asStateFlow()
    
    fun loadQuestScoring() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val chapterRankings = questScoringService.getChaptersRankedByInterest()
                val isReadyForQuests = questScoringService.isUserReadyForPersonalizedQuests()
                
                _uiState.value = _uiState.value.copy(
                    chapterRankings = chapterRankings,
                    isReadyForQuests = isReadyForQuests,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
    
    fun refreshData() {
        loadQuestScoring()
    }
    
    fun showDetailedScoring(chapterId: String) {
        viewModelScope.launch {
            try {
                val detailedScoring = questScoringService.getDetailedScoring(chapterId)
                _uiState.value = _uiState.value.copy(
                    detailedScoring = detailedScoring,
                    showDetailedScoring = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun hideDetailedScoring() {
        _uiState.value = _uiState.value.copy(
            showDetailedScoring = false,
            detailedScoring = null
        )
    }
}

data class QuestScoringUiState(
    val chapterRankings: List<ChapterInterestScore> = emptyList(),
    val isReadyForQuests: Boolean = false,
    val detailedScoring: DetailedScoring? = null,
    val showDetailedScoring: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
