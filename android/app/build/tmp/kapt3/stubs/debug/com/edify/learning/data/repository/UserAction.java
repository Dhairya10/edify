package com.edify.learning.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n\u00a8\u0006\u000b"}, d2 = {"Lcom/edify/learning/data/repository/UserAction;", "", "()V", "SaveNote", "SendChat", "SubmitRevision", "Visit", "Lcom/edify/learning/data/repository/UserAction$SaveNote;", "Lcom/edify/learning/data/repository/UserAction$SendChat;", "Lcom/edify/learning/data/repository/UserAction$SubmitRevision;", "Lcom/edify/learning/data/repository/UserAction$Visit;", "app_debug"})
public abstract class UserAction {
    
    private UserAction() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/edify/learning/data/repository/UserAction$SaveNote;", "Lcom/edify/learning/data/repository/UserAction;", "()V", "app_debug"})
    public static final class SaveNote extends com.edify.learning.data.repository.UserAction {
        @org.jetbrains.annotations.NotNull()
        public static final com.edify.learning.data.repository.UserAction.SaveNote INSTANCE = null;
        
        private SaveNote() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\r\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\bJ$\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0003H\u00d6\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/edify/learning/data/repository/UserAction$SendChat;", "Lcom/edify/learning/data/repository/UserAction;", "question", "", "curiosityScore", "", "(Ljava/lang/String;Ljava/lang/Integer;)V", "getCuriosityScore", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getQuestion", "()Ljava/lang/String;", "component1", "component2", "copy", "(Ljava/lang/String;Ljava/lang/Integer;)Lcom/edify/learning/data/repository/UserAction$SendChat;", "equals", "", "other", "", "hashCode", "toString", "app_debug"})
    public static final class SendChat extends com.edify.learning.data.repository.UserAction {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String question = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer curiosityScore = null;
        
        public SendChat(@org.jetbrains.annotations.NotNull()
        java.lang.String question, @org.jetbrains.annotations.Nullable()
        java.lang.Integer curiosityScore) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getQuestion() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getCuriosityScore() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.edify.learning.data.repository.UserAction.SendChat copy(@org.jetbrains.annotations.NotNull()
        java.lang.String question, @org.jetbrains.annotations.Nullable()
        java.lang.Integer curiosityScore) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ:\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0015J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\n\u00a8\u0006\u001c"}, d2 = {"Lcom/edify/learning/data/repository/UserAction$SubmitRevision;", "Lcom/edify/learning/data/repository/UserAction;", "questionId", "", "answer", "correctnessScore", "", "originalityScore", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;)V", "getAnswer", "()Ljava/lang/String;", "getCorrectnessScore", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getOriginalityScore", "getQuestionId", "component1", "component2", "component3", "component4", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/edify/learning/data/repository/UserAction$SubmitRevision;", "equals", "", "other", "", "hashCode", "toString", "app_debug"})
    public static final class SubmitRevision extends com.edify.learning.data.repository.UserAction {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String questionId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String answer = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer correctnessScore = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer originalityScore = null;
        
        public SubmitRevision(@org.jetbrains.annotations.NotNull()
        java.lang.String questionId, @org.jetbrains.annotations.NotNull()
        java.lang.String answer, @org.jetbrains.annotations.Nullable()
        java.lang.Integer correctnessScore, @org.jetbrains.annotations.Nullable()
        java.lang.Integer originalityScore) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getQuestionId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getAnswer() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getCorrectnessScore() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getOriginalityScore() {
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
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.edify.learning.data.repository.UserAction.SubmitRevision copy(@org.jetbrains.annotations.NotNull()
        java.lang.String questionId, @org.jetbrains.annotations.NotNull()
        java.lang.String answer, @org.jetbrains.annotations.Nullable()
        java.lang.Integer correctnessScore, @org.jetbrains.annotations.Nullable()
        java.lang.Integer originalityScore) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/edify/learning/data/repository/UserAction$Visit;", "Lcom/edify/learning/data/repository/UserAction;", "()V", "app_debug"})
    public static final class Visit extends com.edify.learning.data.repository.UserAction {
        @org.jetbrains.annotations.NotNull()
        public static final com.edify.learning.data.repository.UserAction.Visit INSTANCE = null;
        
        private Visit() {
        }
    }
}