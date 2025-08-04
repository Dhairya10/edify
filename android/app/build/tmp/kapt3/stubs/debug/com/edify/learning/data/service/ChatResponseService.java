package com.edify.learning.data.service;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000fH\u0002Jh\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0016\b\u0002\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u001e2\u0016\b\u0002\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u001eJ@\u0010 \u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0082@\u00a2\u0006\u0002\u0010!J\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000f0#J.\u0010$\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u000f2\u0014\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u001eH\u0002J\u0006\u0010&\u001a\u00020\fJ\u000e\u0010\'\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u000fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/edify/learning/data/service/ChatResponseService;", "", "repository", "Lcom/edify/learning/data/repository/LearningRepository;", "questRepository", "Lcom/edify/learning/data/repository/QuestRepository;", "chatDao", "Lcom/edify/learning/data/dao/ChatDao;", "gemmaService", "Lcom/edify/learning/data/service/GemmaService;", "(Lcom/edify/learning/data/repository/LearningRepository;Lcom/edify/learning/data/repository/QuestRepository;Lcom/edify/learning/data/dao/ChatDao;Lcom/edify/learning/data/service/GemmaService;)V", "isGenerating", "", "pendingResponses", "", "", "responseGenerationMutex", "Lkotlinx/coroutines/sync/Mutex;", "cleanupPendingResponse", "", "messageId", "generateChatResponse", "userMessage", "Lcom/edify/learning/data/model/ChatMessage;", "prompt", "context", "subject", "image", "Landroid/graphics/Bitmap;", "onLoadingStateChange", "Lkotlin/Function1;", "onError", "generateResponseInternal", "(Lcom/edify/learning/data/model/ChatMessage;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPendingResponseIds", "", "handleResponseGenerationFailure", "reason", "isAnyResponseGenerating", "isResponsePending", "app_debug"})
public final class ChatResponseService {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.LearningRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.QuestRepository questRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.ChatDao chatDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.GemmaService gemmaService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Mutex responseGenerationMutex = null;
    private boolean isGenerating = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.String> pendingResponses = null;
    
    @javax.inject.Inject()
    public ChatResponseService(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.LearningRepository repository, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.QuestRepository questRepository, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChatDao chatDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.GemmaService gemmaService) {
        super();
    }
    
    /**
     * Generate chat response in background that survives navigation/lifecycle changes
     * Uses same robust pattern as QuestGenerationService with subject-specific query classification
     */
    public final void generateChatResponse(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.ChatMessage userMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String prompt, @org.jetbrains.annotations.Nullable()
    java.lang.String context, @org.jetbrains.annotations.Nullable()
    java.lang.String subject, @org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap image, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onLoadingStateChange, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    /**
     * Internal response generation logic with proper coroutine context checks
     * Now uses subject-specific query classification
     */
    private final java.lang.Object generateResponseInternal(com.edify.learning.data.model.ChatMessage userMessage, java.lang.String prompt, java.lang.String context, java.lang.String subject, android.graphics.Bitmap image, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Handle response generation failures gracefully
     */
    private final void handleResponseGenerationFailure(java.lang.String messageId, java.lang.String reason, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    /**
     * Clean up pending response tracking
     */
    private final void cleanupPendingResponse(java.lang.String messageId) {
    }
    
    /**
     * Check if a response is currently being generated for a message
     */
    public final boolean isResponsePending(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId) {
        return false;
    }
    
    /**
     * Check if any response generation is currently in progress
     */
    public final boolean isAnyResponseGenerating() {
        return false;
    }
    
    /**
     * Get all pending response message IDs
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getPendingResponseIds() {
        return null;
    }
}