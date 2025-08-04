package com.edify.learning.data.service;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 -2\u00020\u0001:\u0001-B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002JD\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J4\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\f0\u000f2\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0082@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001dJD\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001f\u0010\u0018J<\u0010 \u001a\b\u0012\u0004\u0012\u00020\f0\u000f2\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010!\u001a\u00020\"H\u0082@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b#\u0010$J$\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00100&2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\'\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010(J\u0012\u0010)\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0016\u001a\u00020\fH\u0002J\u000e\u0010*\u001a\u00020+H\u0086@\u00a2\u0006\u0002\u0010,R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006."}, d2 = {"Lcom/edify/learning/data/service/RevisionEvaluationService;", "", "context", "Landroid/content/Context;", "gemmaService", "Lcom/edify/learning/data/service/GemmaService;", "promptService", "Lcom/edify/learning/data/service/PromptService;", "revisionSubmissionDao", "Lcom/edify/learning/data/dao/RevisionSubmissionDao;", "(Landroid/content/Context;Lcom/edify/learning/data/service/GemmaService;Lcom/edify/learning/data/service/PromptService;Lcom/edify/learning/data/dao/RevisionSubmissionDao;)V", "createFallbackEvaluation", "", "studentResponse", "evaluateImageResponse", "Lkotlin/Result;", "Lcom/edify/learning/data/model/RevisionSubmission;", "chapterId", "questionIndex", "", "question", "expectedAnswer", "imageUri", "evaluateImageResponse-hUnOzRk", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "evaluateImageWithGemma", "imageBitmap", "Landroid/graphics/Bitmap;", "evaluateImageWithGemma-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "evaluateTextResponse", "evaluateTextResponse-hUnOzRk", "evaluateWithGemma", "isImageResponse", "", "evaluateWithGemma-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRevisionHistory", "", "exerciseIndex", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadBitmapFromUri", "processAllPendingEvaluations", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class RevisionEvaluationService {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.GemmaService gemmaService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.PromptService promptService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.RevisionSubmissionDao revisionSubmissionDao = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "RevisionEvaluationSvc";
    @org.jetbrains.annotations.NotNull()
    public static final com.edify.learning.data.service.RevisionEvaluationService.Companion Companion = null;
    
    @javax.inject.Inject()
    public RevisionEvaluationService(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.GemmaService gemmaService, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.PromptService promptService, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.RevisionSubmissionDao revisionSubmissionDao) {
        super();
    }
    
    private final java.lang.String createFallbackEvaluation(java.lang.String studentResponse) {
        return null;
    }
    
    private final android.graphics.Bitmap loadBitmapFromUri(java.lang.String imageUri) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRevisionHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, int exerciseIndex, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.RevisionSubmission>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object processAllPendingEvaluations(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/edify/learning/data/service/RevisionEvaluationService$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}