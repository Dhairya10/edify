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
import androidx.sqlite.db.SupportSQLiteStatement;
import com.edify.learning.data.model.ChapterStats;
import com.edify.learning.data.model.ChapterStatsConverters;
import com.edify.learning.data.model.ChatEntry;
import com.edify.learning.data.model.RevisionEntry;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ChapterStatsDao_Impl implements ChapterStatsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChapterStats> __insertionAdapterOfChapterStats;

  private final ChapterStatsConverters __chapterStatsConverters = new ChapterStatsConverters();

  private final EntityDeletionOrUpdateAdapter<ChapterStats> __deletionAdapterOfChapterStats;

  private final SharedSQLiteStatement __preparedStmtOfIncrementVisitCount;

  private final SharedSQLiteStatement __preparedStmtOfIncrementNoteCount;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ChapterStatsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChapterStats = new EntityInsertionAdapter<ChapterStats>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `chapter_stats` (`id`,`chapterId`,`userId`,`visitCount`,`noteCount`,`questGenerated`,`revisionHistory`,`chatHistory`,`lastVisited`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChapterStats entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getChapterId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getChapterId());
        }
        if (entity.getUserId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getUserId());
        }
        statement.bindLong(4, entity.getVisitCount());
        statement.bindLong(5, entity.getNoteCount());
        final int _tmp = entity.getQuestGenerated() ? 1 : 0;
        statement.bindLong(6, _tmp);
        final String _tmp_1 = __chapterStatsConverters.fromRevisionEntryList(entity.getRevisionHistory());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_1);
        }
        final String _tmp_2 = __chapterStatsConverters.fromChatEntryList(entity.getChatHistory());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_2);
        }
        statement.bindLong(9, entity.getLastVisited());
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfChapterStats = new EntityDeletionOrUpdateAdapter<ChapterStats>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `chapter_stats` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChapterStats entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfIncrementVisitCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE chapter_stats SET visitCount = visitCount + 1, lastVisited = ?, updatedAt = ? WHERE chapterId = ? AND userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementNoteCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE chapter_stats SET noteCount = noteCount + 1, updatedAt = ? WHERE chapterId = ? AND userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM chapter_stats WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM chapter_stats";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrUpdateChapterStats(final ChapterStats chapterStats,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChapterStats.insert(chapterStats);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object insertOrUpdate(final ChapterStats chapterStats,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChapterStats.insert(chapterStats);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteChapterStats(final ChapterStats chapterStats,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfChapterStats.handle(chapterStats);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object incrementVisitCount(final String chapterId, final String userId,
      final long timestamp, final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementVisitCount.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        if (chapterId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, chapterId);
        }
        _argIndex = 4;
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
          __preparedStmtOfIncrementVisitCount.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Object incrementNoteCount(final String chapterId, final String userId,
      final long timestamp, final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementNoteCount.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        if (chapterId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, chapterId);
        }
        _argIndex = 3;
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
          __preparedStmtOfIncrementNoteCount.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> arg0) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
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
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, arg0);
  }

  @Override
  public Object getAllStats(final Continuation<? super List<ChapterStats>> arg0) {
    final String _sql = "SELECT * FROM chapter_stats ORDER BY lastVisited DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ChapterStats>>() {
      @Override
      @NonNull
      public List<ChapterStats> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfNoteCount = CursorUtil.getColumnIndexOrThrow(_cursor, "noteCount");
          final int _cursorIndexOfQuestGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "questGenerated");
          final int _cursorIndexOfRevisionHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "revisionHistory");
          final int _cursorIndexOfChatHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "chatHistory");
          final int _cursorIndexOfLastVisited = CursorUtil.getColumnIndexOrThrow(_cursor, "lastVisited");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ChapterStats> _result = new ArrayList<ChapterStats>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChapterStats _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final int _tmpNoteCount;
            _tmpNoteCount = _cursor.getInt(_cursorIndexOfNoteCount);
            final boolean _tmpQuestGenerated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQuestGenerated);
            _tmpQuestGenerated = _tmp != 0;
            final List<RevisionEntry> _tmpRevisionHistory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRevisionHistory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRevisionHistory);
            }
            _tmpRevisionHistory = __chapterStatsConverters.toRevisionEntryList(_tmp_1);
            final List<ChatEntry> _tmpChatHistory;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfChatHistory)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfChatHistory);
            }
            _tmpChatHistory = __chapterStatsConverters.toChatEntryList(_tmp_2);
            final long _tmpLastVisited;
            _tmpLastVisited = _cursor.getLong(_cursorIndexOfLastVisited);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ChapterStats(_tmpId,_tmpChapterId,_tmpUserId,_tmpVisitCount,_tmpNoteCount,_tmpQuestGenerated,_tmpRevisionHistory,_tmpChatHistory,_tmpLastVisited,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Object getChapterStatsForUser(final String userId,
      final Continuation<? super List<ChapterStats>> arg1) {
    final String _sql = "SELECT * FROM chapter_stats WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ChapterStats>>() {
      @Override
      @NonNull
      public List<ChapterStats> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfNoteCount = CursorUtil.getColumnIndexOrThrow(_cursor, "noteCount");
          final int _cursorIndexOfQuestGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "questGenerated");
          final int _cursorIndexOfRevisionHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "revisionHistory");
          final int _cursorIndexOfChatHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "chatHistory");
          final int _cursorIndexOfLastVisited = CursorUtil.getColumnIndexOrThrow(_cursor, "lastVisited");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ChapterStats> _result = new ArrayList<ChapterStats>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChapterStats _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final int _tmpNoteCount;
            _tmpNoteCount = _cursor.getInt(_cursorIndexOfNoteCount);
            final boolean _tmpQuestGenerated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQuestGenerated);
            _tmpQuestGenerated = _tmp != 0;
            final List<RevisionEntry> _tmpRevisionHistory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRevisionHistory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRevisionHistory);
            }
            _tmpRevisionHistory = __chapterStatsConverters.toRevisionEntryList(_tmp_1);
            final List<ChatEntry> _tmpChatHistory;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfChatHistory)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfChatHistory);
            }
            _tmpChatHistory = __chapterStatsConverters.toChatEntryList(_tmp_2);
            final long _tmpLastVisited;
            _tmpLastVisited = _cursor.getLong(_cursorIndexOfLastVisited);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ChapterStats(_tmpId,_tmpChapterId,_tmpUserId,_tmpVisitCount,_tmpNoteCount,_tmpQuestGenerated,_tmpRevisionHistory,_tmpChatHistory,_tmpLastVisited,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
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
  public Object getChapterStats(final String chapterId, final String userId,
      final Continuation<? super ChapterStats> arg2) {
    final String _sql = "SELECT * FROM chapter_stats WHERE chapterId = ? AND userId = ?";
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
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ChapterStats>() {
      @Override
      @Nullable
      public ChapterStats call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfNoteCount = CursorUtil.getColumnIndexOrThrow(_cursor, "noteCount");
          final int _cursorIndexOfQuestGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "questGenerated");
          final int _cursorIndexOfRevisionHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "revisionHistory");
          final int _cursorIndexOfChatHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "chatHistory");
          final int _cursorIndexOfLastVisited = CursorUtil.getColumnIndexOrThrow(_cursor, "lastVisited");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final ChapterStats _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final int _tmpNoteCount;
            _tmpNoteCount = _cursor.getInt(_cursorIndexOfNoteCount);
            final boolean _tmpQuestGenerated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQuestGenerated);
            _tmpQuestGenerated = _tmp != 0;
            final List<RevisionEntry> _tmpRevisionHistory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRevisionHistory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRevisionHistory);
            }
            _tmpRevisionHistory = __chapterStatsConverters.toRevisionEntryList(_tmp_1);
            final List<ChatEntry> _tmpChatHistory;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfChatHistory)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfChatHistory);
            }
            _tmpChatHistory = __chapterStatsConverters.toChatEntryList(_tmp_2);
            final long _tmpLastVisited;
            _tmpLastVisited = _cursor.getLong(_cursorIndexOfLastVisited);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ChapterStats(_tmpId,_tmpChapterId,_tmpUserId,_tmpVisitCount,_tmpNoteCount,_tmpQuestGenerated,_tmpRevisionHistory,_tmpChatHistory,_tmpLastVisited,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
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
  public Object getChapterStatsByChapter(final String chapterId,
      final Continuation<? super ChapterStats> arg1) {
    final String _sql = "SELECT * FROM chapter_stats WHERE chapterId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ChapterStats>() {
      @Override
      @Nullable
      public ChapterStats call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfNoteCount = CursorUtil.getColumnIndexOrThrow(_cursor, "noteCount");
          final int _cursorIndexOfQuestGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "questGenerated");
          final int _cursorIndexOfRevisionHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "revisionHistory");
          final int _cursorIndexOfChatHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "chatHistory");
          final int _cursorIndexOfLastVisited = CursorUtil.getColumnIndexOrThrow(_cursor, "lastVisited");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final ChapterStats _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final int _tmpNoteCount;
            _tmpNoteCount = _cursor.getInt(_cursorIndexOfNoteCount);
            final boolean _tmpQuestGenerated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQuestGenerated);
            _tmpQuestGenerated = _tmp != 0;
            final List<RevisionEntry> _tmpRevisionHistory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRevisionHistory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRevisionHistory);
            }
            _tmpRevisionHistory = __chapterStatsConverters.toRevisionEntryList(_tmp_1);
            final List<ChatEntry> _tmpChatHistory;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfChatHistory)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfChatHistory);
            }
            _tmpChatHistory = __chapterStatsConverters.toChatEntryList(_tmp_2);
            final long _tmpLastVisited;
            _tmpLastVisited = _cursor.getLong(_cursorIndexOfLastVisited);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ChapterStats(_tmpId,_tmpChapterId,_tmpUserId,_tmpVisitCount,_tmpNoteCount,_tmpQuestGenerated,_tmpRevisionHistory,_tmpChatHistory,_tmpLastVisited,_tmpCreatedAt,_tmpUpdatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
