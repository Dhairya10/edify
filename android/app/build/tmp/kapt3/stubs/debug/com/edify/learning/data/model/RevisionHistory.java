package com.edify.learning.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003JW\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\f\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020\u0005H\u00d6\u0001J\t\u0010%\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015\u00a8\u0006&"}, d2 = {"Lcom/edify/learning/data/model/RevisionHistory;", "", "chapterId", "", "questionIndex", "", "question", "expectedAnswer", "submissions", "", "Lcom/edify/learning/data/model/RevisionSubmission;", "latestSubmission", "totalSubmissions", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/util/List;Lcom/edify/learning/data/model/RevisionSubmission;I)V", "getChapterId", "()Ljava/lang/String;", "getExpectedAnswer", "getLatestSubmission", "()Lcom/edify/learning/data/model/RevisionSubmission;", "getQuestion", "getQuestionIndex", "()I", "getSubmissions", "()Ljava/util/List;", "getTotalSubmissions", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class RevisionHistory {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String chapterId = null;
    private final int questionIndex = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String question = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String expectedAnswer = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.edify.learning.data.model.RevisionSubmission> submissions = null;
    @org.jetbrains.annotations.Nullable()
    private final com.edify.learning.data.model.RevisionSubmission latestSubmission = null;
    private final int totalSubmissions = 0;
    
    public RevisionHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, int questionIndex, @org.jetbrains.annotations.NotNull()
    java.lang.String question, @org.jetbrains.annotations.NotNull()
    java.lang.String expectedAnswer, @org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.RevisionSubmission> submissions, @org.jetbrains.annotations.Nullable()
    com.edify.learning.data.model.RevisionSubmission latestSubmission, int totalSubmissions) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getChapterId() {
        return null;
    }
    
    public final int getQuestionIndex() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getQuestion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getExpectedAnswer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.RevisionSubmission> getSubmissions() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.edify.learning.data.model.RevisionSubmission getLatestSubmission() {
        return null;
    }
    
    public final int getTotalSubmissions() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.RevisionSubmission> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.edify.learning.data.model.RevisionSubmission component6() {
        return null;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.model.RevisionHistory copy(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, int questionIndex, @org.jetbrains.annotations.NotNull()
    java.lang.String question, @org.jetbrains.annotations.NotNull()
    java.lang.String expectedAnswer, @org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.RevisionSubmission> submissions, @org.jetbrains.annotations.Nullable()
    com.edify.learning.data.model.RevisionSubmission latestSubmission, int totalSubmissions) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}