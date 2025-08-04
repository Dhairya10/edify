package com.edify.learning.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.edify.learning.data.dao.*
import com.edify.learning.data.dao.RevisionSubmissionDao
import com.edify.learning.data.model.*
import com.edify.learning.data.model.RevisionSubmission
@Database(
    entities = [
        Subject::class,
        Chapter::class,
        Note::class,
        ChatMessage::class,
        UserResponse::class,
        RevisionResponse::class,
        RevisionSubmission::class,
        ChapterStats::class,
        UserProfile::class,
        GeneratedQuest::class,
        TranslatedChapter::class
    ],
    version = 15,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class EdifyDatabase : RoomDatabase() {
    
    abstract fun subjectDao(): SubjectDao
    abstract fun chapterDao(): ChapterDao
    abstract fun noteDao(): NoteDao
    abstract fun chatDao(): ChatDao
    abstract fun userResponseDao(): UserResponseDao
    abstract fun revisionResponseDao(): RevisionResponseDao
    abstract fun revisionSubmissionDao(): RevisionSubmissionDao
    abstract fun chapterStatsDao(): ChapterStatsDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun generatedQuestDao(): GeneratedQuestDao
    abstract fun translatedChapterDao(): TranslatedChapterDao
    
    companion object {
        const val DATABASE_NAME = "edify_database"
        
        @Volatile
        private var INSTANCE: EdifyDatabase? = null
        
        fun getDatabase(context: Context): EdifyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EdifyDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
