package com.edify.learning.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00e8\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\u0007\n\u0002\b\u000e\b\u0007\u0018\u00002\u00020\u0001Bi\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u00a2\u0006\u0002\u0010\u001aJ\u000e\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ \u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u001fH\u0002J\b\u0010#\u001a\u00020\u001fH\u0002J\b\u0010$\u001a\u00020\u001fH\u0002J\b\u0010%\u001a\u00020\u001fH\u0002J\u0016\u0010&\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020,H\u0086@\u00a2\u0006\u0002\u0010-J4\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001f0/2\u0006\u00100\u001a\u00020\u001f2\u0006\u00101\u001a\u00020\u001f2\u0006\u0010\u0002\u001a\u00020\u001fH\u0082@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b2\u00103J<\u00104\u001a\b\u0012\u0004\u0012\u00020\u001f0/2\u0006\u00105\u001a\u00020\u001f2\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u001fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b6\u00103J4\u00107\u001a\b\u0012\u0004\u0012\u00020\u001f082\u0006\u00105\u001a\u00020\u001f2\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u001fH\u0086@\u00a2\u0006\u0002\u00103JH\u00109\u001a\b\u0012\u0004\u0012\u00020\u001f0/2\u0006\u00105\u001a\u00020\u001f2\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010:\u001a\u0004\u0018\u00010;H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b<\u0010=J\u0012\u0010>\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0?08J\u0012\u0010@\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020A0?08J\u0012\u0010B\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0?08J\u0018\u0010C\u001a\u0004\u0018\u00010D2\u0006\u0010E\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010FJ\u0016\u0010G\u001a\u00020\u001f2\u0006\u0010H\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010FJ\u001a\u0010I\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020D0?082\u0006\u0010J\u001a\u00020\u001fJ\u001a\u0010K\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020L0?082\u0006\u0010H\u001a\u00020\u001fJ\u0014\u0010M\u001a\b\u0012\u0004\u0012\u00020N0?2\u0006\u0010H\u001a\u00020\u001fJ\u001a\u0010O\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0?082\u0006\u0010H\u001a\u00020\u001fJ\"\u0010P\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0?082\u0006\u0010H\u001a\u00020\u001f2\u0006\u0010Q\u001a\u00020RJ\u0016\u0010S\u001a\u00020T2\u0006\u0010H\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010FJ\u001c\u0010U\u001a\b\u0012\u0004\u0012\u00020V0?2\u0006\u0010J\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010FJ\u0014\u0010W\u001a\b\u0012\u0004\u0012\u00020X0?2\u0006\u0010H\u001a\u00020\u001fJ \u0010Y\u001a\u0004\u0018\u00010Z2\u0006\u0010H\u001a\u00020\u001f2\u0006\u0010[\u001a\u00020TH\u0086@\u00a2\u0006\u0002\u0010\\J\u001a\u0010]\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020Z0?082\u0006\u0010H\u001a\u00020\u001fJ\u0018\u0010^\u001a\u0004\u0018\u00010A2\u0006\u0010E\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010FJ\u001a\u0010_\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0?082\u0006\u0010J\u001a\u00020\u001fJ \u0010`\u001a\u0004\u0018\u00010,2\u0006\u0010H\u001a\u00020\u001f2\u0006\u0010a\u001a\u00020TH\u0086@\u00a2\u0006\u0002\u0010\\J\u001a\u0010b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0?082\u0006\u0010H\u001a\u00020\u001fJ\u000e\u0010c\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010d\u001a\u00020\u001cH\u0082@\u00a2\u0006\u0002\u0010\u001dJ\u001c\u0010e\u001a\b\u0012\u0004\u0012\u00020\u001c0/H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bf\u0010\u001dJ\u0016\u0010g\u001a\u00020\u001c2\u0006\u0010h\u001a\u00020DH\u0086@\u00a2\u0006\u0002\u0010iJ\u0016\u0010j\u001a\u00020\u001c2\u0006\u0010k\u001a\u00020LH\u0086@\u00a2\u0006\u0002\u0010lJ\u0016\u0010m\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010)J\u0016\u0010n\u001a\u00020\u001c2\u0006\u00101\u001a\u00020AH\u0086@\u00a2\u0006\u0002\u0010oJ\u0010\u0010p\u001a\u00020q2\u0006\u0010r\u001a\u00020\u001fH\u0002J\u000e\u0010s\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010t\u001a\u00020u2\u0006\u0010+\u001a\u00020ZH\u0086@\u00a2\u0006\u0002\u0010vJ\u0016\u0010w\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020,H\u0086@\u00a2\u0006\u0002\u0010-J\u001a\u0010x\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020D0?082\u0006\u00100\u001a\u00020\u001fJ\u001a\u0010y\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020A0?082\u0006\u00100\u001a\u00020\u001fJ@\u0010z\u001a\b\u0012\u0004\u0012\u00020\u001f0/2\u0006\u0010H\u001a\u00020\u001f2\u0006\u0010[\u001a\u00020T2\u0006\u0010\"\u001a\u00020\u001f2\n\b\u0002\u0010{\u001a\u0004\u0018\u00010\u001fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b|\u0010}J \u0010~\u001a\u00020\u001c2\u0006\u0010H\u001a\u00020\u001f2\u0007\u0010\u007f\u001a\u00030\u0080\u0001H\u0086@\u00a2\u0006\u0003\u0010\u0081\u0001J*\u0010\u0082\u0001\u001a\u00020\u001c2\u0006\u0010H\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\t\b\u0002\u0010\u0083\u0001\u001a\u00020\u001fH\u0082@\u00a2\u0006\u0002\u00103J#\u0010\u0084\u0001\u001a\u00020\u001c2\u0006\u0010H\u001a\u00020\u001f2\t\b\u0002\u0010\u0083\u0001\u001a\u00020\u001fH\u0082@\u00a2\u0006\u0003\u0010\u0085\u0001J#\u0010\u0086\u0001\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020,2\t\b\u0002\u0010\u0083\u0001\u001a\u00020\u001fH\u0082@\u00a2\u0006\u0003\u0010\u0087\u0001J#\u0010\u0088\u0001\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020Z2\t\b\u0002\u0010\u0083\u0001\u001a\u00020\u001fH\u0082@\u00a2\u0006\u0003\u0010\u0089\u0001J#\u0010\u008a\u0001\u001a\u00020\u001c2\u0006\u0010H\u001a\u00020\u001f2\t\b\u0002\u0010\u0083\u0001\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0003\u0010\u0085\u0001J \u0010\u008b\u0001\u001a\u00020\u001c2\u0006\u0010J\u001a\u00020\u001f2\u0006\u0010H\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0003\u0010\u0085\u0001J\u0017\u0010\u008c\u0001\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020ZH\u0086@\u00a2\u0006\u0002\u0010vJ\u0017\u0010\u008d\u0001\u001a\u00020\u001c2\u0006\u0010J\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010FR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u008e\u0001"}, d2 = {"Lcom/edify/learning/data/repository/LearningRepository;", "", "context", "Landroid/content/Context;", "subjectDao", "Lcom/edify/learning/data/dao/SubjectDao;", "chapterDao", "Lcom/edify/learning/data/dao/ChapterDao;", "noteDao", "Lcom/edify/learning/data/dao/NoteDao;", "chatDao", "Lcom/edify/learning/data/dao/ChatDao;", "userResponseDao", "Lcom/edify/learning/data/dao/UserResponseDao;", "revisionResponseDao", "Lcom/edify/learning/data/dao/RevisionResponseDao;", "chapterStatsDao", "Lcom/edify/learning/data/dao/ChapterStatsDao;", "generatedQuestDao", "Lcom/edify/learning/data/dao/GeneratedQuestDao;", "gemmaService", "Lcom/edify/learning/data/service/GemmaService;", "questGenerationService", "Lcom/edify/learning/data/service/QuestGenerationService;", "promptService", "Lcom/edify/learning/data/service/PromptService;", "(Landroid/content/Context;Lcom/edify/learning/data/dao/SubjectDao;Lcom/edify/learning/data/dao/ChapterDao;Lcom/edify/learning/data/dao/NoteDao;Lcom/edify/learning/data/dao/ChatDao;Lcom/edify/learning/data/dao/UserResponseDao;Lcom/edify/learning/data/dao/RevisionResponseDao;Lcom/edify/learning/data/dao/ChapterStatsDao;Lcom/edify/learning/data/dao/GeneratedQuestDao;Lcom/edify/learning/data/service/GemmaService;Lcom/edify/learning/data/service/QuestGenerationService;Lcom/edify/learning/data/service/PromptService;)V", "clearAllData", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createAnswerReviewPrompt", "", "question", "referenceAnswer", "userAnswer", "createSampleMathContent", "createSamplePolynomialContent", "createSampleScienceContent", "deleteNote", "note", "Lcom/edify/learning/data/model/Note;", "(Lcom/edify/learning/data/model/Note;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteUserResponse", "response", "Lcom/edify/learning/data/model/UserResponse;", "(Lcom/edify/learning/data/model/UserResponse;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateEducationalResponse", "Lkotlin/Result;", "query", "subject", "generateEducationalResponse-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateGemmaResponse", "prompt", "generateGemmaResponse-BWLJW6A", "generateGemmaResponseStream", "Lkotlinx/coroutines/flow/Flow;", "generateGemmaResponseWithImage", "image", "Landroid/graphics/Bitmap;", "generateGemmaResponseWithImage-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllNotes", "", "getAllSubjects", "Lcom/edify/learning/data/model/Subject;", "getAllUserNotes", "getChapterById", "Lcom/edify/learning/data/model/Chapter;", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChapterSummaryForChat", "chapterId", "getChaptersBySubject", "subjectId", "getChatMessages", "Lcom/edify/learning/data/model/ChatMessage;", "getExercisesForChapter", "Lcom/edify/learning/data/model/Exercise;", "getNotesByChapter", "getNotesByChapterAndType", "type", "Lcom/edify/learning/data/model/NoteType;", "getResponseCountForChapter", "", "getRevisionDataForSubject", "Lcom/edify/learning/data/model/ChapterRevisionData;", "getRevisionQuestionsForChapter", "Lcom/edify/learning/data/model/RevisionQuestion;", "getRevisionResponse", "Lcom/edify/learning/data/model/RevisionResponse;", "questionIndex", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRevisionResponsesForChapter", "getSubjectById", "getUserNotesBySubject", "getUserResponse", "exerciseIndex", "getUserResponsesForChapter", "initializeContentData", "initializeFallbackData", "initializeGemma", "initializeGemma-IoAF18A", "insertChapter", "chapter", "(Lcom/edify/learning/data/model/Chapter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertChatMessage", "message", "(Lcom/edify/learning/data/model/ChatMessage;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertNote", "insertSubject", "(Lcom/edify/learning/data/model/Subject;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseFeedbackCategory", "Lcom/edify/learning/data/model/FeedbackCategory;", "feedback", "refreshChapterTitles", "saveRevisionResponse", "", "(Lcom/edify/learning/data/model/RevisionResponse;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserResponse", "searchChapters", "searchSubjects", "submitRevisionResponseForReview", "imageUri", "submitRevisionResponseForReview-yxL6bBk", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChapterProgress", "progress", "", "(Ljava/lang/String;FLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChapterStatsForChat", "userId", "updateChapterStatsForNote", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChapterStatsForRevision", "(Lcom/edify/learning/data/model/UserResponse;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChapterStatsForRevisionResponse", "(Lcom/edify/learning/data/model/RevisionResponse;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChapterVisit", "updateLastReadChapter", "updateRevisionResponse", "updateSubjectProgress", "app_debug"})
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
    private final com.edify.learning.data.dao.RevisionResponseDao revisionResponseDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.ChapterStatsDao chapterStatsDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.dao.GeneratedQuestDao generatedQuestDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.GemmaService gemmaService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.QuestGenerationService questGenerationService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.PromptService promptService = null;
    
    @javax.inject.Inject()
    public LearningRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.SubjectDao subjectDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChapterDao chapterDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.NoteDao noteDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChatDao chatDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.UserResponseDao userResponseDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.RevisionResponseDao revisionResponseDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChapterStatsDao chapterStatsDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.GeneratedQuestDao generatedQuestDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.GemmaService gemmaService, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.QuestGenerationService questGenerationService, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.PromptService promptService) {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearAllData(@org.jetbrains.annotations.NotNull()
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
    java.lang.String subject, @org.jetbrains.annotations.Nullable()
    java.lang.String context, @org.jetbrains.annotations.NotNull()
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.RevisionQuestion> getRevisionQuestionsForChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRevisionDataForSubject(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.ChapterRevisionData>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.RevisionResponse>> getRevisionResponsesForChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRevisionResponse(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, int questionIndex, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.RevisionResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveRevisionResponse(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.RevisionResponse response, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateRevisionResponse(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.RevisionResponse response, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String createAnswerReviewPrompt(java.lang.String question, java.lang.String referenceAnswer, java.lang.String userAnswer) {
        return null;
    }
    
    private final com.edify.learning.data.model.FeedbackCategory parseFeedbackCategory(java.lang.String feedback) {
        return null;
    }
    
    private final java.lang.Object updateChapterStatsForRevisionResponse(com.edify.learning.data.model.RevisionResponse response, java.lang.String userId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}