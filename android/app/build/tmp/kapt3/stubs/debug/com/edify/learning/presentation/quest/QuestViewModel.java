package com.edify.learning.presentation.quest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015J(\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u000f2\u0018\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00120\u0019J\u000e\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u000fJ\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00150\u001cH\u0002J\u0016\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0010J\b\u0010\u001f\u001a\u00020\u0012H\u0002J\u000e\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u000fJ\u001c\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001c2\u0006\u0010\u000e\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0010J\u0006\u0010$\u001a\u00020\u0012J\u000e\u0010%\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u000fJ\u0016\u0010&\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020\u000fJ\u0016\u0010(\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020\u000fR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006)"}, d2 = {"Lcom/edify/learning/presentation/quest/QuestViewModel;", "Landroidx/lifecycle/ViewModel;", "questRepository", "Lcom/edify/learning/data/repository/QuestRepository;", "(Lcom/edify/learning/data/repository/QuestRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/edify/learning/presentation/quest/QuestUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "calculateProgressData", "Lcom/edify/learning/presentation/quest/QuestProgressData;", "userId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkPersonalizationReadiness", "", "exploreQuestion", "question", "Lcom/edify/learning/data/model/QuestQuestion;", "generateDeepExplorationQuestion", "chapterId", "onQuestionGenerated", "Lkotlin/Function2;", "generateQuestForChapter", "getIntroductoryQuestions", "", "getSubjectName", "subjectId", "loadQuestData", "loadQuestDetail", "questId", "loadTopInterestedChapters", "Lcom/edify/learning/presentation/quest/PersonalizedChapterQuest;", "refreshQuestData", "startQuest", "submitChapterQuestAnswer", "answer", "submitQuestAnswer", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class QuestViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.QuestRepository questRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.quest.QuestUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.quest.QuestUiState> uiState = null;
    
    @javax.inject.Inject()
    public QuestViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.QuestRepository questRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.quest.QuestUiState> getUiState() {
        return null;
    }
    
    public final void refreshQuestData() {
    }
    
    private final void loadQuestData() {
    }
    
    private final java.util.List<com.edify.learning.data.model.QuestQuestion> getIntroductoryQuestions() {
        return null;
    }
    
    public final void startQuest(@org.jetbrains.annotations.NotNull()
    java.lang.String questId) {
    }
    
    /**
     * Generate deep exploration question for a chapter and prepare for Q&A view
     */
    public final void generateDeepExplorationQuestion(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onQuestionGenerated) {
    }
    
    public final void exploreQuestion(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.QuestQuestion question) {
    }
    
    public final void checkPersonalizationReadiness(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    public final void loadQuestDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String questId) {
    }
    
    public final void submitQuestAnswer(@org.jetbrains.annotations.NotNull()
    java.lang.String questId, @org.jetbrains.annotations.NotNull()
    java.lang.String answer) {
    }
    
    /**
     * Submit answer for chapter-based deep exploration question
     */
    public final void submitChapterQuestAnswer(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String answer) {
    }
    
    private final java.lang.Object calculateProgressData(java.lang.String userId, kotlin.coroutines.Continuation<? super com.edify.learning.presentation.quest.QuestProgressData> $completion) {
        return null;
    }
    
    private final java.lang.Object loadTopInterestedChapters(java.lang.String userId, kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.presentation.quest.PersonalizedChapterQuest>> $completion) {
        return null;
    }
    
    private final java.lang.Object getSubjectName(java.lang.String subjectId, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    public final void generateQuestForChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
    }
}