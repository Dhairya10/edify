package com.edify.learning.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\f\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\"\u0010\u000e\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\f2\u0006\u0010\u0011\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0014J*\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0017\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0018J*\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0017\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u001a\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u001b\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u001c"}, d2 = {"Lcom/edify/learning/data/dao/ChapterStatsDao;", "", "deleteById", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteChapterStats", "chapterStats", "Lcom/edify/learning/data/model/ChapterStats;", "(Lcom/edify/learning/data/model/ChapterStats;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllStats", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChapterStats", "chapterId", "", "userId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChapterStatsByChapter", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChapterStatsForUser", "incrementNoteCount", "timestamp", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "incrementVisitCount", "insertOrUpdate", "insertOrUpdateChapterStats", "app_debug"})
@androidx.room.Dao()
public abstract interface ChapterStatsDao {
    
    @androidx.room.Query(value = "SELECT * FROM chapter_stats ORDER BY lastVisited DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.ChapterStats>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM chapter_stats WHERE userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChapterStatsForUser(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.ChapterStats>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM chapter_stats WHERE chapterId = :chapterId AND userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChapterStats(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.ChapterStats> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM chapter_stats WHERE chapterId = :chapterId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChapterStatsByChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.ChapterStats> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrUpdateChapterStats(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.ChapterStats chapterStats, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrUpdate(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.ChapterStats chapterStats, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE chapter_stats SET visitCount = visitCount + 1, lastVisited = :timestamp, updatedAt = :timestamp WHERE chapterId = :chapterId AND userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object incrementVisitCount(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE chapter_stats SET noteCount = noteCount + 1, updatedAt = :timestamp WHERE chapterId = :chapterId AND userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object incrementNoteCount(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteChapterStats(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.ChapterStats chapterStats, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM chapter_stats WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}