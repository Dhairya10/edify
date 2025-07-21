package com.edify.learning.presentation.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0015\u001a\u00020\u0016J\u0018\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\t2\b\u0010\u0019\u001a\u0004\u0018\u00010\tJ\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\tJ\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\tH\u0002J\u0006\u0010\u001d\u001a\u00020\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012\u00a8\u0006\u001e"}, d2 = {"Lcom/edify/learning/presentation/chat/ChatViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "repository", "Lcom/edify/learning/data/repository/LearningRepository;", "(Landroid/content/Context;Lcom/edify/learning/data/repository/LearningRepository;)V", "_messageInput", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_uiState", "Lcom/edify/learning/presentation/chat/ChatUiState;", "contextContent", "currentChapterId", "currentSessionId", "messageInput", "Lkotlinx/coroutines/flow/StateFlow;", "getMessageInput", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "clearError", "", "initializeChat", "chapterId", "selectedText", "onMessageInputChanged", "input", "sendExplanationRequest", "sendMessage", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChatViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.LearningRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.chat.ChatUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.chat.ChatUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _messageInput = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> messageInput = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentSessionId = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentChapterId = "";
    @org.jetbrains.annotations.Nullable()
    private java.lang.String contextContent;
    
    @javax.inject.Inject()
    public ChatViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.LearningRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.chat.ChatUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getMessageInput() {
        return null;
    }
    
    public final void initializeChat(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedText) {
    }
    
    public final void onMessageInputChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String input) {
    }
    
    public final void sendMessage() {
    }
    
    private final void sendExplanationRequest(java.lang.String selectedText) {
    }
    
    public final void clearError() {
    }
}