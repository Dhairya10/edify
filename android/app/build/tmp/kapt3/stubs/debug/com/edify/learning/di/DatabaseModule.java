package com.edify.learning.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u000b\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\rH\u0007J\u0012\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u000fH\u0007J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u001c"}, d2 = {"Lcom/edify/learning/di/DatabaseModule;", "", "()V", "provideChapterDao", "Lcom/edify/learning/data/dao/ChapterDao;", "database", "Lcom/edify/learning/data/database/EdifyDatabase;", "provideChapterStatsDao", "Lcom/edify/learning/data/dao/ChapterStatsDao;", "provideChatDao", "Lcom/edify/learning/data/dao/ChatDao;", "provideEdifyDatabase", "context", "Landroid/content/Context;", "provideGemmaService", "Lcom/edify/learning/data/service/GemmaService;", "provideNoteDao", "Lcom/edify/learning/data/dao/NoteDao;", "provideQuestService", "Lcom/edify/learning/data/service/QuestService;", "chapterDao", "gemmaService", "provideSubjectDao", "Lcom/edify/learning/data/dao/SubjectDao;", "provideUserProfileDao", "Lcom/edify/learning/data/dao/UserProfileDao;", "provideUserResponseDao", "Lcom/edify/learning/data/dao/UserResponseDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.edify.learning.di.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.database.EdifyDatabase provideEdifyDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.dao.SubjectDao provideSubjectDao(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.database.EdifyDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.dao.ChapterDao provideChapterDao(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.database.EdifyDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.dao.NoteDao provideNoteDao(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.database.EdifyDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.dao.ChatDao provideChatDao(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.database.EdifyDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.dao.UserResponseDao provideUserResponseDao(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.database.EdifyDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.dao.ChapterStatsDao provideChapterStatsDao(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.database.EdifyDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.dao.UserProfileDao provideUserProfileDao(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.database.EdifyDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.service.GemmaService provideGemmaService(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.service.QuestService provideQuestService(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChapterDao chapterDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.GemmaService gemmaService) {
        return null;
    }
}