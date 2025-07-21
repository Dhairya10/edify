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
import com.edify.learning.data.model.UserResponse;
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
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserResponseDao_Impl implements UserResponseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserResponse> __insertionAdapterOfUserResponse;

  private final EntityDeletionOrUpdateAdapter<UserResponse> __deletionAdapterOfUserResponse;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllResponsesForChapter;

  public UserResponseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserResponse = new EntityInsertionAdapter<UserResponse>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_responses` (`id`,`chapterId`,`exerciseIndex`,`textResponse`,`imageUri`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserResponse entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getChapterId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getChapterId());
        }
        statement.bindLong(3, entity.getExerciseIndex());
        if (entity.getTextResponse() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTextResponse());
        }
        if (entity.getImageUri() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getImageUri());
        }
        statement.bindLong(6, entity.getCreatedAt());
        statement.bindLong(7, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfUserResponse = new EntityDeletionOrUpdateAdapter<UserResponse>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `user_responses` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserResponse entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllResponsesForChapter = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM user_responses WHERE chapterId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrUpdateResponse(final UserResponse response,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserResponse.insert(response);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteResponse(final UserResponse response,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfUserResponse.handle(response);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllResponsesForChapter(final String chapterId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllResponsesForChapter.acquire();
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
          __preparedStmtOfDeleteAllResponsesForChapter.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<UserResponse>> getResponsesForChapter(final String chapterId) {
    final String _sql = "SELECT * FROM user_responses WHERE chapterId = ? ORDER BY exerciseIndex ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_responses"}, new Callable<List<UserResponse>>() {
      @Override
      @NonNull
      public List<UserResponse> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfExerciseIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseIndex");
          final int _cursorIndexOfTextResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "textResponse");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<UserResponse> _result = new ArrayList<UserResponse>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserResponse _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final int _tmpExerciseIndex;
            _tmpExerciseIndex = _cursor.getInt(_cursorIndexOfExerciseIndex);
            final String _tmpTextResponse;
            if (_cursor.isNull(_cursorIndexOfTextResponse)) {
              _tmpTextResponse = null;
            } else {
              _tmpTextResponse = _cursor.getString(_cursorIndexOfTextResponse);
            }
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new UserResponse(_tmpId,_tmpChapterId,_tmpExerciseIndex,_tmpTextResponse,_tmpImageUri,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getResponse(final String chapterId, final int exerciseIndex,
      final Continuation<? super UserResponse> $completion) {
    final String _sql = "SELECT * FROM user_responses WHERE chapterId = ? AND exerciseIndex = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, exerciseIndex);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserResponse>() {
      @Override
      @Nullable
      public UserResponse call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfExerciseIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseIndex");
          final int _cursorIndexOfTextResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "textResponse");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final UserResponse _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final int _tmpExerciseIndex;
            _tmpExerciseIndex = _cursor.getInt(_cursorIndexOfExerciseIndex);
            final String _tmpTextResponse;
            if (_cursor.isNull(_cursorIndexOfTextResponse)) {
              _tmpTextResponse = null;
            } else {
              _tmpTextResponse = _cursor.getString(_cursorIndexOfTextResponse);
            }
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new UserResponse(_tmpId,_tmpChapterId,_tmpExerciseIndex,_tmpTextResponse,_tmpImageUri,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getResponseCountForChapter(final String chapterId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM user_responses WHERE chapterId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
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
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
