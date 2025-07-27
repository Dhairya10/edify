package com.edify.learning.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00ba\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u001c\n\u0002\u0010\u0007\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001BQ\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u0010!J:\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00160#2\u0006\u0010$\u001a\u00020\u00162\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010%\u001a\u00020&H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\'\u0010(J2\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00160*2\u0006\u0010$\u001a\u00020\u00162\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010%\u001a\u00020&H\u0086@\u00a2\u0006\u0002\u0010(JF\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00160#2\u0006\u0010$\u001a\u00020\u00162\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00162\n\b\u0002\u0010,\u001a\u0004\u0018\u00010-2\b\b\u0002\u0010%\u001a\u00020&H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b.\u0010/J\u0012\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c010*J\u0012\u00102\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u000203010*J\u0012\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c010*J\u0018\u00105\u001a\u0004\u0018\u0001062\u0006\u00107\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u00108J\u0016\u00109\u001a\u00020\u00162\u0006\u0010:\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u00108J\u001a\u0010;\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u000206010*2\u0006\u0010<\u001a\u00020\u0016J\u001a\u0010=\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020>010*2\u0006\u0010:\u001a\u00020\u0016J\u0014\u0010?\u001a\b\u0012\u0004\u0012\u00020@012\u0006\u0010:\u001a\u00020\u0016J\u001a\u0010A\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c010*2\u0006\u0010:\u001a\u00020\u0016J\"\u0010B\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c010*2\u0006\u0010:\u001a\u00020\u00162\u0006\u0010C\u001a\u00020DJ\u0016\u0010E\u001a\u00020F2\u0006\u0010:\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u00108J\u0018\u0010G\u001a\u0004\u0018\u0001032\u0006\u00107\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u00108J\u001a\u0010H\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c010*2\u0006\u0010<\u001a\u00020\u0016J \u0010I\u001a\u0004\u0018\u00010 2\u0006\u0010:\u001a\u00020\u00162\u0006\u0010J\u001a\u00020FH\u0086@\u00a2\u0006\u0002\u0010KJ\u001a\u0010L\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 010*2\u0006\u0010:\u001a\u00020\u0016J\u000e\u0010M\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010NJ\u000e\u0010O\u001a\u00020\u001aH\u0082@\u00a2\u0006\u0002\u0010NJ\u001c\u0010P\u001a\b\u0012\u0004\u0012\u00020\u001a0#H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bQ\u0010NJ\u0016\u0010R\u001a\u00020\u001a2\u0006\u0010S\u001a\u000206H\u0086@\u00a2\u0006\u0002\u0010TJ\u0016\u0010U\u001a\u00020\u001a2\u0006\u0010V\u001a\u00020>H\u0086@\u00a2\u0006\u0002\u0010WJ\u0016\u0010X\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010Y\u001a\u00020\u001a2\u0006\u0010Z\u001a\u000203H\u0086@\u00a2\u0006\u0002\u0010[J\u000e\u0010\\\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010NJ\u0016\u0010]\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u0010!J\u001a\u0010^\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u000206010*2\u0006\u0010_\u001a\u00020\u0016J\u001a\u0010`\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u000203010*2\u0006\u0010_\u001a\u00020\u0016J\u001e\u0010a\u001a\u00020\u001a2\u0006\u0010:\u001a\u00020\u00162\u0006\u0010b\u001a\u00020cH\u0086@\u00a2\u0006\u0002\u0010dJ(\u0010e\u001a\u00020\u001a2\u0006\u0010:\u001a\u00020\u00162\u0006\u0010f\u001a\u00020\u00162\b\b\u0002\u0010g\u001a\u00020\u0016H\u0082@\u00a2\u0006\u0002\u0010hJ \u0010i\u001a\u00020\u001a2\u0006\u0010:\u001a\u00020\u00162\b\b\u0002\u0010g\u001a\u00020\u0016H\u0082@\u00a2\u0006\u0002\u0010jJ \u0010k\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 2\b\b\u0002\u0010g\u001a\u00020\u0016H\u0082@\u00a2\u0006\u0002\u0010lJ \u0010m\u001a\u00020\u001a2\u0006\u0010:\u001a\u00020\u00162\b\b\u0002\u0010g\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010jJ\u001e\u0010n\u001a\u00020\u001a2\u0006\u0010<\u001a\u00020\u00162\u0006\u0010:\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010jJ\u0016\u0010o\u001a\u00020\u001a2\u0006\u0010<\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u00108R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006p"}, d2 = {"Lcom/edify/learning/data/repository/LearningRepository;", "", "context", "Landroid/content/Context;", "subjectDao", "Lcom/edify/learning/data/dao/SubjectDao;", "chapterDao", "Lcom/edify/learning/data/dao/ChapterDao;", "noteDao", "Lcom/edify/learning/data/dao/NoteDao;", "chatDao", "Lcom/edify/learning/data/dao/ChatDao;", "userResponseDao", "Lcom/edify/learning/data/dao/UserResponseDao;", "chapterStatsDao", "Lcom/edify/learning/data/dao/ChapterStatsDao;", "gemmaService", "Lcom/edify/learning/data/service/GemmaService;", "questGenerationService", "Lcom/edify/learning/data/service/QuestGenerationService;", "(Landroid/content/Context;Lcom/edify/learning/data/dao/SubjectDao;Lcom/edify/learning/data/dao/ChapterDao;Lcom/edify/learning/data/dao/NoteDao;Lcom/edify/learning/data/dao/ChatDao;Lcom/edify/learning/data/dao/UserResponseDao;Lcom/edify/learning/data/dao/ChapterStatsDao;Lcom/edify/learning/data/service/GemmaService;Lcom/edify/learning/data/service/QuestGenerationService;)V", "createSampleMathContent", "", "createSamplePolynomialContent", "createSampleScienceContent", "deleteNote", "", "note", "Lcom/edify/learning/data/model/Note;", "(Lcom/edify/learning/data/model/Note;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteUserResponse", "response", "Lcom/edify/learning/data/model/UserResponse;", "(Lcom/edify/learning/data/model/UserResponse;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateGemmaResponse", "Lkotlin/Result;", "prompt", "isExplanation", "", "generateGemmaResponse-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateGemmaResponseStream", "Lkotlinx/coroutines/flow/Flow;", "generateGemmaResponseWithImage", "image", "Landroid/graphics/Bitmap;", "generateGemmaResponseWithImage-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllNotes", "", "getAllSubjects", "Lcom/edify/learning/data/model/Subject;", "getAllUserNotes", "getChapterById", "Lcom/edify/learning/data/model/Chapter;", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChapterSummaryForChat", "chapterId", "getChaptersBySubject", "subjectId", "getChatMessages", "Lcom/edify/learning/data/model/ChatMessage;", "getExercisesForChapter", "Lcom/edify/learning/data/model/Exercise;", "getNotesByChapter", "getNotesByChapterAndType", "type", "Lcom/edify/learning/data/model/NoteType;", "getResponseCountForChapter", "", "getSubjectById", "getUserNotesBySubject", "getUserResponse", "exerciseIndex", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserResponsesForChapter", "initializeContentData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initializeFallbackData", "initializeGemma", "initializeGemma-IoAF18A", "insertChapter", "chapter", "(Lcom/edify/learning/data/model/Chapter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertChatMessage", "message", "(Lcom/edify/learning/data/model/ChatMessage;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertNote", "insertSubject", "subject", "(Lcom/edify/learning/data/model/Subject;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshChapterTitles", "saveUserResponse", "searchChapters", "query", "searchSubjects", "updateChapterProgress", "progress", "", "(Ljava/lang/String;FLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChapterStatsForChat", "question", "userId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChapterStatsForNote", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChapterStatsForRevision", "(Lcom/edify/learning/data/model/UserResponse;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChapterVisit", "updateLastReadChapter", "updateSubjectProgress", "app_debug"})
public final class LearningRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.SubjectDao subjectDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.ChapterDao chapterDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.NoteDao noteDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.ChatDao chatDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.UserResponseDao userResponseDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.ChapterStatsDao chapterStatsDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.GemmaService gemmaService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.QuestGenerationService questGenerationService = null;
    
    @javax.inject.Inject()
    public LearningRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.SubjectDao subjectDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChapterDao chapterDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.NoteDao noteDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChatDao chatDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.UserResponseDao userResponseDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChapterStatsDao chapterStatsDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.GemmaService gemmaService, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.QuestGenerationService questGenerationService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Subject>> getAllSubjects() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getSubjectById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.Subject> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Subject>> searchSubjects(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Chapter>> searchChapters(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertSubject(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Subject subject, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateSubjectProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateLastReadChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Chapter>> getChaptersBySubject(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getChapterById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.Chapter> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertChapter(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Chapter chapter, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateChapterProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, float progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> getAllNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> getAllUserNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> getUserNotesBySubject(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> getNotesByChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> getNotesByChapterAndType(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.NoteType type) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertNote(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Note note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteNote(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Note note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.ChatMessage>> getChatMessages(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertChatMessage(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.ChatMessage message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object generateGemmaResponseStream(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt, @org.jetbrains.annotations.Nullable()
    java.lang.String context, boolean isExplanation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<java.lang.String>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getChapterSummaryForChat(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.Exercise> getExercisesForChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.UserResponse>> getUserResponsesForChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserResponse(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, int exerciseIndex, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.UserResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveUserResponse(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.UserResponse response, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteUserResponse(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.UserResponse response, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getResponseCountForChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshChapterTitles(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object initializeContentData(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object initializeFallbackData(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String createSampleMathContent() {
        return null;
    }
    
    private final java.lang.String createSamplePolynomialContent() {
        return null;
    }
    
    private final java.lang.String createSampleScienceContent() {
        return null;
    }
    
    private final java.lang.Object updateChapterStatsForNote(java.lang.String chapterId, java.lang.String userId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateChapterStatsForChat(java.lang.String chapterId, java.lang.String question, java.lang.String userId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateChapterStatsForRevision(com.edify.learning.data.model.UserResponse response, java.lang.String userId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateChapterVisit(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}