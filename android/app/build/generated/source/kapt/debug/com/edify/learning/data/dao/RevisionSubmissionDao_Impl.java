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
import com.edify.learning.data.model.RevisionSubmission;
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
public final class RevisionSubmissionDao_Impl implements RevisionSubmissionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RevisionSubmission> __insertionAdapterOfRevisionSubmission;

  private final EntityDeletionOrUpdateAdapter<RevisionSubmission> __deletionAdapterOfRevisionSubmission;

  private final EntityDeletionOrUpdateAdapter<RevisionSubmission> __updateAdapterOfRevisionSubmission;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSubmissionEvaluation;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSubmissionsForChapter;

  public RevisionSubmissionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRevisionSubmission = new EntityInsertionAdapter<RevisionSubmission>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `revision_submissions` (`id`,`chapterId`,`questionIndex`,`question`,`expectedAnswer`,`userTextResponse`,`userImageUri`,`gemmaFeedback`,`isEvaluated`,`submittedAt`,`evaluatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RevisionSubmission entity) {
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
        if (entity.getExpectedAnswer() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getExpectedAnswer());
        }
        if (entity.getUserTextResponse() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getUserTextResponse());
        }
        if (entity.getUserImageUri() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getUserImageUri());
        }
        if (entity.getGemmaFeedback() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getGemmaFeedback());
        }
        final int _tmp = entity.isEvaluated() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getSubmittedAt());
        if (entity.getEvaluatedAt() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getEvaluatedAt());
        }
      }
    };
    this.__deletionAdapterOfRevisionSubmission = new EntityDeletionOrUpdateAdapter<RevisionSubmission>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `revision_submissions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RevisionSubmission entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfRevisionSubmission = new EntityDeletionOrUpdateAdapter<RevisionSubmission>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `revision_submissions` SET `id` = ?,`chapterId` = ?,`questionIndex` = ?,`question` = ?,`expectedAnswer` = ?,`userTextResponse` = ?,`userImageUri` = ?,`gemmaFeedback` = ?,`isEvaluated` = ?,`submittedAt` = ?,`evaluatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RevisionSubmission entity) {
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
        if (entity.getExpectedAnswer() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getExpectedAnswer());
        }
        if (entity.getUserTextResponse() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getUserTextResponse());
        }
        if (entity.getUserImageUri() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getUserImageUri());
        }
        if (entity.getGemmaFeedback() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getGemmaFeedback());
        }
        final int _tmp = entity.isEvaluated() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getSubmittedAt());
        if (entity.getEvaluatedAt() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getEvaluatedAt());
        }
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateSubmissionEvaluation = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE revision_submissions SET gemmaFeedback = ?, isEvaluated = ?, evaluatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteSubmissionsForChapter = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM revision_submissions WHERE chapterId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSubmission(final RevisionSubmission submission,
      final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRevisionSubmission.insertAndReturnId(submission);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteSubmission(final RevisionSubmission submission,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRevisionSubmission.handle(submission);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateSubmission(final RevisionSubmission submission,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRevisionSubmission.handle(submission);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateSubmissionEvaluation(final long submissionId, final String feedback,
      final boolean isEvaluated, final long evaluatedAt, final Continuation<? super Unit> arg4) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSubmissionEvaluation.acquire();
        int _argIndex = 1;
        if (feedback == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, feedback);
        }
        _argIndex = 2;
        final int _tmp = isEvaluated ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, evaluatedAt);
        _argIndex = 4;
        _stmt.bindLong(_argIndex, submissionId);
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
          __preparedStmtOfUpdateSubmissionEvaluation.release(_stmt);
        }
      }
    }, arg4);
  }

  @Override
  public Object deleteSubmissionsForChapter(final String chapterId,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSubmissionsForChapter.acquire();
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
          __preparedStmtOfDeleteSubmissionsForChapter.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<RevisionSubmission>> getSubmissionsForChapter(final String chapterId) {
    final String _sql = "SELECT * FROM revision_submissions WHERE chapterId = ? ORDER BY questionIndex ASC, submittedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"revision_submissions"}, new Callable<List<RevisionSubmission>>() {
      @Override
      @NonNull
      public List<RevisionSubmission> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfQuestionIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIndex");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfExpectedAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedAnswer");
          final int _cursorIndexOfUserTextResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "userTextResponse");
          final int _cursorIndexOfUserImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "userImageUri");
          final int _cursorIndexOfGemmaFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "gemmaFeedback");
          final int _cursorIndexOfIsEvaluated = CursorUtil.getColumnIndexOrThrow(_cursor, "isEvaluated");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfEvaluatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "evaluatedAt");
          final List<RevisionSubmission> _result = new ArrayList<RevisionSubmission>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RevisionSubmission _item;
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
            final String _tmpExpectedAnswer;
            if (_cursor.isNull(_cursorIndexOfExpectedAnswer)) {
              _tmpExpectedAnswer = null;
            } else {
              _tmpExpectedAnswer = _cursor.getString(_cursorIndexOfExpectedAnswer);
            }
            final String _tmpUserTextResponse;
            if (_cursor.isNull(_cursorIndexOfUserTextResponse)) {
              _tmpUserTextResponse = null;
            } else {
              _tmpUserTextResponse = _cursor.getString(_cursorIndexOfUserTextResponse);
            }
            final String _tmpUserImageUri;
            if (_cursor.isNull(_cursorIndexOfUserImageUri)) {
              _tmpUserImageUri = null;
            } else {
              _tmpUserImageUri = _cursor.getString(_cursorIndexOfUserImageUri);
            }
            final String _tmpGemmaFeedback;
            if (_cursor.isNull(_cursorIndexOfGemmaFeedback)) {
              _tmpGemmaFeedback = null;
            } else {
              _tmpGemmaFeedback = _cursor.getString(_cursorIndexOfGemmaFeedback);
            }
            final boolean _tmpIsEvaluated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEvaluated);
            _tmpIsEvaluated = _tmp != 0;
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final Long _tmpEvaluatedAt;
            if (_cursor.isNull(_cursorIndexOfEvaluatedAt)) {
              _tmpEvaluatedAt = null;
            } else {
              _tmpEvaluatedAt = _cursor.getLong(_cursorIndexOfEvaluatedAt);
            }
            _item = new RevisionSubmission(_tmpId,_tmpChapterId,_tmpQuestionIndex,_tmpQuestion,_tmpExpectedAnswer,_tmpUserTextResponse,_tmpUserImageUri,_tmpGemmaFeedback,_tmpIsEvaluated,_tmpSubmittedAt,_tmpEvaluatedAt);
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
  public Flow<List<RevisionSubmission>> getSubmissionsForQuestion(final String chapterId,
      final int questionIndex) {
    final String _sql = "SELECT * FROM revision_submissions WHERE chapterId = ? AND questionIndex = ? ORDER BY submittedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (chapterId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chapterId);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, questionIndex);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"revision_submissions"}, new Callable<List<RevisionSubmission>>() {
      @Override
      @NonNull
      public List<RevisionSubmission> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfQuestionIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIndex");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfExpectedAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedAnswer");
          final int _cursorIndexOfUserTextResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "userTextResponse");
          final int _cursorIndexOfUserImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "userImageUri");
          final int _cursorIndexOfGemmaFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "gemmaFeedback");
          final int _cursorIndexOfIsEvaluated = CursorUtil.getColumnIndexOrThrow(_cursor, "isEvaluated");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfEvaluatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "evaluatedAt");
          final List<RevisionSubmission> _result = new ArrayList<RevisionSubmission>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RevisionSubmission _item;
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
            final String _tmpExpectedAnswer;
            if (_cursor.isNull(_cursorIndexOfExpectedAnswer)) {
              _tmpExpectedAnswer = null;
            } else {
              _tmpExpectedAnswer = _cursor.getString(_cursorIndexOfExpectedAnswer);
            }
            final String _tmpUserTextResponse;
            if (_cursor.isNull(_cursorIndexOfUserTextResponse)) {
              _tmpUserTextResponse = null;
            } else {
              _tmpUserTextResponse = _cursor.getString(_cursorIndexOfUserTextResponse);
            }
            final String _tmpUserImageUri;
            if (_cursor.isNull(_cursorIndexOfUserImageUri)) {
              _tmpUserImageUri = null;
            } else {
              _tmpUserImageUri = _cursor.getString(_cursorIndexOfUserImageUri);
            }
            final String _tmpGemmaFeedback;
            if (_cursor.isNull(_cursorIndexOfGemmaFeedback)) {
              _tmpGemmaFeedback = null;
            } else {
              _tmpGemmaFeedback = _cursor.getString(_cursorIndexOfGemmaFeedback);
            }
            final boolean _tmpIsEvaluated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEvaluated);
            _tmpIsEvaluated = _tmp != 0;
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final Long _tmpEvaluatedAt;
            if (_cursor.isNull(_cursorIndexOfEvaluatedAt)) {
              _tmpEvaluatedAt = null;
            } else {
              _tmpEvaluatedAt = _cursor.getLong(_cursorIndexOfEvaluatedAt);
            }
            _item = new RevisionSubmission(_tmpId,_tmpChapterId,_tmpQuestionIndex,_tmpQuestion,_tmpExpectedAnswer,_tmpUserTextResponse,_tmpUserImageUri,_tmpGemmaFeedback,_tmpIsEvaluated,_tmpSubmittedAt,_tmpEvaluatedAt);
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
  public Object getLatestSubmissionForQuestion(final String chapterId, final int questionIndex,
      final Continuation<? super RevisionSubmission> arg2) {
    final String _sql = "SELECT * FROM revision_submissions WHERE chapterId = ? AND questionIndex = ? ORDER BY submittedAt DESC LIMIT 1";
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
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RevisionSubmission>() {
      @Override
      @Nullable
      public RevisionSubmission call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfQuestionIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIndex");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfExpectedAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedAnswer");
          final int _cursorIndexOfUserTextResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "userTextResponse");
          final int _cursorIndexOfUserImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "userImageUri");
          final int _cursorIndexOfGemmaFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "gemmaFeedback");
          final int _cursorIndexOfIsEvaluated = CursorUtil.getColumnIndexOrThrow(_cursor, "isEvaluated");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfEvaluatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "evaluatedAt");
          final RevisionSubmission _result;
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
            final String _tmpExpectedAnswer;
            if (_cursor.isNull(_cursorIndexOfExpectedAnswer)) {
              _tmpExpectedAnswer = null;
            } else {
              _tmpExpectedAnswer = _cursor.getString(_cursorIndexOfExpectedAnswer);
            }
            final String _tmpUserTextResponse;
            if (_cursor.isNull(_cursorIndexOfUserTextResponse)) {
              _tmpUserTextResponse = null;
            } else {
              _tmpUserTextResponse = _cursor.getString(_cursorIndexOfUserTextResponse);
            }
            final String _tmpUserImageUri;
            if (_cursor.isNull(_cursorIndexOfUserImageUri)) {
              _tmpUserImageUri = null;
            } else {
              _tmpUserImageUri = _cursor.getString(_cursorIndexOfUserImageUri);
            }
            final String _tmpGemmaFeedback;
            if (_cursor.isNull(_cursorIndexOfGemmaFeedback)) {
              _tmpGemmaFeedback = null;
            } else {
              _tmpGemmaFeedback = _cursor.getString(_cursorIndexOfGemmaFeedback);
            }
            final boolean _tmpIsEvaluated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEvaluated);
            _tmpIsEvaluated = _tmp != 0;
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final Long _tmpEvaluatedAt;
            if (_cursor.isNull(_cursorIndexOfEvaluatedAt)) {
              _tmpEvaluatedAt = null;
            } else {
              _tmpEvaluatedAt = _cursor.getLong(_cursorIndexOfEvaluatedAt);
            }
            _result = new RevisionSubmission(_tmpId,_tmpChapterId,_tmpQuestionIndex,_tmpQuestion,_tmpExpectedAnswer,_tmpUserTextResponse,_tmpUserImageUri,_tmpGemmaFeedback,_tmpIsEvaluated,_tmpSubmittedAt,_tmpEvaluatedAt);
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
  public Object getSubmissionById(final long submissionId,
      final Continuation<? super RevisionSubmission> arg1) {
    final String _sql = "SELECT * FROM revision_submissions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, submissionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RevisionSubmission>() {
      @Override
      @Nullable
      public RevisionSubmission call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfQuestionIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIndex");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfExpectedAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedAnswer");
          final int _cursorIndexOfUserTextResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "userTextResponse");
          final int _cursorIndexOfUserImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "userImageUri");
          final int _cursorIndexOfGemmaFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "gemmaFeedback");
          final int _cursorIndexOfIsEvaluated = CursorUtil.getColumnIndexOrThrow(_cursor, "isEvaluated");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfEvaluatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "evaluatedAt");
          final RevisionSubmission _result;
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
            final String _tmpExpectedAnswer;
            if (_cursor.isNull(_cursorIndexOfExpectedAnswer)) {
              _tmpExpectedAnswer = null;
            } else {
              _tmpExpectedAnswer = _cursor.getString(_cursorIndexOfExpectedAnswer);
            }
            final String _tmpUserTextResponse;
            if (_cursor.isNull(_cursorIndexOfUserTextResponse)) {
              _tmpUserTextResponse = null;
            } else {
              _tmpUserTextResponse = _cursor.getString(_cursorIndexOfUserTextResponse);
            }
            final String _tmpUserImageUri;
            if (_cursor.isNull(_cursorIndexOfUserImageUri)) {
              _tmpUserImageUri = null;
            } else {
              _tmpUserImageUri = _cursor.getString(_cursorIndexOfUserImageUri);
            }
            final String _tmpGemmaFeedback;
            if (_cursor.isNull(_cursorIndexOfGemmaFeedback)) {
              _tmpGemmaFeedback = null;
            } else {
              _tmpGemmaFeedback = _cursor.getString(_cursorIndexOfGemmaFeedback);
            }
            final boolean _tmpIsEvaluated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEvaluated);
            _tmpIsEvaluated = _tmp != 0;
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final Long _tmpEvaluatedAt;
            if (_cursor.isNull(_cursorIndexOfEvaluatedAt)) {
              _tmpEvaluatedAt = null;
            } else {
              _tmpEvaluatedAt = _cursor.getLong(_cursorIndexOfEvaluatedAt);
            }
            _result = new RevisionSubmission(_tmpId,_tmpChapterId,_tmpQuestionIndex,_tmpQuestion,_tmpExpectedAnswer,_tmpUserTextResponse,_tmpUserImageUri,_tmpGemmaFeedback,_tmpIsEvaluated,_tmpSubmittedAt,_tmpEvaluatedAt);
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
  public Object getEvaluatedSubmissionsCount(final String chapterId,
      final Continuation<? super Integer> arg1) {
    final String _sql = "SELECT COUNT(*) FROM revision_submissions WHERE chapterId = ? AND isEvaluated = 1";
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
    }, arg1);
  }

  @Override
  public Object getUniqueQuestionsAnswered(final String chapterId,
      final Continuation<? super Integer> arg1) {
    final String _sql = "SELECT COUNT(DISTINCT questionIndex) FROM revision_submissions WHERE chapterId = ?";
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
    }, arg1);
  }

  @Override
  public Object getPendingEvaluations(final Continuation<? super List<RevisionSubmission>> arg0) {
    final String _sql = "SELECT * FROM revision_submissions WHERE isEvaluated = 0 ORDER BY submittedAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RevisionSubmission>>() {
      @Override
      @NonNull
      public List<RevisionSubmission> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChapterId = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterId");
          final int _cursorIndexOfQuestionIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIndex");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfExpectedAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedAnswer");
          final int _cursorIndexOfUserTextResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "userTextResponse");
          final int _cursorIndexOfUserImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "userImageUri");
          final int _cursorIndexOfGemmaFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "gemmaFeedback");
          final int _cursorIndexOfIsEvaluated = CursorUtil.getColumnIndexOrThrow(_cursor, "isEvaluated");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfEvaluatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "evaluatedAt");
          final List<RevisionSubmission> _result = new ArrayList<RevisionSubmission>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RevisionSubmission _item;
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
            final String _tmpExpectedAnswer;
            if (_cursor.isNull(_cursorIndexOfExpectedAnswer)) {
              _tmpExpectedAnswer = null;
            } else {
              _tmpExpectedAnswer = _cursor.getString(_cursorIndexOfExpectedAnswer);
            }
            final String _tmpUserTextResponse;
            if (_cursor.isNull(_cursorIndexOfUserTextResponse)) {
              _tmpUserTextResponse = null;
            } else {
              _tmpUserTextResponse = _cursor.getString(_cursorIndexOfUserTextResponse);
            }
            final String _tmpUserImageUri;
            if (_cursor.isNull(_cursorIndexOfUserImageUri)) {
              _tmpUserImageUri = null;
            } else {
              _tmpUserImageUri = _cursor.getString(_cursorIndexOfUserImageUri);
            }
            final String _tmpGemmaFeedback;
            if (_cursor.isNull(_cursorIndexOfGemmaFeedback)) {
              _tmpGemmaFeedback = null;
            } else {
              _tmpGemmaFeedback = _cursor.getString(_cursorIndexOfGemmaFeedback);
            }
            final boolean _tmpIsEvaluated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEvaluated);
            _tmpIsEvaluated = _tmp != 0;
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final Long _tmpEvaluatedAt;
            if (_cursor.isNull(_cursorIndexOfEvaluatedAt)) {
              _tmpEvaluatedAt = null;
            } else {
              _tmpEvaluatedAt = _cursor.getLong(_cursorIndexOfEvaluatedAt);
            }
            _item = new RevisionSubmission(_tmpId,_tmpChapterId,_tmpQuestionIndex,_tmpQuestion,_tmpExpectedAnswer,_tmpUserTextResponse,_tmpUserImageUri,_tmpGemmaFeedback,_tmpIsEvaluated,_tmpSubmittedAt,_tmpEvaluatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
