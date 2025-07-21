package com.edify.learning.presentation.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.model.Exercise
import com.edify.learning.data.model.UserResponse
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
        
        // Load exercises when switching to revision mode
        if (mode == SubjectMode.REVISION) {
            loadExercisesForSubject()
        }
    }
    
    private fun loadExercisesForSubject() {
        viewModelScope.launch {
            try {
                val chapters = _uiState.value.chapters
                val chapterExercises = mutableListOf<ChapterExercises>()
                
                println("SubjectViewModel: Loading exercises for ${chapters.size} chapters")
                
                chapters.forEach { chapter ->
                    println("SubjectViewModel: Loading exercises for chapter: ${chapter.id} - ${chapter.title}")
                    val exercises = repository.getExercisesForChapter(chapter.id)
                    println("SubjectViewModel: Found ${exercises.size} exercises for chapter ${chapter.id}")
                    
                    if (exercises.isNotEmpty()) {
                        chapterExercises.add(
                            ChapterExercises(
                                chapterId = chapter.id,
                                chapterTitle = chapter.title,
                                exercises = exercises
                            )
                        )
                        println("SubjectViewModel: Added ${exercises.size} exercises for chapter ${chapter.title}")
                    } else {
                        println("SubjectViewModel: No exercises found for chapter ${chapter.id}")
                    }
                }
                
                println("SubjectViewModel: Total chapter exercises loaded: ${chapterExercises.size}")
                
                // Update UI state with exercises
                _uiState.value = _uiState.value.copy(
                    exercises = chapterExercises
                )
                
            } catch (e: Exception) {
                println("SubjectViewModel: Error loading exercises: ${e.message}")
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load exercises: ${e.message}"
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
}

data class SubjectUiState(
    val subject: Subject? = null,
    val chapters: List<Chapter> = emptyList(),
    val exercises: List<ChapterExercises> = emptyList(),
    val userResponses: Map<String, UserResponse> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ChapterExercises(
    val chapterId: String,
    val chapterTitle: String,
    val exercises: List<Exercise>
)

enum class SubjectMode {
    LEARNING,
    REVISION
}
