package com.edify.learning.presentation.chapter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u001eJ\u0006\u0010 \u001a\u00020\u001eJ\u000e\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020\rJ\u000e\u0010#\u001a\u00020\u001eH\u0082@\u00a2\u0006\u0002\u0010$J\u0016\u0010%\u001a\u00020\u001e2\u0006\u0010&\u001a\u00020\r2\u0006\u0010\'\u001a\u00020\rJ\u0016\u0010(\u001a\u00020\u001e2\u0006\u0010\'\u001a\u00020\r2\u0006\u0010)\u001a\u00020\u000bJ\u0006\u0010*\u001a\u00020\u001eJ\u001e\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020\r2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.J\u000e\u00100\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020\rJ\u000e\u00101\u001a\u00020\u001e2\u0006\u00102\u001a\u000203R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\r0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00100\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\r0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/edify/learning/presentation/chapter/ChapterViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/edify/learning/data/repository/LearningRepository;", "questRepository", "Lcom/edify/learning/data/repository/QuestRepository;", "userProfileRepository", "Lcom/edify/learning/data/repository/UserProfileRepository;", "(Lcom/edify/learning/data/repository/LearningRepository;Lcom/edify/learning/data/repository/QuestRepository;Lcom/edify/learning/data/repository/UserProfileRepository;)V", "_isImageSelected", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_selectedContent", "", "_selectedText", "_uiState", "Lcom/edify/learning/presentation/chapter/ChapterUiState;", "_userLanguagePreference", "isImageSelected", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "selectedContent", "getSelectedContent", "selectedText", "getSelectedText", "uiState", "getUiState", "userLanguagePreference", "getUserLanguagePreference", "clearContentSelection", "", "clearError", "clearMessage", "loadChapter", "chapterId", "loadUserLanguagePreference", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onAddNote", "title", "content", "onContentSelected", "isImage", "onExplainContent", "onHighlightText", "text", "startOffset", "", "endOffset", "onTextSelected", "updateReadingProgress", "progress", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChapterViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.LearningRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.QuestRepository questRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.UserProfileRepository userProfileRepository = null;
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
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _userLanguagePreference = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> userLanguagePreference = null;
    
    @javax.inject.Inject()
    public ChapterViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.LearningRepository repository, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.QuestRepository questRepository, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.UserProfileRepository userProfileRepository) {
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
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUserLanguagePreference() {
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
    
    private final java.lang.Object loadUserLanguagePreference(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}