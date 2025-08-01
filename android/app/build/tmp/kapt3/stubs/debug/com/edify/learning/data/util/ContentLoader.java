package com.edify.learning.data.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u001c\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u000b\u001a\u00020\bH\u0002J\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0012\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013J$\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013J*\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00150\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\b2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\bH\u0002J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\bJ\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\r2\u0006\u0010\u0007\u001a\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/edify/learning/data/util/ContentLoader;", "", "()V", "json", "Lkotlinx/serialization/json/Json$Default;", "estimateReadingTime", "", "content", "", "getDirectoryAndFilename", "Lkotlin/Pair;", "chapterId", "loadAllSubjects", "", "Lcom/edify/learning/data/model/Subject;", "context", "Landroid/content/Context;", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadChapterSummaryForChat", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadChaptersForSubject", "Lcom/edify/learning/data/model/Chapter;", "subjectId", "loadChaptersFromDirectory", "directoryPath", "loadExercisesForChapter", "Lcom/edify/learning/data/model/Exercise;", "parseExercisesFromContent", "app_debug"})
public final class ContentLoader {
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.serialization.json.Json.Default json = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.edify.learning.data.util.ContentLoader INSTANCE = null;
    
    private ContentLoader() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadAllSubjects(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.Subject>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadChaptersForSubject(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String subjectId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.Chapter>> $completion) {
        return null;
    }
    
    private final java.util.List<com.edify.learning.data.model.Chapter> loadChaptersFromDirectory(android.content.Context context, java.lang.String directoryPath, java.lang.String subjectId) {
        return null;
    }
    
    private final int estimateReadingTime(java.lang.String content) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.Exercise> loadExercisesForChapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
        return null;
    }
    
    private final kotlin.Pair<java.lang.String, java.lang.String> getDirectoryAndFilename(java.lang.String chapterId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadChapterSummaryForChat(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.util.List<com.edify.learning.data.model.Exercise> parseExercisesFromContent(java.lang.String content) {
        return null;
    }
}