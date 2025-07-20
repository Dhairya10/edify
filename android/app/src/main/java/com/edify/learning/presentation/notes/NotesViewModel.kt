package com.edify.learning.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Note
import com.edify.learning.data.model.NoteType
import com.edify.learning.data.repository.LearningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: LearningRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()
    
    private val _selectedFilter = MutableStateFlow(NoteFilter.ALL)
    val selectedFilter: StateFlow<NoteFilter> = _selectedFilter.asStateFlow()
    
    private var currentChapterId: String = ""
    
    fun loadNotes(chapterId: String) {
        currentChapterId = chapterId
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                combine(
                    repository.getNotesByChapter(chapterId),
                    _selectedFilter
                ) { notes, filter ->
                    when (filter) {
                        NoteFilter.ALL -> notes
                        NoteFilter.NOTES -> notes.filter { it.type == NoteType.USER_NOTE }
                        NoteFilter.HIGHLIGHTS -> notes.filter { it.type == NoteType.HIGHLIGHT }
                    }
                }.collect { filteredNotes ->
                    _uiState.value = _uiState.value.copy(
                        notes = filteredNotes,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load notes"
                )
            }
        }
    }
    
    fun onFilterChanged(filter: NoteFilter) {
        _selectedFilter.value = filter
    }
    
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.deleteNote(note)
                _uiState.value = _uiState.value.copy(
                    message = "Note deleted successfully"
                )
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
}

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)

enum class NoteFilter {
    ALL,
    NOTES,
    HIGHLIGHTS
}
