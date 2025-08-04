package com.edify.learning.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.repository.LearningRepository
import com.edify.learning.data.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: LearningRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _personalizedGreeting = MutableStateFlow("Welcome,")
    val personalizedGreeting: StateFlow<String> = _personalizedGreeting.asStateFlow()
    
    private val _userName = MutableStateFlow("there")
    val userName: StateFlow<String> = _userName.asStateFlow()
    
    init {
        initializeData()
        observeSubjects()
        loadPersonalizedGreeting()
    }
    
    private fun initializeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Initialize Gemma model
                repository.initializeGemma()
                
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
    
    /**
     * Refresh the personalized greeting based on current time
     * Call this when user navigates to home screen
     */
    fun refreshGreeting() {
        loadPersonalizedGreeting()
    }
    
    private fun loadPersonalizedGreeting() {
        viewModelScope.launch {
            try {
                val userName = userProfileRepository.getUserName()
                val greeting = getPersonalizedGreeting(userName)
                _personalizedGreeting.value = greeting
                _userName.value = if (userName != "there") userName else "there"
            } catch (e: Exception) {
                _personalizedGreeting.value = "Welcome,"
                _userName.value = "there"
            }
        }
    }
    
    private fun getPersonalizedGreeting(userName: String): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        
        val baseGreeting = when (hour) {
            in 5..11 -> {
                val morningGreetings = listOf(
                    "Good morning",
                    "Rise and shine",
                    "Fresh start ahead",
                    "Hello, early bird",
                    "Morning sunshine",
                    "Ready to seize the day?",
                    "Another beautiful morning",
                    "Time to make things happen",
                    "Bright and early today",
                    "Morning motivation incoming",
                    "Let's start strong"
                )
                
                val daySpecificGreetings = when (dayOfWeek) {
                    Calendar.MONDAY -> listOf("Monday motivation", "Fresh week ahead", "New week, new goals")
                    Calendar.FRIDAY -> listOf("Friday energy", "End the week strong", "Almost weekend")
                    Calendar.SATURDAY, Calendar.SUNDAY -> listOf("Weekend warrior", "Relaxed morning", "Weekend learning")
                    else -> emptyList()
                }
                
                (morningGreetings + daySpecificGreetings).random()
            }
            in 12..16 -> {
                listOf(
                    "Good afternoon",
                    "Keep the energy up",
                    "Afternoon productivity mode",
                    "Hope your day's going well",
                    "Afternoon achiever",
                    "Steady progress continues",
                    "Power through the afternoon",
                    "Making moves this afternoon?",
                    "Afternoon focus time",
                    "How's the day treating you?"
                ).random()
            }
            in 17..20 -> {
                listOf(
                    "Good evening",
                    "Time to wind down and grow",
                    "Evening reflection time",
                    "Hope today was fulfilling",
                    "Perfect time for progress",
                    "Evening inspiration awaits",
                    "Peaceful evening ahead",
                    "Let's make this evening count"
                ).random()
            }
            in 21..23, in 0..4 -> {
                listOf(
                    "Welcome, night owl",
                    "Burning the midnight oil?",
                    "Late night dedication",
                    "Hello there, midnight warrior",
                    "Night shift in full swing",
                    "Stars are out, so are you",
                    "Late night, big dreams",
                    "When others sleep, you grow",
                    "Moonlight motivation",
                    "Night time is your time"
                ).random()
            }
            else -> "Welcome"
        }
        
        return "$baseGreeting"
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
