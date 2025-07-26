package com.edify.learning.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u0006\n\u0002\b\u0014\b\u0087\b\u0018\u00002\u00020\u0001B\u0087\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0015J\b\u0010\'\u001a\u00020(H\u0002J\u0006\u0010)\u001a\u00020(J\b\u0010*\u001a\u00020(H\u0002J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0005H\u00c6\u0003J\t\u00100\u001a\u00020\u0005H\u00c6\u0003J\t\u00101\u001a\u00020\bH\u00c6\u0003J\t\u00102\u001a\u00020\bH\u00c6\u0003J\t\u00103\u001a\u00020\u000bH\u00c6\u0003J\t\u00104\u001a\u00020\u000bH\u00c6\u0003J\u000f\u00105\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00c6\u0003J\u000f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00110\u000eH\u00c6\u0003J\u008d\u0001\u00107\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u0003H\u00c6\u0001J\u0013\u00108\u001a\u00020\u000b2\b\u00109\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010:\u001a\u00020\bH\u00d6\u0001J\t\u0010;\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001bR\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001dR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019R\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010!\u00a8\u0006<"}, d2 = {"Lcom/edify/learning/data/model/ChapterStats;", "", "id", "", "chapterId", "", "userId", "visitCount", "", "noteCount", "questGenerated", "", "divergentQuestGenerated", "revisionHistory", "", "Lcom/edify/learning/data/model/RevisionEntry;", "chatHistory", "Lcom/edify/learning/data/model/ChatEntry;", "lastVisited", "createdAt", "updatedAt", "(JLjava/lang/String;Ljava/lang/String;IIZZLjava/util/List;Ljava/util/List;JJJ)V", "getChapterId", "()Ljava/lang/String;", "getChatHistory", "()Ljava/util/List;", "getCreatedAt", "()J", "getDivergentQuestGenerated", "()Z", "getId", "getLastVisited", "getNoteCount", "()I", "getQuestGenerated", "getRevisionHistory", "getUpdatedAt", "getUserId", "getVisitCount", "calculateChatScore", "", "calculateInterestScore", "calculateRevisionScore", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "chapter_stats")
@androidx.room.TypeConverters(value = {com.edify.learning.data.model.ChapterStatsConverters.class})
public final class ChapterStats {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String chapterId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String userId = null;
    private final int visitCount = 0;
    private final int noteCount = 0;
    private final boolean questGenerated = false;
    private final boolean divergentQuestGenerated = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.edify.learning.data.model.RevisionEntry> revisionHistory = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.edify.learning.data.model.ChatEntry> chatHistory = null;
    private final long lastVisited = 0L;
    private final long createdAt = 0L;
    private final long updatedAt = 0L;
    
    public ChapterStats(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, int visitCount, int noteCount, boolean questGenerated, boolean divergentQuestGenerated, @org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.RevisionEntry> revisionHistory, @org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.ChatEntry> chatHistory, long lastVisited, long createdAt, long updatedAt) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getChapterId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserId() {
        return null;
    }
    
    public final int getVisitCount() {
        return 0;
    }
    
    public final int getNoteCount() {
        return 0;
    }
    
    public final boolean getQuestGenerated() {
        return false;
    }
    
    public final boolean getDivergentQuestGenerated() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.RevisionEntry> getRevisionHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.ChatEntry> getChatHistory() {
        return null;
    }
    
    public final long getLastVisited() {
        return 0L;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    /**
     * Calculate Interest Score based on Quest scoring algorithm
     * Formula: InterestScore = (w_revision * RevisionScore) + (w_chat * ChatScore) + (w_notes * NoteScore) + (w_visits * VisitScore)
     * Weights: w_chat=0.4, w_notes=0.25, w_visits=0.2, w_revision=0.15
     */
    public final double calculateInterestScore() {
        return 0.0;
    }
    
    /**
     * Calculate Revision Score: Average of correctnessScore and originalityScore across all revision entries
     */
    private final double calculateRevisionScore() {
        return 0.0;
    }
    
    /**
     * Calculate Chat Score: Average curiosityScore from all chat entries
     */
    private final double calculateChatScore() {
        return 0.0;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component10() {
        return 0L;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final long component12() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.RevisionEntry> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.ChatEntry> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.model.ChapterStats copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, int visitCount, int noteCount, boolean questGenerated, boolean divergentQuestGenerated, @org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.RevisionEntry> revisionHistory, @org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.ChatEntry> chatHistory, long lastVisited, long createdAt, long updatedAt) {
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