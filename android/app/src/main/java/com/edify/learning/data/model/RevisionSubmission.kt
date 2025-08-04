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
    val gemmaGrade: FeedbackCategory? = null,
    val isEvaluated: Boolean = false,
    val submittedAt: Long = System.currentTimeMillis(),
    val evaluatedAt: Long? = null
)

enum class FeedbackCategory(val displayName: String) {
    NEEDS_IMPROVEMENT("Needs Improvement"),
    GOOD_JOB("Good Job"), 
    EXCELLENT("Excellent")
}

data class RevisionHistory(
    val chapterId: String,
    val questionIndex: Int,
    val question: String,
    val expectedAnswer: String,
    val submissions: List<RevisionSubmission> = emptyList(),
    val latestSubmission: RevisionSubmission? = submissions.lastOrNull(),
    val totalSubmissions: Int = submissions.size,
    val hasImproved: Boolean = submissions.size >= 2 && 
        submissions.takeLast(2).let { recent ->
            recent.size == 2 && recent[1].gemmaGrade?.ordinal ?: -1 > recent[0].gemmaGrade?.ordinal ?: -1
        }
)