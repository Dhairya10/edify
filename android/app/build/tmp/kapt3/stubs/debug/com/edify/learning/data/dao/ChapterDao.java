package com.edify.learning.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ \u0010\u0010\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000e0\u00162\u0006\u0010\u0011\u001a\u00020\u0007H\'J\u0016\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u0019\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\u001a\u001a\u00020\u00032\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u001c\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000e0\u00162\u0006\u0010\u001e\u001a\u00020\u0007H\'J\u0016\u0010\u001f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ(\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020#2\b\b\u0002\u0010$\u001a\u00020%H\u00a7@\u00a2\u0006\u0002\u0010&J(\u0010\'\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u00072\u0006\u0010(\u001a\u00020)2\b\b\u0002\u0010$\u001a\u00020%H\u00a7@\u00a2\u0006\u0002\u0010*\u00a8\u0006+"}, d2 = {"Lcom/edify/learning/data/dao/ChapterDao;", "", "deleteAllChapters", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteById", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteChapter", "chapter", "Lcom/edify/learning/data/model/Chapter;", "(Lcom/edify/learning/data/model/Chapter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllChapters", "", "getChapterById", "getChapterByNumber", "subjectId", "chapterNumber", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChaptersBySubject", "Lkotlinx/coroutines/flow/Flow;", "getCompletedChaptersCount", "getTotalChaptersCount", "insertChapter", "insertChapters", "chapters", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchChapters", "query", "updateChapter", "updateCompletionStatus", "chapterId", "isCompleted", "", "timestamp", "", "(Ljava/lang/String;ZJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateReadingProgress", "progress", "", "(Ljava/lang/String;FJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface ChapterDao {
    
    @androidx.room.Query(value = "SELECT * FROM chapters ORDER BY subjectId ASC, chapterNumber ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllChapters(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.Chapter>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM chapters WHERE subjectId = :subjectId ORDER BY chapterNumber ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Chapter>> getChaptersBySubject(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId);
    
    @androidx.room.Query(value = "SELECT * FROM chapters WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChapterById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.Chapter> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM chapters WHERE subjectId = :subjectId AND chapterNumber = :chapterNumber")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChapterByNumber(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId, int chapterNumber, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.Chapter> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM chapters WHERE title LIKE \'%\' || :query || \'%\' OR description LIKE \'%\' || :query || \'%\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Chapter>> searchChapters(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertChapter(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Chapter chapter, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertChapters(@org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.Chapter> chapters, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateChapter(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Chapter chapter, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteChapter(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Chapter chapter, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM chapters WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM chapters")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllChapters(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE chapters SET readingProgress = :progress, updatedAt = :timestamp WHERE id = :chapterId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateReadingProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, float progress, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE chapters SET isCompleted = :isCompleted, updatedAt = :timestamp WHERE id = :chapterId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateCompletionStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, boolean isCompleted, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM chapters WHERE subjectId = :subjectId AND isCompleted = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCompletedChaptersCount(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM chapters WHERE subjectId = :subjectId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalChaptersCount(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}