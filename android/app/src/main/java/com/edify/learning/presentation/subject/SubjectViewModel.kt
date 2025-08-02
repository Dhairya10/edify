package com.edify.learning.presentation.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.model.Exercise
import com.edify.learning.data.model.UserResponse
import com.edify.learning.data.model.RevisionResponse
import com.edify.learning.data.model.ChapterRevisionData
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
        
        // Load revision data when switching to revision mode
        if (mode == SubjectMode.REVISION) {
            loadRevisionDataForSubject()
        }
    }
    
    private fun loadRevisionDataForSubject() {
        viewModelScope.launch {
            try {
                val subject = _uiState.value.subject ?: return@launch
                
                println("SubjectViewModel: Loading revision data for subject: ${subject.id}")
                
                val revisionData = repository.getRevisionDataForSubject(subject.id)
                println("SubjectViewModel: Found revision data for ${revisionData.size} chapters")
                
                revisionData.forEach { chapterData ->
                    println("SubjectViewModel: Chapter ${chapterData.chapterTitle} has ${chapterData.questions.size} questions")
                }
                
                // Update UI state with revision data
                _uiState.value = _uiState.value.copy(
                    revisionData = revisionData
                )
                
                // Load existing revision responses
                loadRevisionResponses(revisionData)
                
            } catch (e: Exception) {
                println("SubjectViewModel: Error loading revision data: ${e.message}")
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load revision data: ${e.message}"
                )
            }
        }
    }
    
    private fun loadRevisionResponses(revisionData: List<ChapterRevisionData>) {
        viewModelScope.launch {
            try {
                val revisionResponses = mutableMapOf<String, RevisionResponse>()
                
                revisionData.forEach { chapterData ->
                    repository.getRevisionResponsesForChapter(chapterData.chapterId).collect { responses ->
                        responses.forEach { response ->
                            val key = "${response.chapterId}_${response.questionIndex}"
                            revisionResponses[key] = response
                        }
                        
                        _uiState.value = _uiState.value.copy(
                            revisionResponses = revisionResponses
                        )
                    }
                }
                
            } catch (e: Exception) {
                println("SubjectViewModel: Error loading revision responses: ${e.message}")
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
    
    fun updateRevisionResponse(chapterId: String, questionIndex: Int, userAnswer: String, imageUri: String? = null) {
        viewModelScope.launch {
            try {
                // Get existing response or create new one
                val existingResponse = repository.getRevisionResponse(chapterId, questionIndex)
                val revisionData = _uiState.value.revisionData
                val chapterData = revisionData.find { it.chapterId == chapterId }
                val question = chapterData?.questions?.getOrNull(questionIndex)?.question ?: ""
                
                val updatedResponse = existingResponse?.copy(
                    userAnswer = userAnswer,
                    imageUri = imageUri,
                    isSubmitted = false,
                    updatedAt = System.currentTimeMillis()
                ) ?: RevisionResponse(
                    chapterId = chapterId,
                    questionIndex = questionIndex,
                    question = question,
                    userAnswer = userAnswer,
                    imageUri = imageUri,
                    isSubmitted = false
                )
                
                // Save to database
                if (existingResponse != null) {
                    repository.updateRevisionResponse(updatedResponse)
                } else {
                    repository.saveRevisionResponse(updatedResponse)
                }
                
                // Update UI state
                val key = "${chapterId}_${questionIndex}"
                val updatedResponses = _uiState.value.revisionResponses.toMutableMap()
                updatedResponses[key] = updatedResponse
                
                _uiState.value = _uiState.value.copy(revisionResponses = updatedResponses)
                
            } catch (e: Exception) {
                println("SubjectViewModel: Error updating revision response: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    error = "Failed to save response: ${e.message}"
                )
            }
        }
    }
    
    fun submitRevisionResponseForReview(chapterId: String, questionIndex: Int, userAnswer: String, imageUri: String? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSubmittingAnswer = true)
            
            try {
                val result = repository.submitRevisionResponseForReview(chapterId, questionIndex, userAnswer, imageUri)
                
                result.fold(
                    onSuccess = { feedback ->
                        // Update UI state with the reviewed response
                        val existingResponse = repository.getRevisionResponse(chapterId, questionIndex)
                        existingResponse?.let { response ->
                            val key = "${chapterId}_${questionIndex}"
                            val updatedResponses = _uiState.value.revisionResponses.toMutableMap()
                            updatedResponses[key] = response
                            
                            _uiState.value = _uiState.value.copy(
                                revisionResponses = updatedResponses,
                                isSubmittingAnswer = false,
                                lastSubmissionResult = "Success"
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isSubmittingAnswer = false,
                            error = "Failed to get feedback: ${error.message}"
                        )
                    }
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSubmittingAnswer = false,
                    error = "Error submitting answer: ${e.message}"
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
    val revisionData: List<ChapterRevisionData> = emptyList(),
    val revisionResponses: Map<String, RevisionResponse> = emptyMap(),
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

enum class SubjectMode {
    LEARNING,
    REVISION
}
