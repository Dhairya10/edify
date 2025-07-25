package com.edify.learning.presentation.chapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.model.Note
import com.edify.learning.data.model.NoteType
import com.edify.learning.data.model.ContentItem
import com.edify.learning.data.repository.LearningRepository
import com.edify.learning.data.repository.QuestRepository
import com.edify.learning.data.repository.UserAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChapterViewModel @Inject constructor(
    private val repository: LearningRepository,
    private val questRepository: QuestRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ChapterUiState())
    val uiState: StateFlow<ChapterUiState> = _uiState.asStateFlow()
    
    private val _selectedText = MutableStateFlow("")
    val selectedText: StateFlow<String> = _selectedText.asStateFlow()
    
    private val _selectedContent = MutableStateFlow("")
    val selectedContent: StateFlow<String> = _selectedContent.asStateFlow()
    
    private val _isImageSelected = MutableStateFlow(false)
    val isImageSelected: StateFlow<Boolean> = _isImageSelected.asStateFlow()
    
    fun loadChapter(chapterId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val chapter = repository.getChapterById(chapterId)
                if (chapter != null) {
                    _uiState.value = _uiState.value.copy(
                        chapter = chapter,
                        isLoading = false
                    )
                    
                    // Update last read chapter
                    repository.updateLastReadChapter(chapter.subjectId, chapterId)
                    
                    // Track chapter visit for quest personalization
                    questRepository.updateChapterStats(
                        chapterId = chapterId,
                        userId = "default_user", // TODO: Get actual user ID
                        action = UserAction.Visit
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Chapter not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load chapter"
                )
            }
        }
    }
    
    fun onTextSelected(text: String) {
        _selectedText.value = text
    }
    
    fun onContentSelected(content: String, isImage: Boolean) {
        _selectedContent.value = content
        _isImageSelected.value = isImage
        if (!isImage) {
            _selectedText.value = content
        }
    }
    
    fun clearContentSelection() {
        _selectedContent.value = ""
        _isImageSelected.value = false
        _selectedText.value = ""
    }
    
    fun onExplainContent() {
        // This will be handled by the navigation to chat with selected content
        val content = _selectedContent.value
        if (content.isNotBlank()) {
            // The ChapterScreen will handle navigation with the selected content
        }
    }
    
    fun onHighlightText(text: String, startOffset: Int, endOffset: Int) {
        val chapter = _uiState.value.chapter ?: return
        
        viewModelScope.launch {
            try {
                val contentItem = ContentItem.TextContent(
                    blockId = "highlight_${UUID.randomUUID()}",
                    text = text,
                    startOffset = startOffset,
                    endOffset = endOffset,
                    selectedText = text
                )
                
                val note = Note(
                    id = UUID.randomUUID().toString(),
                    chapterId = chapter.id,
                    title = "Highlighted Text",
                    content = listOf(contentItem).toString(),
                    type = NoteType.HIGHLIGHT
                )
                
                repository.insertNote(note)
                
                _uiState.value = _uiState.value.copy(
                    message = "Text highlighted successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to highlight text"
                )
            }
        }
    }
    
    fun onAddNote(title: String, content: String) {
        val chapter = _uiState.value.chapter ?: return
        
        viewModelScope.launch {
            try {
                val note = Note(
                    id = UUID.randomUUID().toString(),
                    chapterId = chapter.id,
                    title = title,
                    content = content, // Store the actual text content directly
                    type = NoteType.USER_NOTE
                )
                
                repository.insertNote(note)
                
                // Track note creation for quest personalization
                questRepository.updateChapterStats(
                    chapterId = chapter.id,
                    userId = "default_user", // TODO: Get actual user ID
                    action = UserAction.SaveNote
                )
                
                _uiState.value = _uiState.value.copy(
                    message = "Note added successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to add note"
                )
            }
        }
    }
    
    fun updateReadingProgress(progress: Float) {
        val chapter = _uiState.value.chapter ?: return
        
        viewModelScope.launch {
            try {
                repository.updateChapterProgress(chapter.id, progress)
                
                _uiState.value = _uiState.value.copy(
                    chapter = chapter.copy(readingProgress = progress)
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to update progress"
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

data class ChapterUiState(
    val chapter: Chapter? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)
