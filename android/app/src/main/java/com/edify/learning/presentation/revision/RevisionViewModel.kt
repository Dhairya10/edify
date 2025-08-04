package com.edify.learning.presentation.revision

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Exercise
import com.edify.learning.data.model.UserResponse
import com.edify.learning.data.model.UserAction
import com.edify.learning.data.repository.LearningRepository
import com.edify.learning.data.repository.QuestRepository
import com.edify.learning.data.service.QuestGenerationService
import com.edify.learning.data.service.RevisionEvaluationService
import com.edify.learning.data.model.RevisionSubmission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RevisionUiState(
    val isLoading: Boolean = false,
    val exercises: List<Exercise> = emptyList(),
    val userResponses: Map<Int, UserResponse> = emptyMap(),
    val revisionSubmissions: Map<Int, List<RevisionSubmission>> = emptyMap(),
    val completedCount: Int = 0,
    val isEvaluating: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class RevisionViewModel @Inject constructor(
    private val repository: LearningRepository,
    private val questRepository: QuestRepository,
    private val questGenerationService: QuestGenerationService,
    private val revisionEvaluationService: RevisionEvaluationService
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(RevisionUiState())
    val uiState: StateFlow<RevisionUiState> = _uiState.asStateFlow()
    
    private var currentChapterId: String = ""
    
    fun loadExercises(chapterId: String) {
        currentChapterId = chapterId
        viewModelScope.launch {
            println("RevisionViewModel: Starting loadExercises for chapterId: '$chapterId'")
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                println("RevisionViewModel: Calling repository.getExercisesForChapter('$chapterId')")
                
                // Load exercises from JSON
                val exercises = repository.getExercisesForChapter(chapterId)
                println("RevisionViewModel: Repository returned ${exercises.size} exercises")
                
                if (exercises.isNotEmpty()) {
                    println("RevisionViewModel: First exercise preview: '${exercises.first().question.take(50)}...'")
                    exercises.forEachIndexed { index, exercise ->
                        println("RevisionViewModel: Exercise $index: '${exercise.question.take(30)}...'")
                    }
                }
                
                if (exercises.isEmpty()) {
                    println("RevisionViewModel: No exercises found, setting error state")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        exercises = emptyList(),
                        error = "No exercises found for this chapter"
                    )
                    return@launch
                }
                
                // Load existing user responses and observe changes
                repository.getUserResponsesForChapter(chapterId)
                    .collect { responses ->
                        println("RevisionViewModel: Loaded ${responses.size} user responses")
                        
                        val responseMap = responses.associateBy { it.exerciseIndex }
                        val completedCount = responses.count { response ->
                            response.textResponse?.isNotBlank() == true || response.imageUri != null
                        }
                        
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            exercises = exercises,
                            userResponses = responseMap,
                            completedCount = completedCount,
                            error = null
                        )
                    }
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
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
                
                repository.saveUserResponse(updatedResponse)
            
            // Quest generation is already triggered in repository.saveUserResponse()
            // No need to call it again here to avoid race conditions
                
                // Update UI state
                val currentResponses = _uiState.value.userResponses.toMutableMap()
                currentResponses[exerciseIndex] = updatedResponse
                
                val completedCount = currentResponses.values.count { userResponse ->
                    userResponse.textResponse?.isNotBlank() == true || userResponse.imageUri != null
                }
                
                _uiState.value = _uiState.value.copy(
                    userResponses = currentResponses,
                    completedCount = completedCount
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to save response: ${e.message}"
                )
            }
        }
    }
    
    fun deleteUserResponse(exerciseIndex: Int) {
        viewModelScope.launch {
            try {
                val response = _uiState.value.userResponses[exerciseIndex]
                response?.let {
                    repository.deleteUserResponse(it)
                    
                    val currentResponses = _uiState.value.userResponses.toMutableMap()
                    currentResponses.remove(exerciseIndex)
                    
                    val completedCount = currentResponses.values.count { userResponse ->
                        userResponse.textResponse?.isNotBlank() == true || userResponse.imageUri != null
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        userResponses = currentResponses,
                        completedCount = completedCount
                    )
                }
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to delete response: ${e.message}"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun submitForEvaluation(chapterId: String, exerciseIndex: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isEvaluating = true)
                
                val exercise = _uiState.value.exercises.getOrNull(exerciseIndex)
                val userResponse = _uiState.value.userResponses[exerciseIndex]
                
                if (exercise == null || userResponse == null) {
                    _uiState.value = _uiState.value.copy(
                        isEvaluating = false,
                        error = "No response found to evaluate"
                    )
                    return@launch
                }
                
                val result = when {
                    !userResponse.textResponse.isNullOrBlank() -> {
                        revisionEvaluationService.evaluateTextResponse(
                            chapterId = chapterId,
                            questionIndex = exerciseIndex,
                            question = exercise.question,
                            expectedAnswer = exercise.answer,
                            studentResponse = userResponse.textResponse
                        )
                    }
                    !userResponse.imageUri.isNullOrBlank() -> {
                        revisionEvaluationService.evaluateImageResponse(
                            chapterId = chapterId,
                            questionIndex = exerciseIndex,
                            question = exercise.question,
                            expectedAnswer = exercise.answer,
                            imageUri = userResponse.imageUri
                        )
                    }
                    else -> {
                        _uiState.value = _uiState.value.copy(
                            isEvaluating = false,
                            error = "Please provide a text or image response before submitting"
                        )
                        return@launch
                    }
                }
                
                result.fold(
                    onSuccess = { submission ->
                        // Update UI state with new submission
                        val currentSubmissions = _uiState.value.revisionSubmissions.toMutableMap()
                        val questionSubmissions = currentSubmissions[exerciseIndex]?.toMutableList() ?: mutableListOf()
                        questionSubmissions.add(submission)
                        currentSubmissions[exerciseIndex] = questionSubmissions
                        
                        _uiState.value = _uiState.value.copy(
                            revisionSubmissions = currentSubmissions,
                            isEvaluating = false
                        )
                    },
                    onFailure = { exception ->
                        _uiState.value = _uiState.value.copy(
                            isEvaluating = false,
                            error = "Failed to evaluate response: ${exception.message}"
                        )
                    }
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isEvaluating = false,
                    error = "Error submitting for evaluation: ${e.message}"
                )
            }
        }
    }
    
    fun loadRevisionHistory(chapterId: String, exerciseIndex: Int) {
        viewModelScope.launch {
            try {
                val submissions = revisionEvaluationService.getRevisionHistory(chapterId, exerciseIndex)
                val currentSubmissions = _uiState.value.revisionSubmissions.toMutableMap()
                currentSubmissions[exerciseIndex] = submissions
                
                _uiState.value = _uiState.value.copy(
                    revisionSubmissions = currentSubmissions
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load revision history: ${e.message}"
                )
            }
        }
    }
}
