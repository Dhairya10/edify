package com.edify.learning.presentation.developer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012\b\b\u0002\u0010\n\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\rJ\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\fH\u00c6\u0003JO\u0010\u001c\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u00062\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\fH\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0014R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014\u00a8\u0006\""}, d2 = {"Lcom/edify/learning/presentation/developer/QuestScoringUiState;", "", "chapterRankings", "", "Lcom/edify/learning/domain/service/ChapterInterestScore;", "isReadyForQuests", "", "detailedScoring", "Lcom/edify/learning/domain/service/DetailedScoring;", "showDetailedScoring", "isLoading", "error", "", "(Ljava/util/List;ZLcom/edify/learning/domain/service/DetailedScoring;ZZLjava/lang/String;)V", "getChapterRankings", "()Ljava/util/List;", "getDetailedScoring", "()Lcom/edify/learning/domain/service/DetailedScoring;", "getError", "()Ljava/lang/String;", "()Z", "getShowDetailedScoring", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class QuestScoringUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.edify.learning.domain.service.ChapterInterestScore> chapterRankings = null;
    private final boolean isReadyForQuests = false;
    @org.jetbrains.annotations.Nullable()
    private final com.edify.learning.domain.service.DetailedScoring detailedScoring = null;
    private final boolean showDetailedScoring = false;
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public QuestScoringUiState(@org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.domain.service.ChapterInterestScore> chapterRankings, boolean isReadyForQuests, @org.jetbrains.annotations.Nullable()
    com.edify.learning.domain.service.DetailedScoring detailedScoring, boolean showDetailedScoring, boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.domain.service.ChapterInterestScore> getChapterRankings() {
        return null;
    }
    
    public final boolean isReadyForQuests() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.edify.learning.domain.service.DetailedScoring getDetailedScoring() {
        return null;
    }
    
    public final boolean getShowDetailedScoring() {
        return false;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public QuestScoringUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.domain.service.ChapterInterestScore> component1() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.edify.learning.domain.service.DetailedScoring component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.presentation.developer.QuestScoringUiState copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.domain.service.ChapterInterestScore> chapterRankings, boolean isReadyForQuests, @org.jetbrains.annotations.Nullable()
    com.edify.learning.domain.service.DetailedScoring detailedScoring, boolean showDetailedScoring, boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
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