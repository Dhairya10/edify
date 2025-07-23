package com.edify.learning.di

import android.content.Context
import androidx.room.Room
import com.edify.learning.data.dao.*
import com.edify.learning.data.database.EdifyDatabase
import com.edify.learning.data.service.GemmaService
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
    @Singleton
    fun provideGemmaService(
        @ApplicationContext context: Context
    ): GemmaService {
        return GemmaService(context)
    }
}
