package com.edify.learning.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.edify.learning.data.model.GeneratedQuest;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GeneratedQuestDao_Impl implements GeneratedQuestDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GeneratedQuest> __insertionAdapterOfGeneratedQuest;

  private final EntityDeletionOrUpdateAdapter<GeneratedQuest> __deletionAdapterOfGeneratedQuest;

  private final EntityDeletionOrUpdateAdapter<GeneratedQuest> __updateAdapterOfGeneratedQuest;

  private final SharedSQLiteStatement __preparedStmtOfCompleteQuest;

  private final SharedSQLiteStatement __preparedStmtOfUnlockQuestByChapterId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteQuestById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllQuests;

  public GeneratedQuestDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGeneratedQuest = new EntityInsertionAdapter<GeneratedQuest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `generated_quests` (`id`,`chapterId`,`subjectName`,`title`,`question`,`questType`,`involvedChapterIds`,`userId`,`isCompleted`,`isUnlocked`,`userAnswer`,`completedAt`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GeneratedQuest entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getChapterId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getChapterId());
        }
        if (entity.getSubjectName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubjectName());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        if (entity.getQuestion() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getQuestion());
        }
        if (entity.getQuestType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getQuestType());
        }
        if (entity.getInvolvedChapterIds() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getInvolvedChapterIds());
        }
        if (entity.getUserId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getUserId());
        }
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final int _tmp_1 = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        if (entity.getUserAnswer() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getUserAnswer());
        }
        if (entity.getCompletedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getCompletedAt());
        }
        statement.bindLong(13, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfGeneratedQuest = new EntityDeletionOrUpdateAdapter<GeneratedQuest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `generated_quests` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GeneratedQuest entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
      }
    };
    this.__updateAdapterOfGeneratedQuest = new EntityDeletionOrUpdateAdapter<GeneratedQuest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `generated_quests` SET `id` = ?,`chapterId` = ?,`subjectName` = ?,`title` = ?,`question` = ?,`questType` = ?,`involvedChapterIds` = ?,`userId` = ?,`isCompleted` = ?,`isUnlocked` = ?,`userAnswer` = ?,`completedAt` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GeneratedQuest entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getChapterId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getChapterId());
        }
        if (entity.getSubjectName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubjectName());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        if (entity.getQuestion() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getQuestion());
        }
        if (entity.getQuestType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getQuestType());
        }
        if (entity.getInvolvedChapterIds() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getInvolvedChapterIds());
        }
        if (entity.getUserId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getUserId());
        }
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final int _tmp_1 = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        if (entity.getUserAnswer() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getUserAnswer());
        }
        if (entity.getCompletedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getCompletedAt());
        }
        statement.bindLong(13, entity.getCreatedAt());
        if (entity.getId() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getId());
        }
      }
    };
    this.__preparedStmtOfCompleteQuest = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE generated_quests SET isCompleted = 1, userAnswer = ?, completedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUnlockQuestByChapterId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE generated_quests SET isUnlocked = 1 WHERE chapterId = ? AND userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteQuestById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM generated_quests WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllQuests = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM generated_quests";
        return _query;
      }
    };
  }

  @Override
  public Object insertQuest(final GeneratedQuest quest, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfGeneratedQuest.insert(quest);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteQuest(final GeneratedQuest quest, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfGeneratedQuest.handle(quest);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateQuest(final GeneratedQuest quest, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfGeneratedQuest.handle(quest);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object completeQuest(final String questId, final String answer, final long completedAt,
      final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfCompleteQuest.acquire();
        int _argIndex = 1;
        if (answer == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, answer);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, completedAt);
        _argIndex = 3;
        if (questId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, questId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfCompleteQuest.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Object unlockQuestByChapterId(final String chapterId, final String userId,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUnlockQuestByChapterId.acquire();
        int _argIndex = 1;
        if (chapterId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, chapterId);
        }
        _argIndex = 2;
        if (userId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, userId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUnlockQuestByChapterId.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public Object deleteQuestById(final String questId, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteQuestById.acquire();
        int _argIndex = 1;
        if (questId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, questId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteQuestById.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteAllQuests(final Continuation<? super Unit> arg0) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllQuests.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllQuests.release(_stmt);
        }
      }
    }, arg0);
  }

  @Override
  public Flow<List<GeneratedQuest>> getAllQuests(final String userId) {
    final String _sql = "SELECT * FROM generated_quests WHERE userId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"generated_quests"}, new Callable<List<GeneratedQuest>>() {
      @Override
      @NonNull
      public List<GeneratedQuest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfQuestType = CursorUtil.getColumnIndexOrThrow(_cursor, "questType");
          final int _cursorIndexOfInvolvedChapterIds = CursorUtil.getColumnIndexOrThrow(_cursor, "involvedChapterIds");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUserAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "userAnswer");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<GeneratedQuest> _result = new ArrayList<GeneratedQuest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GeneratedQuest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpQuestType;
            if (_cursor.isNull(_cursorIndexOfQuestType)) {
              _tmpQuestType = null;
            } else {
              _tmpQuestType = _cursor.getString(_cursorIndexOfQuestType);
            }
            final String _tmpInvolvedChapterIds;
            if (_cursor.isNull(_cursorIndexOfInvolvedChapterIds)) {
              _tmpInvolvedChapterIds = null;
            } else {
              _tmpInvolvedChapterIds = _cursor.getString(_cursorIndexOfInvolvedChapterIds);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final boolean _tmpIsUnlocked;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_1 != 0;
            final String _tmpUserAnswer;
            if (_cursor.isNull(_cursorIndexOfUserAnswer)) {
              _tmpUserAnswer = null;
            } else {
              _tmpUserAnswer = _cursor.getString(_cursorIndexOfUserAnswer);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new GeneratedQuest(_tmpId,_tmpChapterId,_tmpSubjectName,_tmpTitle,_tmpQuestion,_tmpQuestType,_tmpInvolvedChapterIds,_tmpUserId,_tmpIsCompleted,_tmpIsUnlocked,_tmpUserAnswer,_tmpCompletedAt,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getQuestById(final String questId,
      final Continuation<? super GeneratedQuest> arg1) {
    final String _sql = "SELECT * FROM generated_quests WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (questId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, questId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<GeneratedQuest>() {
      @Override
      @Nullable
      public GeneratedQuest call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfQuestType = CursorUtil.getColumnIndexOrThrow(_cursor, "questType");
          final int _cursorIndexOfInvolvedChapterIds = CursorUtil.getColumnIndexOrThrow(_cursor, "involvedChapterIds");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUserAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "userAnswer");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final GeneratedQuest _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpQuestType;
            if (_cursor.isNull(_cursorIndexOfQuestType)) {
              _tmpQuestType = null;
            } else {
              _tmpQuestType = _cursor.getString(_cursorIndexOfQuestType);
            }
            final String _tmpInvolvedChapterIds;
            if (_cursor.isNull(_cursorIndexOfInvolvedChapterIds)) {
              _tmpInvolvedChapterIds = null;
            } else {
              _tmpInvolvedChapterIds = _cursor.getString(_cursorIndexOfInvolvedChapterIds);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final boolean _tmpIsUnlocked;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_1 != 0;
            final String _tmpUserAnswer;
            if (_cursor.isNull(_cursorIndexOfUserAnswer)) {
              _tmpUserAnswer = null;
            } else {
              _tmpUserAnswer = _cursor.getString(_cursorIndexOfUserAnswer);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new GeneratedQuest(_tmpId,_tmpChapterId,_tmpSubjectName,_tmpTitle,_tmpQuestion,_tmpQuestType,_tmpInvolvedChapterIds,_tmpUserId,_tmpIsCompleted,_tmpIsUnlocked,_tmpUserAnswer,_tmpCompletedAt,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<GeneratedQuest>> getIncompleteQuests(final String userId) {
    final String _sql = "SELECT * FROM generated_quests WHERE userId = ? AND isCompleted = 0 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"generated_quests"}, new Callable<List<GeneratedQuest>>() {
      @Override
      @NonNull
      public List<GeneratedQuest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfQuestType = CursorUtil.getColumnIndexOrThrow(_cursor, "questType");
          final int _cursorIndexOfInvolvedChapterIds = CursorUtil.getColumnIndexOrThrow(_cursor, "involvedChapterIds");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUserAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "userAnswer");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<GeneratedQuest> _result = new ArrayList<GeneratedQuest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GeneratedQuest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpQuestType;
            if (_cursor.isNull(_cursorIndexOfQuestType)) {
              _tmpQuestType = null;
            } else {
              _tmpQuestType = _cursor.getString(_cursorIndexOfQuestType);
            }
            final String _tmpInvolvedChapterIds;
            if (_cursor.isNull(_cursorIndexOfInvolvedChapterIds)) {
              _tmpInvolvedChapterIds = null;
            } else {
              _tmpInvolvedChapterIds = _cursor.getString(_cursorIndexOfInvolvedChapterIds);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final boolean _tmpIsUnlocked;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_1 != 0;
            final String _tmpUserAnswer;
            if (_cursor.isNull(_cursorIndexOfUserAnswer)) {
              _tmpUserAnswer = null;
            } else {
              _tmpUserAnswer = _cursor.getString(_cursorIndexOfUserAnswer);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new GeneratedQuest(_tmpId,_tmpChapterId,_tmpSubjectName,_tmpTitle,_tmpQuestion,_tmpQuestType,_tmpInvolvedChapterIds,_tmpUserId,_tmpIsCompleted,_tmpIsUnlocked,_tmpUserAnswer,_tmpCompletedAt,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<GeneratedQuest>> getCompletedQuests(final String userId) {
    final String _sql = "SELECT * FROM generated_quests WHERE userId = ? AND isCompleted = 1 ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"generated_quests"}, new Callable<List<GeneratedQuest>>() {
      @Override
      @NonNull
      public List<GeneratedQuest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfQuestType = CursorUtil.getColumnIndexOrThrow(_cursor, "questType");
          final int _cursorIndexOfInvolvedChapterIds = CursorUtil.getColumnIndexOrThrow(_cursor, "involvedChapterIds");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUserAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "userAnswer");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<GeneratedQuest> _result = new ArrayList<GeneratedQuest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GeneratedQuest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpQuestType;
            if (_cursor.isNull(_cursorIndexOfQuestType)) {
              _tmpQuestType = null;
            } else {
              _tmpQuestType = _cursor.getString(_cursorIndexOfQuestType);
            }
            final String _tmpInvolvedChapterIds;
            if (_cursor.isNull(_cursorIndexOfInvolvedChapterIds)) {
              _tmpInvolvedChapterIds = null;
            } else {
              _tmpInvolvedChapterIds = _cursor.getString(_cursorIndexOfInvolvedChapterIds);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final boolean _tmpIsUnlocked;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_1 != 0;
            final String _tmpUserAnswer;
            if (_cursor.isNull(_cursorIndexOfUserAnswer)) {
              _tmpUserAnswer = null;
            } else {
              _tmpUserAnswer = _cursor.getString(_cursorIndexOfUserAnswer);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new GeneratedQuest(_tmpId,_tmpChapterId,_tmpSubjectName,_tmpTitle,_tmpQuestion,_tmpQuestType,_tmpInvolvedChapterIds,_tmpUserId,_tmpIsCompleted,_tmpIsUnlocked,_tmpUserAnswer,_tmpCompletedAt,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getQuestCount(final String userId, final Continuation<? super Integer> arg1) {
    final String _sql = "SELECT COUNT(*) FROM generated_quests WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Object getQuestsByChapterId(final String chapterId, final String userId,
      final Continuation<? super List<GeneratedQuest>> arg2) {
    final String _sql = "SELECT * FROM generated_quests WHERE chapterId = ? AND userId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    _argIndex = 2;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<GeneratedQuest>>() {
      @Override
      @NonNull
      public List<GeneratedQuest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfQuestType = CursorUtil.getColumnIndexOrThrow(_cursor, "questType");
          final int _cursorIndexOfInvolvedChapterIds = CursorUtil.getColumnIndexOrThrow(_cursor, "involvedChapterIds");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUserAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "userAnswer");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<GeneratedQuest> _result = new ArrayList<GeneratedQuest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GeneratedQuest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpQuestType;
            if (_cursor.isNull(_cursorIndexOfQuestType)) {
              _tmpQuestType = null;
            } else {
              _tmpQuestType = _cursor.getString(_cursorIndexOfQuestType);
            }
            final String _tmpInvolvedChapterIds;
            if (_cursor.isNull(_cursorIndexOfInvolvedChapterIds)) {
              _tmpInvolvedChapterIds = null;
            } else {
              _tmpInvolvedChapterIds = _cursor.getString(_cursorIndexOfInvolvedChapterIds);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final boolean _tmpIsUnlocked;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_1 != 0;
            final String _tmpUserAnswer;
            if (_cursor.isNull(_cursorIndexOfUserAnswer)) {
              _tmpUserAnswer = null;
            } else {
              _tmpUserAnswer = _cursor.getString(_cursorIndexOfUserAnswer);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new GeneratedQuest(_tmpId,_tmpChapterId,_tmpSubjectName,_tmpTitle,_tmpQuestion,_tmpQuestType,_tmpInvolvedChapterIds,_tmpUserId,_tmpIsCompleted,_tmpIsUnlocked,_tmpUserAnswer,_tmpCompletedAt,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg2);
  }

  @Override
  public Object getQuestsByType(final String questType, final String userId,
      final Continuation<? super List<GeneratedQuest>> arg2) {
    final String _sql = "SELECT * FROM generated_quests WHERE questType = ? AND userId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (questType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, questType);
    }
    _argIndex = 2;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<GeneratedQuest>>() {
      @Override
      @NonNull
      public List<GeneratedQuest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfQuestType = CursorUtil.getColumnIndexOrThrow(_cursor, "questType");
          final int _cursorIndexOfInvolvedChapterIds = CursorUtil.getColumnIndexOrThrow(_cursor, "involvedChapterIds");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUserAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "userAnswer");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<GeneratedQuest> _result = new ArrayList<GeneratedQuest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GeneratedQuest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpQuestType;
            if (_cursor.isNull(_cursorIndexOfQuestType)) {
              _tmpQuestType = null;
            } else {
              _tmpQuestType = _cursor.getString(_cursorIndexOfQuestType);
            }
            final String _tmpInvolvedChapterIds;
            if (_cursor.isNull(_cursorIndexOfInvolvedChapterIds)) {
              _tmpInvolvedChapterIds = null;
            } else {
              _tmpInvolvedChapterIds = _cursor.getString(_cursorIndexOfInvolvedChapterIds);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final boolean _tmpIsUnlocked;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_1 != 0;
            final String _tmpUserAnswer;
            if (_cursor.isNull(_cursorIndexOfUserAnswer)) {
              _tmpUserAnswer = null;
            } else {
              _tmpUserAnswer = _cursor.getString(_cursorIndexOfUserAnswer);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new GeneratedQuest(_tmpId,_tmpChapterId,_tmpSubjectName,_tmpTitle,_tmpQuestion,_tmpQuestType,_tmpInvolvedChapterIds,_tmpUserId,_tmpIsCompleted,_tmpIsUnlocked,_tmpUserAnswer,_tmpCompletedAt,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg2);
  }

  @Override
  public Object getPastQuestsForChapters(final List<String> chapterIds, final String chapterId,
      final String userId, final Continuation<? super List<GeneratedQuest>> arg3) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM generated_quests WHERE (chapterId IN (");
    final int _inputSize = chapterIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") OR involvedChapterIds LIKE '%' || ");
    _stringBuilder.append("?");
    _stringBuilder.append(" || '%') AND userId = ");
    _stringBuilder.append("?");
    _stringBuilder.append(" ORDER BY createdAt DESC");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 2 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : chapterIds) {
      if (_item == null) {
        _statement.bindNull(_argIndex);
      } else {
        _statement.bindString(_argIndex, _item);
      }
      _argIndex++;
    }
    _argIndex = 1 + _inputSize;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    _argIndex = 2 + _inputSize;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<GeneratedQuest>>() {
      @Override
      @NonNull
      public List<GeneratedQuest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfQuestType = CursorUtil.getColumnIndexOrThrow(_cursor, "questType");
          final int _cursorIndexOfInvolvedChapterIds = CursorUtil.getColumnIndexOrThrow(_cursor, "involvedChapterIds");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUserAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "userAnswer");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<GeneratedQuest> _result = new ArrayList<GeneratedQuest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GeneratedQuest _item_1;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpQuestType;
            if (_cursor.isNull(_cursorIndexOfQuestType)) {
              _tmpQuestType = null;
            } else {
              _tmpQuestType = _cursor.getString(_cursorIndexOfQuestType);
            }
            final String _tmpInvolvedChapterIds;
            if (_cursor.isNull(_cursorIndexOfInvolvedChapterIds)) {
              _tmpInvolvedChapterIds = null;
            } else {
              _tmpInvolvedChapterIds = _cursor.getString(_cursorIndexOfInvolvedChapterIds);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final boolean _tmpIsUnlocked;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_1 != 0;
            final String _tmpUserAnswer;
            if (_cursor.isNull(_cursorIndexOfUserAnswer)) {
              _tmpUserAnswer = null;
            } else {
              _tmpUserAnswer = _cursor.getString(_cursorIndexOfUserAnswer);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item_1 = new GeneratedQuest(_tmpId,_tmpChapterId,_tmpSubjectName,_tmpTitle,_tmpQuestion,_tmpQuestType,_tmpInvolvedChapterIds,_tmpUserId,_tmpIsCompleted,_tmpIsUnlocked,_tmpUserAnswer,_tmpCompletedAt,_tmpCreatedAt);
            _result.add(_item_1);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg3);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
