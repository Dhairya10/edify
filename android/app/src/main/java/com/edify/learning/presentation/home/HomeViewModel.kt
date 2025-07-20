package com.edify.learning.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.repository.LearningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: LearningRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    init {
        initializeData()
        observeSubjects()
    }
    
    private fun initializeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Initialize Gemma model
                repository.initializeGemma()
                
                // Initialize content data from JSON files
                repository.initializeContentData()
                
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to initialize data"
                )
            }
        }
    }
    
    private fun observeSubjects() {
        viewModelScope.launch {
            combine(
                repository.getAllSubjects(),
                _searchQuery
            ) { subjects, query ->
                if (query.isBlank()) {
                    val continueReadingSubject = getContinueReadingSubject(subjects)
                    val lastReadChapter = continueReadingSubject?.lastReadChapterId?.let { chapterId ->
                        repository.getChapterById(chapterId)
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        subjects = subjects,
                        searchResults = emptyList(),
                        continueReadingSubject = continueReadingSubject,
                        lastReadChapter = lastReadChapter
                    )
                } else {
                    // Search in subjects
                    val filteredSubjects = subjects.filter { subject ->
                        subject.name.contains(query, ignoreCase = true) ||
                        subject.description.contains(query, ignoreCase = true)
                    }
                    
                    // Search in chapters
                    searchChapters(query, subjects)
                    
                    val continueReadingSubject = getContinueReadingSubject(subjects)
                    val lastReadChapter = continueReadingSubject?.lastReadChapterId?.let { chapterId ->
                        repository.getChapterById(chapterId)
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        subjects = filteredSubjects,
                        continueReadingSubject = continueReadingSubject,
                        lastReadChapter = lastReadChapter
                    )
                }
            }.collect { }
        }
    }
    
    private fun searchChapters(query: String, subjects: List<Subject>) {
        viewModelScope.launch {
            repository.searchChapters(query).collect { chapters ->
                val searchResults = chapters.map { chapter ->
                    val subject = subjects.find { it.id == chapter.subjectId }
                    SearchResult(
                        type = SearchResultType.CHAPTER,
                        id = chapter.id,
                        title = chapter.title,
                        subtitle = "${subject?.name ?: "Unknown"} • Chapter ${chapter.chapterNumber}",
                        description = chapter.description,
                        subjectId = chapter.subjectId
                    )
                }
                
                _uiState.value = _uiState.value.copy(
                    searchResults = searchResults
                )
            }
        }
    }
    
    private suspend fun getContinueReadingSubject(subjects: List<Subject>): Subject? {
        return subjects
            .filter { it.lastReadChapterId != null }
            .maxByOrNull { it.updatedAt }
    }
    
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun refreshData() {
        initializeData()
    }
}

data class HomeUiState(
    val subjects: List<Subject> = emptyList(),
    val continueReadingSubject: Subject? = null,
    val lastReadChapter: Chapter? = null,
    val searchResults: List<SearchResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class SearchResult(
    val type: SearchResultType,
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val subjectId: String
)

enum class SearchResultType {
    SUBJECT, CHAPTER
}
