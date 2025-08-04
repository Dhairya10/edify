package com.edify.learning.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ \u0010\r\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u00182\u0006\u0010\b\u001a\u00020\tH\'J$\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u00182\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\fH\'J\u0016\u0010\u001a\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J6\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010#\u00a8\u0006$"}, d2 = {"Lcom/edify/learning/data/dao/RevisionSubmissionDao;", "", "deleteSubmission", "", "submission", "Lcom/edify/learning/data/model/RevisionSubmission;", "(Lcom/edify/learning/data/model/RevisionSubmission;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSubmissionsForChapter", "chapterId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEvaluatedSubmissionsCount", "", "getLatestSubmissionForQuestion", "questionIndex", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPendingEvaluations", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSubmissionById", "submissionId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSubmissionsForChapter", "Lkotlinx/coroutines/flow/Flow;", "getSubmissionsForQuestion", "getUniqueQuestionsAnswered", "insertSubmission", "updateSubmission", "updateSubmissionEvaluation", "feedback", "grade", "isEvaluated", "", "evaluatedAt", "(JLjava/lang/String;Ljava/lang/String;ZJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface RevisionSubmissionDao {
    
    @androidx.room.Query(value = "SELECT * FROM revision_submissions WHERE chapterId = :chapterId ORDER BY questionIndex ASC, submittedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.RevisionSubmission>> getSubmissionsForChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId);
    
    @androidx.room.Query(value = "SELECT * FROM revision_submissions WHERE chapterId = :chapterId AND questionIndex = :questionIndex ORDER BY submittedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.RevisionSubmission>> getSubmissionsForQuestion(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, int questionIndex);
    
    @androidx.room.Query(value = "SELECT * FROM revision_submissions WHERE chapterId = :chapterId AND questionIndex = :questionIndex ORDER BY submittedAt DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLatestSubmissionForQuestion(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, int questionIndex, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.RevisionSubmission> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM revision_submissions WHERE id = :submissionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSubmissionById(long submissionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.RevisionSubmission> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSubmission(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.RevisionSubmission submission, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSubmission(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.RevisionSubmission submission, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE revision_submissions SET gemmaFeedback = :feedback, gemmaGrade = :grade, isEvaluated = :isEvaluated, evaluatedAt = :evaluatedAt WHERE id = :submissionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSubmissionEvaluation(long submissionId, @org.jetbrains.annotations.NotNull()
    java.lang.String feedback, @org.jetbrains.annotations.NotNull()
    java.lang.String grade, boolean isEvaluated, long evaluatedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSubmission(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.RevisionSubmission submission, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM revision_submissions WHERE chapterId = :chapterId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSubmissionsForChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM revision_submissions WHERE chapterId = :chapterId AND isEvaluated = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEvaluatedSubmissionsCount(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(DISTINCT questionIndex) FROM revision_submissions WHERE chapterId = :chapterId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUniqueQuestionsAnswered(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM revision_submissions WHERE isEvaluated = 0 ORDER BY submittedAt ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPendingEvaluations(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.RevisionSubmission>> $completion);
}