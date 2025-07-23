package com.edify.learning.presentation.notes;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u000e\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aJ\b\u0010\u001b\u001a\u00020\u0016H\u0002J\b\u0010\u001c\u001a\u00020\u0016H\u0002J\u0010\u0010\u001d\u001a\u00020\u00162\b\u0010\u001e\u001a\u0004\u0018\u00010\nJ\u0006\u0010\u001f\u001a\u00020\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012\u00a8\u0006 "}, d2 = {"Lcom/edify/learning/presentation/notes/NotesViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/edify/learning/data/repository/LearningRepository;", "(Lcom/edify/learning/data/repository/LearningRepository;)V", "_chapters", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/edify/learning/data/model/Chapter;", "_selectedSubjectFilter", "", "_subjects", "Lcom/edify/learning/data/model/Subject;", "_uiState", "Lcom/edify/learning/presentation/notes/NotesUiState;", "selectedSubjectFilter", "Lkotlinx/coroutines/flow/StateFlow;", "getSelectedSubjectFilter", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "clearError", "", "clearMessage", "deleteNote", "noteWithSubject", "Lcom/edify/learning/presentation/notes/NoteWithSubjectInfo;", "filterNotes", "loadInitialData", "onSubjectFilterChanged", "subjectId", "refreshNotes", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class NotesViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.LearningRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.notes.NotesUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.notes.NotesUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedSubjectFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedSubjectFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.edify.learning.data.model.Subject>> _subjects = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.edify.learning.data.model.Chapter>> _chapters = null;
    
    @javax.inject.Inject()
    public NotesViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.LearningRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.notes.NotesUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedSubjectFilter() {
        return null;
    }
    
    private final void loadInitialData() {
    }
    
    public final void onSubjectFilterChanged(@org.jetbrains.annotations.Nullable()
    java.lang.String subjectId) {
    }
    
    private final void filterNotes() {
    }
    
    public final void deleteNote(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.notes.NoteWithSubjectInfo noteWithSubject) {
    }
    
    public final void clearMessage() {
    }
    
    public final void clearError() {
    }
    
    public final void refreshNotes() {
    }
}