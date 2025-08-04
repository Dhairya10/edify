package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "revision_submissions",
    foreignKeys = [
        ForeignKey(
            entity = Chapter::class,
            parentColumns = ["id"],
            childColumns = ["chapterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["chapterId", "questionIndex"])]
)
data class RevisionSubmission(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterId: String,
    val questionIndex: Int,
    val question: String,
    val expectedAnswer: String,
    val userTextResponse: String? = null,
    val userImageUri: String? = null,
    val gemmaFeedback: String? = null,
    val isEvaluated: Boolean = false,
    val submittedAt: Long = System.currentTimeMillis(),
    val evaluatedAt: Long? = null
)

data class RevisionHistory(
    val chapterId: String,
    val questionIndex: Int,
    val question: String,
    val expectedAnswer: String,
    val submissions: List<RevisionSubmission> = emptyList(),
    val latestSubmission: RevisionSubmission? = submissions.maxByOrNull { it.submittedAt },
    val totalSubmissions: Int = submissions.size
)