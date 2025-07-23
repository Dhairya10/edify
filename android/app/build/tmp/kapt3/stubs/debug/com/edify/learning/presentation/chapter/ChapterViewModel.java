package com.edify.learning.presentation.chapter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0017J\u0006\u0010\u0019\u001a\u00020\u0017J\u000e\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\tJ\u0016\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\tJ\u0016\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u0007J\u0006\u0010!\u001a\u00020\u0017J\u001e\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\t2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%J\u000e\u0010\'\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\tJ\u000e\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020*R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000f\u00a8\u0006+"}, d2 = {"Lcom/edify/learning/presentation/chapter/ChapterViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/edify/learning/data/repository/LearningRepository;", "(Lcom/edify/learning/data/repository/LearningRepository;)V", "_isImageSelected", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_selectedContent", "", "_selectedText", "_uiState", "Lcom/edify/learning/presentation/chapter/ChapterUiState;", "isImageSelected", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "selectedContent", "getSelectedContent", "selectedText", "getSelectedText", "uiState", "getUiState", "clearContentSelection", "", "clearError", "clearMessage", "loadChapter", "chapterId", "onAddNote", "title", "content", "onContentSelected", "isImage", "onExplainContent", "onHighlightText", "text", "startOffset", "", "endOffset", "onTextSelected", "updateReadingProgress", "progress", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChapterViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.LearningRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.chapter.ChapterUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.chapter.ChapterUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedText = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedText = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedContent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedContent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isImageSelected = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isImageSelected = null;
    
    @javax.inject.Inject()
    public ChapterViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.LearningRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.chapter.ChapterUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedText() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedContent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isImageSelected() {
        return null;
    }
    
    public final void loadChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
    }
    
    public final void onTextSelected(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void onContentSelected(@org.jetbrains.annotations.NotNull()
    java.lang.String content, boolean isImage) {
    }
    
    public final void clearContentSelection() {
    }
    
    public final void onExplainContent() {
    }
    
    public final void onHighlightText(@org.jetbrains.annotations.NotNull()
    java.lang.String text, int startOffset, int endOffset) {
    }
    
    public final void onAddNote(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String content) {
    }
    
    public final void updateReadingProgress(float progress) {
    }
    
    public final void clearMessage() {
    }
    
    public final void clearError() {
    }
}