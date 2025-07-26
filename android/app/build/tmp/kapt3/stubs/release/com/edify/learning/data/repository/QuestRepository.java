package com.edify.learning.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ(\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u00152\b\b\u0002\u0010\u0012\u001a\u00020\u0010J&\u0010\u0018\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u00192\u0006\u0010\u001c\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u00152\b\b\u0002\u0010\u0012\u001a\u00020\u0010J\u001c\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u00152\b\b\u0002\u0010\u0012\u001a\u00020\u0010J\u0018\u0010 \u001a\u0004\u0018\u00010\u00172\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0018\u0010!\u001a\u00020\"2\b\b\u0002\u0010\u0012\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0018\u0010#\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001dR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/edify/learning/data/repository/QuestRepository;", "", "generatedQuestDao", "Lcom/edify/learning/data/dao/GeneratedQuestDao;", "chapterStatsDao", "Lcom/edify/learning/data/dao/ChapterStatsDao;", "chapterDao", "Lcom/edify/learning/data/dao/ChapterDao;", "subjectDao", "Lcom/edify/learning/data/dao/SubjectDao;", "questGenerationService", "Lcom/edify/learning/data/service/QuestGenerationService;", "(Lcom/edify/learning/data/dao/GeneratedQuestDao;Lcom/edify/learning/data/dao/ChapterStatsDao;Lcom/edify/learning/data/dao/ChapterDao;Lcom/edify/learning/data/dao/SubjectDao;Lcom/edify/learning/data/service/QuestGenerationService;)V", "completeQuest", "", "questId", "", "answer", "userId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllQuests", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/edify/learning/data/model/GeneratedQuest;", "getChapterDetails", "Lkotlin/Pair;", "Lcom/edify/learning/data/model/Chapter;", "Lcom/edify/learning/data/model/Subject;", "chapterId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCompletedQuests", "getIncompleteQuests", "getQuestById", "getQuestCount", "", "triggerQuestGeneration", "app_release"})
public final class QuestRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.GeneratedQuestDao generatedQuestDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.ChapterStatsDao chapterStatsDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.ChapterDao chapterDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.SubjectDao subjectDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.QuestGenerationService questGenerationService = null;
    
    @javax.inject.Inject()
    public QuestRepository(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.GeneratedQuestDao generatedQuestDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChapterStatsDao chapterStatsDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChapterDao chapterDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.SubjectDao subjectDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.QuestGenerationService questGenerationService) {
        super();
    }
    
    /**
     * Get all generated quests for the user
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.GeneratedQuest>> getAllQuests(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
    
    /**
     * Get incomplete quests for the user
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.GeneratedQuest>> getIncompleteQuests(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
    
    /**
     * Get completed quests for the user
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.GeneratedQuest>> getCompletedQuests(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
    
    /**
     * Get quest by ID
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getQuestById(@org.jetbrains.annotations.NotNull()
    java.lang.String questId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.GeneratedQuest> $completion) {
        return null;
    }
    
    /**
     * Complete a quest with user's answer
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object completeQuest(@org.jetbrains.annotations.NotNull()
    java.lang.String questId, @org.jetbrains.annotations.NotNull()
    java.lang.String answer, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Trigger quest generation manually (for testing or manual triggers)
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object triggerQuestGeneration(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get chapter details for quest display
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getChapterDetails(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Pair<com.edify.learning.data.model.Chapter, com.edify.learning.data.model.Subject>> $completion) {
        return null;
    }
    
    /**
     * Get quest count for user
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getQuestCount(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
}