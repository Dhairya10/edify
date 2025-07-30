package com.edify.learning.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u000b\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\rH\u0007J\u001a\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0016\u001a\u00020\u00112\b\b\u0001\u0010\f\u001a\u00020\rH\u0007JH\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010!\u001a\u00020\"2\u0006\u0010\u001b\u001a\u00020\u0004H\u0007J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010$\u001a\u00020%2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010&\u001a\u00020\'2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006("}, d2 = {"Lcom/edify/learning/di/DatabaseModule;", "", "()V", "provideChapterDao", "Lcom/edify/learning/data/dao/ChapterDao;", "database", "Lcom/edify/learning/data/database/EdifyDatabase;", "provideChapterStatsDao", "Lcom/edify/learning/data/dao/ChapterStatsDao;", "provideChatDao", "Lcom/edify/learning/data/dao/ChatDao;", "provideEdifyDatabase", "context", "Landroid/content/Context;", "provideGemmaService", "Lcom/edify/learning/data/service/GemmaService;", "promptService", "Lcom/edify/learning/data/service/PromptService;", "provideGeneratedQuestDao", "Lcom/edify/learning/data/dao/GeneratedQuestDao;", "provideNoteDao", "Lcom/edify/learning/data/dao/NoteDao;", "providePromptService", "provideQuestGenerationService", "Lcom/edify/learning/data/service/QuestGenerationService;", "chapterStatsDao", "generatedQuestDao", "chapterDao", "noteDao", "chatDao", "subjectDao", "Lcom/edify/learning/data/dao/SubjectDao;", "gemmaService", "provideQuestService", "Lcom/edify/learning/data/service/QuestService;", "provideSubjectDao", "provideUserProfileDao", "Lcom/edify/learning/data/dao/UserProfileDao;", "provideUserResponseDao", "Lcom/edify/learning/data/dao/UserResponseDao;", "app_debug"})
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
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.dao.GeneratedQuestDao provideGeneratedQuestDao(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.database.EdifyDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.service.PromptService providePromptService(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.service.GemmaService provideGemmaService(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.PromptService promptService) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.service.QuestService provideQuestService(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChapterDao chapterDao) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.service.QuestGenerationService provideQuestGenerationService(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChapterStatsDao chapterStatsDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.GeneratedQuestDao generatedQuestDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChapterDao chapterDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.NoteDao noteDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.ChatDao chatDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.dao.SubjectDao subjectDao, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.GemmaService gemmaService, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.PromptService promptService) {
        return null;
    }
}