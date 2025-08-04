package com.edify.learning.data.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0016H&\u00a8\u0006\u0018"}, d2 = {"Lcom/edify/learning/data/database/EdifyDatabase;", "Landroidx/room/RoomDatabase;", "()V", "chapterDao", "Lcom/edify/learning/data/dao/ChapterDao;", "chapterStatsDao", "Lcom/edify/learning/data/dao/ChapterStatsDao;", "chatDao", "Lcom/edify/learning/data/dao/ChatDao;", "generatedQuestDao", "Lcom/edify/learning/data/dao/GeneratedQuestDao;", "noteDao", "Lcom/edify/learning/data/dao/NoteDao;", "revisionSubmissionDao", "Lcom/edify/learning/data/dao/RevisionSubmissionDao;", "subjectDao", "Lcom/edify/learning/data/dao/SubjectDao;", "translatedChapterDao", "Lcom/edify/learning/data/database/TranslatedChapterDao;", "userProfileDao", "Lcom/edify/learning/data/dao/UserProfileDao;", "userResponseDao", "Lcom/edify/learning/data/dao/UserResponseDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.edify.learning.data.model.Subject.class, com.edify.learning.data.model.Chapter.class, com.edify.learning.data.model.Note.class, com.edify.learning.data.model.ChatMessage.class, com.edify.learning.data.model.UserResponse.class, com.edify.learning.data.model.RevisionSubmission.class, com.edify.learning.data.model.ChapterStats.class, com.edify.learning.data.model.UserProfile.class, com.edify.learning.data.model.GeneratedQuest.class, com.edify.learning.data.model.TranslatedChapter.class}, version = 17, exportSchema = false)
@androidx.room.TypeConverters(value = {com.edify.learning.data.database.Converters.class})
public abstract class EdifyDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATABASE_NAME = "edify_database";
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.edify.learning.data.database.EdifyDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.edify.learning.data.database.EdifyDatabase.Companion Companion = null;
    
    public EdifyDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.edify.learning.data.dao.SubjectDao subjectDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.edify.learning.data.dao.ChapterDao chapterDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.edify.learning.data.dao.NoteDao noteDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.edify.learning.data.dao.ChatDao chatDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.edify.learning.data.dao.UserResponseDao userResponseDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.edify.learning.data.dao.RevisionSubmissionDao revisionSubmissionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.edify.learning.data.dao.ChapterStatsDao chapterStatsDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.edify.learning.data.dao.UserProfileDao userProfileDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.edify.learning.data.dao.GeneratedQuestDao generatedQuestDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.edify.learning.data.database.TranslatedChapterDao translatedChapterDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/edify/learning/data/database/EdifyDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "INSTANCE", "Lcom/edify/learning/data/database/EdifyDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.edify.learning.data.database.EdifyDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}