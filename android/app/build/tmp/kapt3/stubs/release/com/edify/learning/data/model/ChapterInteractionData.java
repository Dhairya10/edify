package com.edify.learning.data.model;

/**
 * Chapter interaction data for LLM context
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010 \n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003Ja\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e\u00a8\u0006%"}, d2 = {"Lcom/edify/learning/data/model/ChapterInteractionData;", "", "chapterId", "", "chapterTitle", "subject", "interestScore", "", "notes", "", "chatQuestions", "revisionAnswers", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DLjava/util/List;Ljava/util/List;Ljava/util/List;)V", "getChapterId", "()Ljava/lang/String;", "getChapterTitle", "getChatQuestions", "()Ljava/util/List;", "getInterestScore", "()D", "getNotes", "getRevisionAnswers", "getSubject", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"})
public final class ChapterInteractionData {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String chapterId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String chapterTitle = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String subject = null;
    private final double interestScore = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> notes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> chatQuestions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> revisionAnswers = null;
    
    public ChapterInteractionData(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String subject, double interestScore, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> notes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> chatQuestions, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> revisionAnswers) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getChapterId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getChapterTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSubject() {
        return null;
    }
    
    public final double getInterestScore() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getChatQuestions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRevisionAnswers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.model.ChapterInteractionData copy(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String subject, double interestScore, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> notes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> chatQuestions, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> revisionAnswers) {
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