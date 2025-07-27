package com.edify.learning.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00140\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u0005H\'J\u001e\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00140\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u0005H\'J\u001e\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00140\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u0005H\'J4\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00142\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u00142\u0006\u0010\u001a\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0018\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u0015\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0011J&\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00142\u0006\u0010\u001a\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010 J&\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00142\u0006\u0010\"\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010 J\u0016\u0010#\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ \u0010$\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010 J\u0016\u0010%\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006&"}, d2 = {"Lcom/edify/learning/data/dao/GeneratedQuestDao;", "", "completeQuest", "", "questId", "", "answer", "completedAt", "", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllQuests", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteQuest", "quest", "Lcom/edify/learning/data/model/GeneratedQuest;", "(Lcom/edify/learning/data/model/GeneratedQuest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteQuestById", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllQuests", "Lkotlinx/coroutines/flow/Flow;", "", "userId", "getCompletedQuests", "getIncompleteQuests", "getPastQuestsForChapters", "chapterIds", "chapterId", "(Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuestById", "getQuestCount", "", "getQuestsByChapterId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuestsByType", "questType", "insertQuest", "unlockQuestByChapterId", "updateQuest", "app_debug"})
@androidx.room.Dao()
public abstract interface GeneratedQuestDao {
    
    @androidx.room.Query(value = "SELECT * FROM generated_quests WHERE userId = :userId ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.GeneratedQuest>> getAllQuests(@org.jetbrains.annotations.NotNull()
    java.lang.String userId);
    
    @androidx.room.Query(value = "SELECT * FROM generated_quests WHERE id = :questId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuestById(@org.jetbrains.annotations.NotNull()
    java.lang.String questId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.GeneratedQuest> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM generated_quests WHERE userId = :userId AND isCompleted = 0 ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.GeneratedQuest>> getIncompleteQuests(@org.jetbrains.annotations.NotNull()
    java.lang.String userId);
    
    @androidx.room.Query(value = "SELECT * FROM generated_quests WHERE userId = :userId AND isCompleted = 1 ORDER BY completedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.GeneratedQuest>> getCompletedQuests(@org.jetbrains.annotations.NotNull()
    java.lang.String userId);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertQuest(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.GeneratedQuest quest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateQuest(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.GeneratedQuest quest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE generated_quests SET isCompleted = 1, userAnswer = :answer, completedAt = :completedAt WHERE id = :questId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object completeQuest(@org.jetbrains.annotations.NotNull()
    java.lang.String questId, @org.jetbrains.annotations.NotNull()
    java.lang.String answer, long completedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE generated_quests SET isUnlocked = 1 WHERE chapterId = :chapterId AND userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object unlockQuestByChapterId(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteQuest(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.GeneratedQuest quest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM generated_quests WHERE id = :questId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteQuestById(@org.jetbrains.annotations.NotNull()
    java.lang.String questId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM generated_quests WHERE userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuestCount(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "DELETE FROM generated_quests")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllQuests(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM generated_quests WHERE chapterId = :chapterId AND userId = :userId ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuestsByChapterId(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.GeneratedQuest>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM generated_quests WHERE questType = :questType AND userId = :userId ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuestsByType(@org.jetbrains.annotations.NotNull()
    java.lang.String questType, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.GeneratedQuest>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM generated_quests WHERE (chapterId IN (:chapterIds) OR involvedChapterIds LIKE \'%\' || :chapterId || \'%\') AND userId = :userId ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPastQuestsForChapters(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> chapterIds, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.GeneratedQuest>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}