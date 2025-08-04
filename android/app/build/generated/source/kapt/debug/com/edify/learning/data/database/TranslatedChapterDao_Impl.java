package com.edify.learning.data.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.edify.learning.data.model.TranslatedChapter;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TranslatedChapterDao_Impl implements TranslatedChapterDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TranslatedChapter> __insertionAdapterOfTranslatedChapter;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTranslatedContent;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTranslatedChapter;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTranslationsForChapter;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTranslations;

  public TranslatedChapterDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTranslatedChapter = new EntityInsertionAdapter<TranslatedChapter>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `translated_chapters` (`id`,`chapterId`,`language`,`originalContentHash`,`translatedContent`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TranslatedChapter entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getChapterId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getChapterId());
        }
        if (entity.getLanguage() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLanguage());
        }
        if (entity.getOriginalContentHash() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getOriginalContentHash());
        }
        if (entity.getTranslatedContent() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTranslatedContent());
        }
        statement.bindLong(6, entity.getCreatedAt());
        statement.bindLong(7, entity.getUpdatedAt());
      }
    };
    this.__preparedStmtOfUpdateTranslatedContent = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE translated_chapters SET translatedContent = ?, updatedAt = ? WHERE chapterId = ? AND language = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteTranslatedChapter = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM translated_chapters WHERE chapterId = ? AND language = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllTranslationsForChapter = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM translated_chapters WHERE chapterId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllTranslations = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM translated_chapters";
        return _query;
      }
    };
  }

  @Override
  public Object insertTranslatedChapter(final TranslatedChapter translatedChapter,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTranslatedChapter.insert(translatedChapter);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTranslatedContent(final String chapterId, final String language,
      final String translatedContent, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTranslatedContent.acquire();
        int _argIndex = 1;
        if (translatedContent == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, translatedContent);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        if (chapterId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, chapterId);
        }
        _argIndex = 4;
        if (language == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, language);
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
          __preparedStmtOfUpdateTranslatedContent.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTranslatedChapter(final String chapterId, final String language,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTranslatedChapter.acquire();
        int _argIndex = 1;
        if (chapterId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, chapterId);
        }
        _argIndex = 2;
        if (language == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, language);
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
          __preparedStmtOfDeleteTranslatedChapter.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllTranslationsForChapter(final String chapterId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTranslationsForChapter.acquire();
        int _argIndex = 1;
        if (chapterId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, chapterId);
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
          __preparedStmtOfDeleteAllTranslationsForChapter.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllTranslations(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTranslations.acquire();
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
          __preparedStmtOfDeleteAllTranslations.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getTranslatedChapter(final String chapterId, final String language,
      final Continuation<? super TranslatedChapter> $completion) {
    final String _sql = "SELECT * FROM translated_chapters WHERE chapterId = ? AND language = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    _argIndex = 2;
    if (language == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, language);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TranslatedChapter>() {
      @Override
      @Nullable
      public TranslatedChapter call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfOriginalContentHash = CursorUtil.getColumnIndexOrThrow(_cursor, "originalContentHash");
          final int _cursorIndexOfTranslatedContent = CursorUtil.getColumnIndexOrThrow(_cursor, "translatedContent");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final TranslatedChapter _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final String _tmpOriginalContentHash;
            if (_cursor.isNull(_cursorIndexOfOriginalContentHash)) {
              _tmpOriginalContentHash = null;
            } else {
              _tmpOriginalContentHash = _cursor.getString(_cursorIndexOfOriginalContentHash);
            }
            final String _tmpTranslatedContent;
            if (_cursor.isNull(_cursorIndexOfTranslatedContent)) {
              _tmpTranslatedContent = null;
            } else {
              _tmpTranslatedContent = _cursor.getString(_cursorIndexOfTranslatedContent);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new TranslatedChapter(_tmpId,_tmpChapterId,_tmpLanguage,_tmpOriginalContentHash,_tmpTranslatedContent,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TranslatedChapter>> getAllTranslatedChapters() {
    final String _sql = "SELECT * FROM translated_chapters";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"translated_chapters"}, new Callable<List<TranslatedChapter>>() {
      @Override
      @NonNull
      public List<TranslatedChapter> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfOriginalContentHash = CursorUtil.getColumnIndexOrThrow(_cursor, "originalContentHash");
          final int _cursorIndexOfTranslatedContent = CursorUtil.getColumnIndexOrThrow(_cursor, "translatedContent");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<TranslatedChapter> _result = new ArrayList<TranslatedChapter>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TranslatedChapter _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final String _tmpOriginalContentHash;
            if (_cursor.isNull(_cursorIndexOfOriginalContentHash)) {
              _tmpOriginalContentHash = null;
            } else {
              _tmpOriginalContentHash = _cursor.getString(_cursorIndexOfOriginalContentHash);
            }
            final String _tmpTranslatedContent;
            if (_cursor.isNull(_cursorIndexOfTranslatedContent)) {
              _tmpTranslatedContent = null;
            } else {
              _tmpTranslatedContent = _cursor.getString(_cursorIndexOfTranslatedContent);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TranslatedChapter(_tmpId,_tmpChapterId,_tmpLanguage,_tmpOriginalContentHash,_tmpTranslatedContent,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getTranslationCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM translated_chapters";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
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
    }, $completion);
  }

  @Override
  public Object getTranslationsForChapter(final String chapterId,
      final Continuation<? super List<TranslatedChapter>> $completion) {
    final String _sql = "SELECT * FROM translated_chapters WHERE chapterId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TranslatedChapter>>() {
      @Override
      @NonNull
      public List<TranslatedChapter> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfOriginalContentHash = CursorUtil.getColumnIndexOrThrow(_cursor, "originalContentHash");
          final int _cursorIndexOfTranslatedContent = CursorUtil.getColumnIndexOrThrow(_cursor, "translatedContent");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<TranslatedChapter> _result = new ArrayList<TranslatedChapter>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TranslatedChapter _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final String _tmpOriginalContentHash;
            if (_cursor.isNull(_cursorIndexOfOriginalContentHash)) {
              _tmpOriginalContentHash = null;
            } else {
              _tmpOriginalContentHash = _cursor.getString(_cursorIndexOfOriginalContentHash);
            }
            final String _tmpTranslatedContent;
            if (_cursor.isNull(_cursorIndexOfTranslatedContent)) {
              _tmpTranslatedContent = null;
            } else {
              _tmpTranslatedContent = _cursor.getString(_cursorIndexOfTranslatedContent);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TranslatedChapter(_tmpId,_tmpChapterId,_tmpLanguage,_tmpOriginalContentHash,_tmpTranslatedContent,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
