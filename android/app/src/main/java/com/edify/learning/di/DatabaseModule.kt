package com.edify.learning.di

import android.content.Context
import androidx.room.Room
import com.edify.learning.data.dao.*
import com.edify.learning.data.dao.RevisionSubmissionDao
import com.edify.learning.data.database.TranslatedChapterDao
import com.edify.learning.data.database.EdifyDatabase
import com.edify.learning.data.service.GemmaService
import com.edify.learning.data.service.QuestService
import com.edify.learning.data.service.QuestGenerationService
import com.edify.learning.data.service.PromptService
import com.edify.learning.data.service.TranslationCacheService
import com.edify.learning.data.service.RevisionEvaluationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideEdifyDatabase(
        @ApplicationContext context: Context
    ): EdifyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            EdifyDatabase::class.java,
            EdifyDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    fun provideSubjectDao(database: EdifyDatabase): SubjectDao {
        return database.subjectDao()
    }
    
    @Provides
    fun provideChapterDao(database: EdifyDatabase): ChapterDao {
        return database.chapterDao()
    }
    
    @Provides
    fun provideNoteDao(database: EdifyDatabase): NoteDao {
        return database.noteDao()
    }
    
    @Provides
    fun provideChatDao(database: EdifyDatabase): ChatDao {
        return database.chatDao()
    }
    
    @Provides
    fun provideUserResponseDao(database: EdifyDatabase): UserResponseDao {
        return database.userResponseDao()
    }
    
    
    @Provides
    fun provideRevisionSubmissionDao(database: EdifyDatabase): RevisionSubmissionDao {
        return database.revisionSubmissionDao()
    }
    
    @Provides
    fun provideChapterStatsDao(database: EdifyDatabase): ChapterStatsDao {
        return database.chapterStatsDao()
    }
    
    @Provides
    fun provideUserProfileDao(database: EdifyDatabase): UserProfileDao {
        return database.userProfileDao()
    }
    
    @Provides
    fun provideGeneratedQuestDao(database: EdifyDatabase): GeneratedQuestDao {
        return database.generatedQuestDao()
    }
    
    @Provides
    fun provideTranslatedChapterDao(database: EdifyDatabase): TranslatedChapterDao {
        return database.translatedChapterDao()
    }
    
    @Provides
    @Singleton
    fun providePromptService(
        @ApplicationContext context: Context
    ): PromptService {
        return PromptService(context)
    }

    @Provides
    @Singleton
    fun provideGemmaService(
        @ApplicationContext context: Context,
        promptService: PromptService
    ): GemmaService {
        return GemmaService(context, promptService)
    }
    
    @Provides
    @Singleton
    fun provideQuestService(
        chapterDao: ChapterDao
    ): QuestService {
        return QuestService(chapterDao)
    }
    
    @Provides
    @Singleton
    fun provideQuestGenerationService(
        chapterStatsDao: ChapterStatsDao,
        generatedQuestDao: GeneratedQuestDao,
        chapterDao: ChapterDao,
        noteDao: NoteDao,
        chatDao: ChatDao,
        subjectDao: SubjectDao,
        gemmaService: GemmaService,
        promptService: PromptService
    ): QuestGenerationService {
        return QuestGenerationService(
            chapterStatsDao,
            generatedQuestDao,
            chapterDao,
            noteDao,
            chatDao,
            subjectDao,
            gemmaService,
            promptService
        )
    }
    
    @Provides
    @Singleton
    fun provideTranslationCacheService(
        translatedChapterDao: TranslatedChapterDao
    ): TranslationCacheService {
        return TranslationCacheService(translatedChapterDao)
    }
    
    @Provides
    @Singleton
    fun provideRevisionEvaluationService(
        @ApplicationContext context: Context,
        gemmaService: GemmaService,
        promptService: PromptService,
        revisionSubmissionDao: RevisionSubmissionDao
    ): RevisionEvaluationService {
        return RevisionEvaluationService(context, gemmaService, promptService, revisionSubmissionDao)
    }
}
