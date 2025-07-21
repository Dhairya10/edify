package com.edify.learning.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Note
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.repository.LearningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: LearningRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()
    
    private val _selectedSubjectFilter = MutableStateFlow<String?>(null)
    val selectedSubjectFilter: StateFlow<String?> = _selectedSubjectFilter.asStateFlow()
    
    // Cache for subjects and chapters to avoid repeated queries
    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    private val _chapters = MutableStateFlow<List<Chapter>>(emptyList())
    
    init {
        loadInitialData()
    }
    
    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // First get all subjects
                val subjects = repository.getAllSubjects().first()
                _subjects.value = subjects
                
                // Get all chapters for all subjects
                val allChapters = mutableListOf<Chapter>()
                subjects.forEach { subject ->
                    val chapters = repository.getChaptersBySubject(subject.id).first()
                    allChapters.addAll(chapters)
                }
                _chapters.value = allChapters
                
                // Get all user notes
                val notes = repository.getAllUserNotes().first()
                
                // Create notes with subject info
                val notesWithSubject = notes.mapNotNull { note ->
                    val chapter = allChapters.find { it.id == note.chapterId }
                    val subject = subjects.find { it.id == chapter?.subjectId }
                    
                    if (chapter != null && subject != null) {
                        NoteWithSubjectInfo(
                            note = note,
                            subjectId = subject.id,
                            subjectName = subject.name,
                            subjectColor = subject.color,
                            subjectIcon = subject.iconRes,
                            chapterTitle = chapter.title
                        )
                    } else null
                }
                
                _uiState.value = _uiState.value.copy(
                    notes = notesWithSubject,
                    subjects = subjects,
                    isLoading = false
                )
                
                // Apply initial filter
                filterNotes()
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load notes"
                )
            }
        }
    }
    
    fun onSubjectFilterChanged(subjectId: String?) {
        _selectedSubjectFilter.value = subjectId
        filterNotes()
    }
    
    private fun filterNotes() {
        viewModelScope.launch {
            val selectedSubject = _selectedSubjectFilter.value
            val allNotes = _uiState.value.notes
            
            val filteredNotes = if (selectedSubject == null) {
                allNotes
            } else {
                allNotes.filter { it.subjectId == selectedSubject }
            }
            
            _uiState.value = _uiState.value.copy(filteredNotes = filteredNotes)
        }
    }
    
    fun deleteNote(noteWithSubject: NoteWithSubjectInfo) {
        viewModelScope.launch {
            try {
                repository.deleteNote(noteWithSubject.note)
                _uiState.value = _uiState.value.copy(
                    message = "Note deleted successfully"
                )
                // Reload data after deletion
                loadInitialData()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete note"
                )
            }
        }
    }
    
    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun refreshNotes() {
        loadInitialData()
    }
}

data class NotesUiState(
    val notes: List<NoteWithSubjectInfo> = emptyList(),
    val filteredNotes: List<NoteWithSubjectInfo> = emptyList(),
    val subjects: List<Subject> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
) {
    val displayNotes: List<NoteWithSubjectInfo>
        get() = if (filteredNotes.isNotEmpty() || notes.isEmpty()) filteredNotes else notes
}

// Data class for notes with subject information (moved from separate file for simplicity)
data class NoteWithSubjectInfo(
    val note: Note,
    val subjectId: String,
    val subjectName: String,
    val subjectColor: String,
    val subjectIcon: String,
    val chapterTitle: String
)
