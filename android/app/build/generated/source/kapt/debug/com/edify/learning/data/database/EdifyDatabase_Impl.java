package com.edify.learning.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.edify.learning.data.dao.ChapterDao;
import com.edify.learning.data.dao.ChapterDao_Impl;
import com.edify.learning.data.dao.ChapterStatsDao;
import com.edify.learning.data.dao.ChapterStatsDao_Impl;
import com.edify.learning.data.dao.ChatDao;
import com.edify.learning.data.dao.ChatDao_Impl;
import com.edify.learning.data.dao.GeneratedQuestDao;
import com.edify.learning.data.dao.GeneratedQuestDao_Impl;
import com.edify.learning.data.dao.NoteDao;
import com.edify.learning.data.dao.NoteDao_Impl;
import com.edify.learning.data.dao.RevisionResponseDao;
import com.edify.learning.data.dao.RevisionResponseDao_Impl;
import com.edify.learning.data.dao.SubjectDao;
import com.edify.learning.data.dao.SubjectDao_Impl;
import com.edify.learning.data.dao.UserProfileDao;
import com.edify.learning.data.dao.UserProfileDao_Impl;
import com.edify.learning.data.dao.UserResponseDao;
import com.edify.learning.data.dao.UserResponseDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EdifyDatabase_Impl extends EdifyDatabase {
  private volatile SubjectDao _subjectDao;

  private volatile ChapterDao _chapterDao;

  private volatile NoteDao _noteDao;

  private volatile ChatDao _chatDao;

  private volatile UserResponseDao _userResponseDao;

  private volatile RevisionResponseDao _revisionResponseDao;

  private volatile ChapterStatsDao _chapterStatsDao;

  private volatile UserProfileDao _userProfileDao;

  private volatile GeneratedQuestDao _generatedQuestDao;

  private volatile TranslatedChapterDao _translatedChapterDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(13) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `subjects` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `color` TEXT NOT NULL, `iconRes` TEXT NOT NULL, `totalChapters` INTEGER NOT NULL, `completedChapters` INTEGER NOT NULL, `lastReadChapterId` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `chapters` (`id` TEXT NOT NULL, `subjectId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `content` TEXT NOT NULL, `chapterNumber` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `readingProgress` REAL NOT NULL, `estimatedReadingTime` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`subjectId`) REFERENCES `subjects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` TEXT NOT NULL, `chapterId` TEXT NOT NULL, `title` TEXT NOT NULL, `content` TEXT NOT NULL, `type` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`chapterId`) REFERENCES `chapters`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `chat_messages` (`id` TEXT NOT NULL, `sessionId` TEXT NOT NULL, `chapterId` TEXT NOT NULL, `content` TEXT NOT NULL, `isFromUser` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `messageType` TEXT NOT NULL, `attachmentPath` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_responses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `chapterId` TEXT NOT NULL, `exerciseIndex` INTEGER NOT NULL, `textResponse` TEXT, `imageUri` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, FOREIGN KEY(`chapterId`) REFERENCES `chapters`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_user_responses_chapterId_exerciseIndex` ON `user_responses` (`chapterId`, `exerciseIndex`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `revision_responses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `chapterId` TEXT NOT NULL, `questionIndex` INTEGER NOT NULL, `question` TEXT NOT NULL, `userAnswer` TEXT, `imageUri` TEXT, `gemmaFeedback` TEXT, `feedbackCategory` TEXT, `isSubmitted` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, FOREIGN KEY(`chapterId`) REFERENCES `chapters`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_revision_responses_chapterId_questionIndex` ON `revision_responses` (`chapterId`, `questionIndex`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `chapter_stats` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `chapterId` TEXT NOT NULL, `userId` TEXT NOT NULL, `visitCount` INTEGER NOT NULL, `noteCount` INTEGER NOT NULL, `questGenerated` INTEGER NOT NULL, `divergentQuestGenerated` INTEGER NOT NULL, `revisionHistory` TEXT NOT NULL, `chatHistory` TEXT NOT NULL, `lastVisited` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_profile` (`userId` TEXT NOT NULL, `name` TEXT NOT NULL, `languagePreference` TEXT NOT NULL, `hasCompletedOnboarding` INTEGER NOT NULL, `hasUnlockedPersonalizedQuests` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`userId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `generated_quests` (`id` TEXT NOT NULL, `chapterId` TEXT NOT NULL, `subjectName` TEXT NOT NULL, `title` TEXT NOT NULL, `question` TEXT NOT NULL, `questType` TEXT NOT NULL, `involvedChapterIds` TEXT NOT NULL, `userId` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, `isUnlocked` INTEGER NOT NULL, `userAnswer` TEXT, `completedAt` INTEGER, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `translated_chapters` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `chapterId` TEXT NOT NULL, `language` TEXT NOT NULL, `originalContentHash` TEXT NOT NULL, `translatedContent` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_translated_chapters_chapterId_language` ON `translated_chapters` (`chapterId`, `language`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f63875b3fa4fc9c5198f92b989063df1')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `subjects`");
        db.execSQL("DROP TABLE IF EXISTS `chapters`");
        db.execSQL("DROP TABLE IF EXISTS `notes`");
        db.execSQL("DROP TABLE IF EXISTS `chat_messages`");
        db.execSQL("DROP TABLE IF EXISTS `user_responses`");
        db.execSQL("DROP TABLE IF EXISTS `revision_responses`");
        db.execSQL("DROP TABLE IF EXISTS `chapter_stats`");
        db.execSQL("DROP TABLE IF EXISTS `user_profile`");
        db.execSQL("DROP TABLE IF EXISTS `generated_quests`");
        db.execSQL("DROP TABLE IF EXISTS `translated_chapters`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSubjects = new HashMap<String, TableInfo.Column>(10);
        _columnsSubjects.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("color", new TableInfo.Column("color", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("iconRes", new TableInfo.Column("iconRes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("totalChapters", new TableInfo.Column("totalChapters", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("completedChapters", new TableInfo.Column("completedChapters", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("lastReadChapterId", new TableInfo.Column("lastReadChapterId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubjects = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubjects = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubjects = new TableInfo("subjects", _columnsSubjects, _foreignKeysSubjects, _indicesSubjects);
        final TableInfo _existingSubjects = TableInfo.read(db, "subjects");
        if (!_infoSubjects.equals(_existingSubjects)) {
          return new RoomOpenHelper.ValidationResult(false, "subjects(com.edify.learning.data.model.Subject).\n"
                  + " Expected:\n" + _infoSubjects + "\n"
                  + " Found:\n" + _existingSubjects);
        }
        final HashMap<String, TableInfo.Column> _columnsChapters = new HashMap<String, TableInfo.Column>(11);
        _columnsChapters.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapters.put("subjectId", new TableInfo.Column("subjectId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapters.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapters.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapters.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapters.put("chapterNumber", new TableInfo.Column("chapterNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapters.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapters.put("readingProgress", new TableInfo.Column("readingProgress", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapters.put("estimatedReadingTime", new TableInfo.Column("estimatedReadingTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapters.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapters.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChapters = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysChapters.add(new TableInfo.ForeignKey("subjects", "CASCADE", "NO ACTION", Arrays.asList("subjectId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesChapters = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChapters = new TableInfo("chapters", _columnsChapters, _foreignKeysChapters, _indicesChapters);
        final TableInfo _existingChapters = TableInfo.read(db, "chapters");
        if (!_infoChapters.equals(_existingChapters)) {
          return new RoomOpenHelper.ValidationResult(false, "chapters(com.edify.learning.data.model.Chapter).\n"
                  + " Expected:\n" + _infoChapters + "\n"
                  + " Found:\n" + _existingChapters);
        }
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(7);
        _columnsNotes.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("chapterId", new TableInfo.Column("chapterId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysNotes.add(new TableInfo.ForeignKey("chapters", "CASCADE", "NO ACTION", Arrays.asList("chapterId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(db, "notes");
        if (!_infoNotes.equals(_existingNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "notes(com.edify.learning.data.model.Note).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsChatMessages = new HashMap<String, TableInfo.Column>(8);
        _columnsChatMessages.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("sessionId", new TableInfo.Column("sessionId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("chapterId", new TableInfo.Column("chapterId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("isFromUser", new TableInfo.Column("isFromUser", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("messageType", new TableInfo.Column("messageType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("attachmentPath", new TableInfo.Column("attachmentPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChatMessages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChatMessages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChatMessages = new TableInfo("chat_messages", _columnsChatMessages, _foreignKeysChatMessages, _indicesChatMessages);
        final TableInfo _existingChatMessages = TableInfo.read(db, "chat_messages");
        if (!_infoChatMessages.equals(_existingChatMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "chat_messages(com.edify.learning.data.model.ChatMessage).\n"
                  + " Expected:\n" + _infoChatMessages + "\n"
                  + " Found:\n" + _existingChatMessages);
        }
        final HashMap<String, TableInfo.Column> _columnsUserResponses = new HashMap<String, TableInfo.Column>(7);
        _columnsUserResponses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserResponses.put("chapterId", new TableInfo.Column("chapterId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserResponses.put("exerciseIndex", new TableInfo.Column("exerciseIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserResponses.put("textResponse", new TableInfo.Column("textResponse", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserResponses.put("imageUri", new TableInfo.Column("imageUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserResponses.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserResponses.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserResponses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysUserResponses.add(new TableInfo.ForeignKey("chapters", "CASCADE", "NO ACTION", Arrays.asList("chapterId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesUserResponses = new HashSet<TableInfo.Index>(1);
        _indicesUserResponses.add(new TableInfo.Index("index_user_responses_chapterId_exerciseIndex", true, Arrays.asList("chapterId", "exerciseIndex"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoUserResponses = new TableInfo("user_responses", _columnsUserResponses, _foreignKeysUserResponses, _indicesUserResponses);
        final TableInfo _existingUserResponses = TableInfo.read(db, "user_responses");
        if (!_infoUserResponses.equals(_existingUserResponses)) {
          return new RoomOpenHelper.ValidationResult(false, "user_responses(com.edify.learning.data.model.UserResponse).\n"
                  + " Expected:\n" + _infoUserResponses + "\n"
                  + " Found:\n" + _existingUserResponses);
        }
        final HashMap<String, TableInfo.Column> _columnsRevisionResponses = new HashMap<String, TableInfo.Column>(11);
        _columnsRevisionResponses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRevisionResponses.put("chapterId", new TableInfo.Column("chapterId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRevisionResponses.put("questionIndex", new TableInfo.Column("questionIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRevisionResponses.put("question", new TableInfo.Column("question", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRevisionResponses.put("userAnswer", new TableInfo.Column("userAnswer", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRevisionResponses.put("imageUri", new TableInfo.Column("imageUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRevisionResponses.put("gemmaFeedback", new TableInfo.Column("gemmaFeedback", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRevisionResponses.put("feedbackCategory", new TableInfo.Column("feedbackCategory", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRevisionResponses.put("isSubmitted", new TableInfo.Column("isSubmitted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRevisionResponses.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRevisionResponses.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRevisionResponses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRevisionResponses.add(new TableInfo.ForeignKey("chapters", "CASCADE", "NO ACTION", Arrays.asList("chapterId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesRevisionResponses = new HashSet<TableInfo.Index>(1);
        _indicesRevisionResponses.add(new TableInfo.Index("index_revision_responses_chapterId_questionIndex", true, Arrays.asList("chapterId", "questionIndex"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoRevisionResponses = new TableInfo("revision_responses", _columnsRevisionResponses, _foreignKeysRevisionResponses, _indicesRevisionResponses);
        final TableInfo _existingRevisionResponses = TableInfo.read(db, "revision_responses");
        if (!_infoRevisionResponses.equals(_existingRevisionResponses)) {
          return new RoomOpenHelper.ValidationResult(false, "revision_responses(com.edify.learning.data.model.RevisionResponse).\n"
                  + " Expected:\n" + _infoRevisionResponses + "\n"
                  + " Found:\n" + _existingRevisionResponses);
        }
        final HashMap<String, TableInfo.Column> _columnsChapterStats = new HashMap<String, TableInfo.Column>(12);
        _columnsChapterStats.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("chapterId", new TableInfo.Column("chapterId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("visitCount", new TableInfo.Column("visitCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("noteCount", new TableInfo.Column("noteCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("questGenerated", new TableInfo.Column("questGenerated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("divergentQuestGenerated", new TableInfo.Column("divergentQuestGenerated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("revisionHistory", new TableInfo.Column("revisionHistory", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("chatHistory", new TableInfo.Column("chatHistory", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("lastVisited", new TableInfo.Column("lastVisited", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChapterStats.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChapterStats = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChapterStats = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChapterStats = new TableInfo("chapter_stats", _columnsChapterStats, _foreignKeysChapterStats, _indicesChapterStats);
        final TableInfo _existingChapterStats = TableInfo.read(db, "chapter_stats");
        if (!_infoChapterStats.equals(_existingChapterStats)) {
          return new RoomOpenHelper.ValidationResult(false, "chapter_stats(com.edify.learning.data.model.ChapterStats).\n"
                  + " Expected:\n" + _infoChapterStats + "\n"
                  + " Found:\n" + _existingChapterStats);
        }
        final HashMap<String, TableInfo.Column> _columnsUserProfile = new HashMap<String, TableInfo.Column>(7);
        _columnsUserProfile.put("userId", new TableInfo.Column("userId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("languagePreference", new TableInfo.Column("languagePreference", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("hasCompletedOnboarding", new TableInfo.Column("hasCompletedOnboarding", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("hasUnlockedPersonalizedQuests", new TableInfo.Column("hasUnlockedPersonalizedQuests", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserProfile = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserProfile = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProfile = new TableInfo("user_profile", _columnsUserProfile, _foreignKeysUserProfile, _indicesUserProfile);
        final TableInfo _existingUserProfile = TableInfo.read(db, "user_profile");
        if (!_infoUserProfile.equals(_existingUserProfile)) {
          return new RoomOpenHelper.ValidationResult(false, "user_profile(com.edify.learning.data.model.UserProfile).\n"
                  + " Expected:\n" + _infoUserProfile + "\n"
                  + " Found:\n" + _existingUserProfile);
        }
        final HashMap<String, TableInfo.Column> _columnsGeneratedQuests = new HashMap<String, TableInfo.Column>(13);
        _columnsGeneratedQuests.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("chapterId", new TableInfo.Column("chapterId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("subjectName", new TableInfo.Column("subjectName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("question", new TableInfo.Column("question", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("questType", new TableInfo.Column("questType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("involvedChapterIds", new TableInfo.Column("involvedChapterIds", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("userAnswer", new TableInfo.Column("userAnswer", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeneratedQuests.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGeneratedQuests = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGeneratedQuests = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGeneratedQuests = new TableInfo("generated_quests", _columnsGeneratedQuests, _foreignKeysGeneratedQuests, _indicesGeneratedQuests);
        final TableInfo _existingGeneratedQuests = TableInfo.read(db, "generated_quests");
        if (!_infoGeneratedQuests.equals(_existingGeneratedQuests)) {
          return new RoomOpenHelper.ValidationResult(false, "generated_quests(com.edify.learning.data.model.GeneratedQuest).\n"
                  + " Expected:\n" + _infoGeneratedQuests + "\n"
                  + " Found:\n" + _existingGeneratedQuests);
        }
        final HashMap<String, TableInfo.Column> _columnsTranslatedChapters = new HashMap<String, TableInfo.Column>(7);
        _columnsTranslatedChapters.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslatedChapters.put("chapterId", new TableInfo.Column("chapterId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslatedChapters.put("language", new TableInfo.Column("language", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslatedChapters.put("originalContentHash", new TableInfo.Column("originalContentHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslatedChapters.put("translatedContent", new TableInfo.Column("translatedContent", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslatedChapters.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslatedChapters.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTranslatedChapters = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTranslatedChapters = new HashSet<TableInfo.Index>(1);
        _indicesTranslatedChapters.add(new TableInfo.Index("index_translated_chapters_chapterId_language", true, Arrays.asList("chapterId", "language"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoTranslatedChapters = new TableInfo("translated_chapters", _columnsTranslatedChapters, _foreignKeysTranslatedChapters, _indicesTranslatedChapters);
        final TableInfo _existingTranslatedChapters = TableInfo.read(db, "translated_chapters");
        if (!_infoTranslatedChapters.equals(_existingTranslatedChapters)) {
          return new RoomOpenHelper.ValidationResult(false, "translated_chapters(com.edify.learning.data.model.TranslatedChapter).\n"
                  + " Expected:\n" + _infoTranslatedChapters + "\n"
                  + " Found:\n" + _existingTranslatedChapters);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f63875b3fa4fc9c5198f92b989063df1", "1a688b222fe1af51c8379f0f2a548d9b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "subjects","chapters","notes","chat_messages","user_responses","revision_responses","chapter_stats","user_profile","generated_quests","translated_chapters");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `subjects`");
      _db.execSQL("DELETE FROM `chapters`");
      _db.execSQL("DELETE FROM `notes`");
      _db.execSQL("DELETE FROM `chat_messages`");
      _db.execSQL("DELETE FROM `user_responses`");
      _db.execSQL("DELETE FROM `revision_responses`");
      _db.execSQL("DELETE FROM `chapter_stats`");
      _db.execSQL("DELETE FROM `user_profile`");
      _db.execSQL("DELETE FROM `generated_quests`");
      _db.execSQL("DELETE FROM `translated_chapters`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SubjectDao.class, SubjectDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ChapterDao.class, ChapterDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NoteDao.class, NoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ChatDao.class, ChatDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserResponseDao.class, UserResponseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RevisionResponseDao.class, RevisionResponseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ChapterStatsDao.class, ChapterStatsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserProfileDao.class, UserProfileDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GeneratedQuestDao.class, GeneratedQuestDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TranslatedChapterDao.class, TranslatedChapterDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public SubjectDao subjectDao() {
    if (_subjectDao != null) {
      return _subjectDao;
    } else {
      synchronized(this) {
        if(_subjectDao == null) {
          _subjectDao = new SubjectDao_Impl(this);
        }
        return _subjectDao;
      }
    }
  }

  @Override
  public ChapterDao chapterDao() {
    if (_chapterDao != null) {
      return _chapterDao;
    } else {
      synchronized(this) {
        if(_chapterDao == null) {
          _chapterDao = new ChapterDao_Impl(this);
        }
        return _chapterDao;
      }
    }
  }

  @Override
  public NoteDao noteDao() {
    if (_noteDao != null) {
      return _noteDao;
    } else {
      synchronized(this) {
        if(_noteDao == null) {
          _noteDao = new NoteDao_Impl(this);
        }
        return _noteDao;
      }
    }
  }

  @Override
  public ChatDao chatDao() {
    if (_chatDao != null) {
      return _chatDao;
    } else {
      synchronized(this) {
        if(_chatDao == null) {
          _chatDao = new ChatDao_Impl(this);
        }
        return _chatDao;
      }
    }
  }

  @Override
  public UserResponseDao userResponseDao() {
    if (_userResponseDao != null) {
      return _userResponseDao;
    } else {
      synchronized(this) {
        if(_userResponseDao == null) {
          _userResponseDao = new UserResponseDao_Impl(this);
        }
        return _userResponseDao;
      }
    }
  }

  @Override
  public RevisionResponseDao revisionResponseDao() {
    if (_revisionResponseDao != null) {
      return _revisionResponseDao;
    } else {
      synchronized(this) {
        if(_revisionResponseDao == null) {
          _revisionResponseDao = new RevisionResponseDao_Impl(this);
        }
        return _revisionResponseDao;
      }
    }
  }

  @Override
  public ChapterStatsDao chapterStatsDao() {
    if (_chapterStatsDao != null) {
      return _chapterStatsDao;
    } else {
      synchronized(this) {
        if(_chapterStatsDao == null) {
          _chapterStatsDao = new ChapterStatsDao_Impl(this);
        }
        return _chapterStatsDao;
      }
    }
  }

  @Override
  public UserProfileDao userProfileDao() {
    if (_userProfileDao != null) {
      return _userProfileDao;
    } else {
      synchronized(this) {
        if(_userProfileDao == null) {
          _userProfileDao = new UserProfileDao_Impl(this);
        }
        return _userProfileDao;
      }
    }
  }

  @Override
  public GeneratedQuestDao generatedQuestDao() {
    if (_generatedQuestDao != null) {
      return _generatedQuestDao;
    } else {
      synchronized(this) {
        if(_generatedQuestDao == null) {
          _generatedQuestDao = new GeneratedQuestDao_Impl(this);
        }
        return _generatedQuestDao;
      }
    }
  }

  @Override
  public TranslatedChapterDao translatedChapterDao() {
    if (_translatedChapterDao != null) {
      return _translatedChapterDao;
    } else {
      synchronized(this) {
        if(_translatedChapterDao == null) {
          _translatedChapterDao = new TranslatedChapterDao_Impl(this);
        }
        return _translatedChapterDao;
      }
    }
  }
}
