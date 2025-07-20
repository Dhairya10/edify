package com.edify.learning.presentation.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.repository.LearningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val repository: LearningRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SubjectUiState())
    val uiState: StateFlow<SubjectUiState> = _uiState.asStateFlow()
    
    private val _selectedMode = MutableStateFlow(SubjectMode.LEARNING)
    val selectedMode: StateFlow<SubjectMode> = _selectedMode.asStateFlow()
    
    fun loadSubject(subjectId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val subject = repository.getSubjectById(subjectId)
                if (subject != null) {
                    _uiState.value = _uiState.value.copy(
                        subject = subject,
                        isLoading = false
                    )
                    
                    // Load chapters
                    repository.getChaptersBySubject(subjectId).collect { chapters ->
                        _uiState.value = _uiState.value.copy(chapters = chapters)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Subject not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load subject"
                )
            }
        }
    }
    
    fun onModeChanged(mode: SubjectMode) {
        _selectedMode.value = mode
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class SubjectUiState(
    val subject: Subject? = null,
    val chapters: List<Chapter> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class SubjectMode {
    LEARNING,
    REVISION
}
