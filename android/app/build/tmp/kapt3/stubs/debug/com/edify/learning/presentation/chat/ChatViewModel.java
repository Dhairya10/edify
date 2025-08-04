package com.edify.learning.presentation.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B1\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0001\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0006\u0010#\u001a\u00020$J\u0018\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020\u000f2\b\u0010\'\u001a\u0004\u0018\u00010\u000fJ\b\u0010(\u001a\u00020$H\u0002J\u0010\u0010)\u001a\u00020$2\b\u0010*\u001a\u0004\u0018\u00010\u0011J\u000e\u0010+\u001a\u00020$2\u0006\u0010,\u001a\u00020\u000fJ\u0006\u0010-\u001a\u00020$J\u0012\u0010.\u001a\u0004\u0018\u00010\u000f2\u0006\u0010/\u001a\u00020\u0011H\u0002J\u000e\u00100\u001a\u00020$2\u0006\u0010\'\u001a\u00020\u000fJ\u0006\u00101\u001a\u00020$J\u0010\u00102\u001a\u00020$2\u0006\u00103\u001a\u00020\u000fH\u0002J\u0012\u00104\u001a\u00020$2\b\b\u0002\u00105\u001a\u000206H\u0002J\b\u00107\u001a\u00020$H\u0002R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001cR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00130\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001cR\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001cR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00068"}, d2 = {"Lcom/edify/learning/presentation/chat/ChatViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/edify/learning/data/repository/LearningRepository;", "questRepository", "Lcom/edify/learning/data/repository/QuestRepository;", "userProfileRepository", "Lcom/edify/learning/data/repository/UserProfileRepository;", "chatResponseService", "Lcom/edify/learning/data/service/ChatResponseService;", "context", "Landroid/content/Context;", "(Lcom/edify/learning/data/repository/LearningRepository;Lcom/edify/learning/data/repository/QuestRepository;Lcom/edify/learning/data/repository/UserProfileRepository;Lcom/edify/learning/data/service/ChatResponseService;Landroid/content/Context;)V", "_messageInput", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_selectedImage", "Landroid/graphics/Bitmap;", "_uiState", "Lcom/edify/learning/presentation/chat/ChatUiState;", "_userInitial", "contextContent", "currentChapterId", "currentSessionId", "currentSubject", "messageInput", "Lkotlinx/coroutines/flow/StateFlow;", "getMessageInput", "()Lkotlinx/coroutines/flow/StateFlow;", "selectedImage", "getSelectedImage", "uiState", "getUiState", "userInitial", "getUserInitial", "clearError", "", "initializeChat", "chapterId", "selectedText", "loadUserProfile", "onImageSelected", "image", "onMessageInputChanged", "input", "retryLastMessage", "saveImageToInternalStorage", "bitmap", "sendExplanationRequest", "sendMessage", "setError", "message", "startLoadingWithProgress", "isExplanation", "", "stopLoading", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChatViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.LearningRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.QuestRepository questRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.UserProfileRepository userProfileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.ChatResponseService chatResponseService = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.chat.ChatUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.chat.ChatUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _messageInput = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> messageInput = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<android.graphics.Bitmap> _selectedImage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<android.graphics.Bitmap> selectedImage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _userInitial = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> userInitial = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentSessionId = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentChapterId = "";
    @org.jetbrains.annotations.Nullable()
    private java.lang.String contextContent;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String currentSubject;
    
    @javax.inject.Inject()
    public ChatViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.LearningRepository repository, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.QuestRepository questRepository, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.UserProfileRepository userProfileRepository, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.ChatResponseService chatResponseService, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
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
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<android.graphics.Bitmap> getSelectedImage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUserInitial() {
        return null;
    }
    
    public final void initializeChat(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedText) {
    }
    
    public final void onMessageInputChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String input) {
    }
    
    public final void onImageSelected(@org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap image) {
    }
    
    public final void sendMessage() {
    }
    
    public final void sendExplanationRequest(@org.jetbrains.annotations.NotNull()
    java.lang.String selectedText) {
    }
    
    public final void clearError() {
    }
    
    private final void startLoadingWithProgress(boolean isExplanation) {
    }
    
    private final void stopLoading() {
    }
    
    private final void setError(java.lang.String message) {
    }
    
    private final java.lang.String saveImageToInternalStorage(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    public final void retryLastMessage() {
    }
    
    private final void loadUserProfile() {
    }
}