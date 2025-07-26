package com.edify.learning.presentation.developer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u001d\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\f\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010\u0013\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010\u0014\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010\u0015\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0014\u0010\u0019\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fJ$\u0010\u001a\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010\u001b\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010\u001c\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J\u0006\u0010\u001d\u001a\u00020\rJ\u0016\u0010\u001e\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020\"H\u0082@\u00a2\u0006\u0002\u0010#J\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020 0%H\u0082@\u00a2\u0006\u0002\u0010#J\u000e\u0010&\u001a\u00020\"H\u0082@\u00a2\u0006\u0002\u0010#J\u0014\u0010\'\u001a\b\u0012\u0004\u0012\u00020 0%H\u0082@\u00a2\u0006\u0002\u0010#J\u000e\u0010(\u001a\u00020\"H\u0082@\u00a2\u0006\u0002\u0010#J\u0014\u0010)\u001a\b\u0012\u0004\u0012\u00020 0%H\u0082@\u00a2\u0006\u0002\u0010#J\u000e\u0010*\u001a\u00020\"H\u0082@\u00a2\u0006\u0002\u0010#J\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020 0%H\u0082@\u00a2\u0006\u0002\u0010#J\u000e\u0010,\u001a\u00020\"H\u0082@\u00a2\u0006\u0002\u0010#J\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020 0%H\u0082@\u00a2\u0006\u0002\u0010#J\u000e\u0010.\u001a\u00020\"H\u0082@\u00a2\u0006\u0002\u0010#J\u0014\u0010/\u001a\b\u0012\u0004\u0012\u00020 0%H\u0082@\u00a2\u0006\u0002\u0010#J\u000e\u00100\u001a\u00020\"H\u0082@\u00a2\u0006\u0002\u0010#J\u0014\u00101\u001a\b\u0012\u0004\u0012\u00020 0%H\u0082@\u00a2\u0006\u0002\u0010#J\u0006\u00102\u001a\u00020\rJ\u0006\u00103\u001a\u00020\rJ\u0006\u00104\u001a\u00020\rJ\b\u00105\u001a\u00020\rH\u0002J\u0006\u00106\u001a\u00020\rJ\u000e\u00107\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0018J\u0016\u00108\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020 J$\u00109\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010:\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010;\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010<\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010=\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0014\u0010\u0019\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fJ$\u0010>\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010?\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J$\u0010@\u001a\u00020\r2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0012J\u000e\u0010A\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0018R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006B"}, d2 = {"Lcom/edify/learning/presentation/developer/DeveloperModeViewModel;", "Landroidx/lifecycle/ViewModel;", "database", "Lcom/edify/learning/data/database/EdifyDatabase;", "(Lcom/edify/learning/data/database/EdifyDatabase;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/edify/learning/presentation/developer/DeveloperModeUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addChapter", "", "data", "", "", "", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addChapterStats", "addChatMessage", "addNote", "addRecord", "table", "Lcom/edify/learning/presentation/developer/DatabaseTable;", "recordData", "addSubject", "addUserProfile", "addUserResponse", "clearAllQuestData", "deleteRecord", "record", "Lcom/edify/learning/presentation/developer/DatabaseRecord;", "getChapterCount", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChapterData", "", "getChapterStatsCount", "getChapterStatsData", "getChatMessageCount", "getChatMessageData", "getNoteCount", "getNoteData", "getSubjectCount", "getSubjectData", "getUserProfileCount", "getUserProfileData", "getUserResponseCount", "getUserResponseData", "hideAddRecordDialog", "hideEditRecordDialog", "hideTableDataDialog", "loadDatabaseTables", "refreshData", "showAddRecordDialog", "showEditRecordDialog", "updateChapter", "updateChapterStats", "updateChatMessage", "updateNote", "updateRecord", "updateSubject", "updateUserProfile", "updateUserResponse", "viewTableData", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DeveloperModeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.database.EdifyDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.developer.DeveloperModeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.developer.DeveloperModeUiState> uiState = null;
    
    @javax.inject.Inject()
    public DeveloperModeViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.database.EdifyDatabase database) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.developer.DeveloperModeUiState> getUiState() {
        return null;
    }
    
    public final void refreshData() {
    }
    
    public final void clearAllQuestData() {
    }
    
    private final void loadDatabaseTables() {
    }
    
    public final void viewTableData(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.developer.DatabaseTable table) {
    }
    
    public final void hideTableDataDialog() {
    }
    
    public final void showAddRecordDialog(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.developer.DatabaseTable table) {
    }
    
    public final void hideAddRecordDialog() {
    }
    
    public final void showEditRecordDialog(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.developer.DatabaseTable table, @org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.developer.DatabaseRecord record) {
    }
    
    public final void hideEditRecordDialog() {
    }
    
    public final void addRecord(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.developer.DatabaseTable table, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> recordData) {
    }
    
    public final void updateRecord(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.developer.DatabaseTable table, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> recordData) {
    }
    
    public final void deleteRecord(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.developer.DatabaseTable table, @org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.developer.DatabaseRecord record) {
    }
    
    private final java.lang.Object getSubjectCount(kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object getChapterCount(kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object getNoteCount(kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object getChatMessageCount(kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object getUserResponseCount(kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object getChapterStatsCount(kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object getUserProfileCount(kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object getSubjectData(kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.presentation.developer.DatabaseRecord>> $completion) {
        return null;
    }
    
    private final java.lang.Object getChapterData(kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.presentation.developer.DatabaseRecord>> $completion) {
        return null;
    }
    
    private final java.lang.Object getNoteData(kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.presentation.developer.DatabaseRecord>> $completion) {
        return null;
    }
    
    private final java.lang.Object getChatMessageData(kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.presentation.developer.DatabaseRecord>> $completion) {
        return null;
    }
    
    private final java.lang.Object getUserResponseData(kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.presentation.developer.DatabaseRecord>> $completion) {
        return null;
    }
    
    private final java.lang.Object getChapterStatsData(kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.presentation.developer.DatabaseRecord>> $completion) {
        return null;
    }
    
    private final java.lang.Object getUserProfileData(kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.presentation.developer.DatabaseRecord>> $completion) {
        return null;
    }
    
    private final java.lang.Object addSubject(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object addChapter(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object addNote(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object addChatMessage(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object addUserResponse(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object addChapterStats(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object addUserProfile(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateSubject(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateChapter(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateNote(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateChatMessage(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateUserResponse(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateChapterStats(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateUserProfile(java.util.Map<java.lang.String, ? extends java.lang.Object> data, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}