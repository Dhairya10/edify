package com.edify.learning.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u000b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000f0\u000eH\'J\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\u0012\u001a\u00020\u00032\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000f0\u000e2\u0006\u0010\u0016\u001a\u00020\u0007H\'J(\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00a2\u0006\u0002\u0010\u001cJ(\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00a2\u0006\u0002\u0010 J\u0016\u0010!\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\""}, d2 = {"Lcom/edify/learning/data/dao/SubjectDao;", "", "deleteAllSubjects", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteById", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSubject", "subject", "Lcom/edify/learning/data/model/Subject;", "(Lcom/edify/learning/data/model/Subject;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllSubjects", "Lkotlinx/coroutines/flow/Flow;", "", "getSubjectById", "insertSubject", "insertSubjects", "subjects", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchSubjects", "query", "updateLastReadChapter", "subjectId", "chapterId", "timestamp", "", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProgress", "completedChapters", "", "(Ljava/lang/String;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSubject", "app_release"})
@androidx.room.Dao()
public abstract interface SubjectDao {
    
    @androidx.room.Query(value = "SELECT * FROM subjects ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Subject>> getAllSubjects();
    
    @androidx.room.Query(value = "SELECT * FROM subjects WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSubjectById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.Subject> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM subjects WHERE name LIKE \'%\' || :query || \'%\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Subject>> searchSubjects(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSubject(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Subject subject, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSubjects(@org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.Subject> subjects, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSubject(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Subject subject, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSubject(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Subject subject, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM subjects WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM subjects")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllSubjects(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE subjects SET lastReadChapterId = :chapterId, updatedAt = :timestamp WHERE id = :subjectId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLastReadChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE subjects SET completedChapters = :completedChapters, updatedAt = :timestamp WHERE id = :subjectId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId, int completedChapters, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}