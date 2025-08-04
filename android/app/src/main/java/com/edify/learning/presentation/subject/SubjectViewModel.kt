package com.edify.learning.presentation.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.model.Exercise
import com.edify.learning.data.model.UserResponse
import com.edify.learning.data.model.RevisionSubmission
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
    
    
    
    fun updateUserResponse(chapterId: String, exerciseIndex: Int, response: UserResponse) {
        viewModelScope.launch {
            try {
                val updatedResponse = response.copy(
                    chapterId = chapterId,
                    exerciseIndex = exerciseIndex,
                    updatedAt = System.currentTimeMillis()
                )
                
                // Save to database
                repository.saveUserResponse(updatedResponse)
                println("SubjectViewModel: Saved user response for chapter $chapterId, exercise $exerciseIndex")
                
                // Update UI state
                val key = "${chapterId}_$exerciseIndex"
                val updatedResponses = _uiState.value.userResponses.toMutableMap()
                updatedResponses[key] = updatedResponse
                
                _uiState.value = _uiState.value.copy(userResponses = updatedResponses)
                
            } catch (e: Exception) {
                println("SubjectViewModel: Error saving user response: ${e.message}")
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    error = "Failed to save response: ${e.message}"
                )
            }
        }
    }
    
    
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun clearSubmissionResult() {
        _uiState.value = _uiState.value.copy(lastSubmissionResult = null)
    }
}

data class SubjectUiState(
    val subject: Subject? = null,
    val chapters: List<Chapter> = emptyList(),
    val exercises: List<ChapterExercises> = emptyList(),
    val userResponses: Map<String, UserResponse> = emptyMap(),
    val isLoading: Boolean = false,
    val isSubmittingAnswer: Boolean = false,
    val lastSubmissionResult: String? = null,
    val error: String? = null
)

data class ChapterExercises(
    val chapterId: String,
    val chapterTitle: String,
    val exercises: List<Exercise>
)
