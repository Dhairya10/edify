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
import com.edify.learning.data.model.RevisionResponse;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
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
public final class RevisionResponseDao_Impl implements RevisionResponseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RevisionResponse> __insertionAdapterOfRevisionResponse;

  private final EntityDeletionOrUpdateAdapter<RevisionResponse> __deletionAdapterOfRevisionResponse;

  private final EntityDeletionOrUpdateAdapter<RevisionResponse> __updateAdapterOfRevisionResponse;

  private final SharedSQLiteStatement __preparedStmtOfDeleteRevisionResponsesForChapter;

  public RevisionResponseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRevisionResponse = new EntityInsertionAdapter<RevisionResponse>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `revision_responses` (`id`,`chapterId`,`questionIndex`,`question`,`userAnswer`,`imageUri`,`gemmaFeedback`,`feedbackCategory`,`isSubmitted`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RevisionResponse entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getChapterId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getChapterId());
        }
        statement.bindLong(3, entity.getQuestionIndex());
        if (entity.getQuestion() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getQuestion());
        }
        if (entity.getUserAnswer() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getUserAnswer());
        }
        if (entity.getImageUri() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getImageUri());
        }
        if (entity.getGemmaFeedback() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getGemmaFeedback());
        }
        if (entity.getFeedbackCategory() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getFeedbackCategory());
        }
        final int _tmp = entity.isSubmitted() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfRevisionResponse = new EntityDeletionOrUpdateAdapter<RevisionResponse>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `revision_responses` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RevisionResponse entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfRevisionResponse = new EntityDeletionOrUpdateAdapter<RevisionResponse>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `revision_responses` SET `id` = ?,`chapterId` = ?,`questionIndex` = ?,`question` = ?,`userAnswer` = ?,`imageUri` = ?,`gemmaFeedback` = ?,`feedbackCategory` = ?,`isSubmitted` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RevisionResponse entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getChapterId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getChapterId());
        }
        statement.bindLong(3, entity.getQuestionIndex());
        if (entity.getQuestion() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getQuestion());
        }
        if (entity.getUserAnswer() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getUserAnswer());
        }
        if (entity.getImageUri() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getImageUri());
        }
        if (entity.getGemmaFeedback() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getGemmaFeedback());
        }
        if (entity.getFeedbackCategory() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getFeedbackCategory());
        }
        final int _tmp = entity.isSubmitted() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteRevisionResponsesForChapter = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM revision_responses WHERE chapterId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertRevisionResponse(final RevisionResponse response,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRevisionResponse.insertAndReturnId(response);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRevisionResponse(final RevisionResponse response,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRevisionResponse.handle(response);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRevisionResponse(final RevisionResponse response,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRevisionResponse.handle(response);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRevisionResponsesForChapter(final String chapterId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteRevisionResponsesForChapter.acquire();
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
          __preparedStmtOfDeleteRevisionResponsesForChapter.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RevisionResponse>> getRevisionResponsesForChapter(final String chapterId) {
    final String _sql = "SELECT * FROM revision_responses WHERE chapterId = ? ORDER BY questionIndex ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"revision_responses"}, new Callable<List<RevisionResponse>>() {
      @Override
      @NonNull
      public List<RevisionResponse> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfQuestionIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIndex");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfUserAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "userAnswer");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfGemmaFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "gemmaFeedback");
          final int _cursorIndexOfFeedbackCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "feedbackCategory");
          final int _cursorIndexOfIsSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "isSubmitted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<RevisionResponse> _result = new ArrayList<RevisionResponse>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RevisionResponse _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final int _tmpQuestionIndex;
            _tmpQuestionIndex = _cursor.getInt(_cursorIndexOfQuestionIndex);
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpUserAnswer;
            if (_cursor.isNull(_cursorIndexOfUserAnswer)) {
              _tmpUserAnswer = null;
            } else {
              _tmpUserAnswer = _cursor.getString(_cursorIndexOfUserAnswer);
            }
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final String _tmpGemmaFeedback;
            if (_cursor.isNull(_cursorIndexOfGemmaFeedback)) {
              _tmpGemmaFeedback = null;
            } else {
              _tmpGemmaFeedback = _cursor.getString(_cursorIndexOfGemmaFeedback);
            }
            final String _tmpFeedbackCategory;
            if (_cursor.isNull(_cursorIndexOfFeedbackCategory)) {
              _tmpFeedbackCategory = null;
            } else {
              _tmpFeedbackCategory = _cursor.getString(_cursorIndexOfFeedbackCategory);
            }
            final boolean _tmpIsSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSubmitted);
            _tmpIsSubmitted = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new RevisionResponse(_tmpId,_tmpChapterId,_tmpQuestionIndex,_tmpQuestion,_tmpUserAnswer,_tmpImageUri,_tmpGemmaFeedback,_tmpFeedbackCategory,_tmpIsSubmitted,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getRevisionResponse(final String chapterId, final int questionIndex,
      final Continuation<? super RevisionResponse> $completion) {
    final String _sql = "SELECT * FROM revision_responses WHERE chapterId = ? AND questionIndex = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, questionIndex);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RevisionResponse>() {
      @Override
      @Nullable
      public RevisionResponse call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfQuestionIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIndex");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfUserAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "userAnswer");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfGemmaFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "gemmaFeedback");
          final int _cursorIndexOfFeedbackCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "feedbackCategory");
          final int _cursorIndexOfIsSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "isSubmitted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final RevisionResponse _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final int _tmpQuestionIndex;
            _tmpQuestionIndex = _cursor.getInt(_cursorIndexOfQuestionIndex);
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpUserAnswer;
            if (_cursor.isNull(_cursorIndexOfUserAnswer)) {
              _tmpUserAnswer = null;
            } else {
              _tmpUserAnswer = _cursor.getString(_cursorIndexOfUserAnswer);
            }
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final String _tmpGemmaFeedback;
            if (_cursor.isNull(_cursorIndexOfGemmaFeedback)) {
              _tmpGemmaFeedback = null;
            } else {
              _tmpGemmaFeedback = _cursor.getString(_cursorIndexOfGemmaFeedback);
            }
            final String _tmpFeedbackCategory;
            if (_cursor.isNull(_cursorIndexOfFeedbackCategory)) {
              _tmpFeedbackCategory = null;
            } else {
              _tmpFeedbackCategory = _cursor.getString(_cursorIndexOfFeedbackCategory);
            }
            final boolean _tmpIsSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSubmitted);
            _tmpIsSubmitted = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new RevisionResponse(_tmpId,_tmpChapterId,_tmpQuestionIndex,_tmpQuestion,_tmpUserAnswer,_tmpImageUri,_tmpGemmaFeedback,_tmpFeedbackCategory,_tmpIsSubmitted,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getSubmittedResponsesCount(final String chapterId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM revision_responses WHERE chapterId = ? AND isSubmitted = 1";
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

  @Override
  public Object getTotalResponsesCount(final String chapterId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM revision_responses WHERE chapterId = ?";
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

  @Override
  public Flow<List<RevisionResponse>> getAllSubmittedResponses() {
    final String _sql = "SELECT * FROM revision_responses WHERE isSubmitted = 1 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"revision_responses"}, new Callable<List<RevisionResponse>>() {
      @Override
      @NonNull
      public List<RevisionResponse> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfQuestionIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIndex");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfUserAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "userAnswer");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfGemmaFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "gemmaFeedback");
          final int _cursorIndexOfFeedbackCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "feedbackCategory");
          final int _cursorIndexOfIsSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "isSubmitted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<RevisionResponse> _result = new ArrayList<RevisionResponse>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RevisionResponse _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpChapterId;
            if (_cursor.isNull(_cursorIndexOfChapterId)) {
              _tmpChapterId = null;
            } else {
              _tmpChapterId = _cursor.getString(_cursorIndexOfChapterId);
            }
            final int _tmpQuestionIndex;
            _tmpQuestionIndex = _cursor.getInt(_cursorIndexOfQuestionIndex);
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpUserAnswer;
            if (_cursor.isNull(_cursorIndexOfUserAnswer)) {
              _tmpUserAnswer = null;
            } else {
              _tmpUserAnswer = _cursor.getString(_cursorIndexOfUserAnswer);
            }
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final String _tmpGemmaFeedback;
            if (_cursor.isNull(_cursorIndexOfGemmaFeedback)) {
              _tmpGemmaFeedback = null;
            } else {
              _tmpGemmaFeedback = _cursor.getString(_cursorIndexOfGemmaFeedback);
            }
            final String _tmpFeedbackCategory;
            if (_cursor.isNull(_cursorIndexOfFeedbackCategory)) {
              _tmpFeedbackCategory = null;
            } else {
              _tmpFeedbackCategory = _cursor.getString(_cursorIndexOfFeedbackCategory);
            }
            final boolean _tmpIsSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSubmitted);
            _tmpIsSubmitted = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new RevisionResponse(_tmpId,_tmpChapterId,_tmpQuestionIndex,_tmpQuestion,_tmpUserAnswer,_tmpImageUri,_tmpGemmaFeedback,_tmpFeedbackCategory,_tmpIsSubmitted,_tmpCreatedAt,_tmpUpdatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
