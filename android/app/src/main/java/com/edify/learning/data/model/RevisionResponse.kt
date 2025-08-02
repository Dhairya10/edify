package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "revision_responses",
    foreignKeys = [
        ForeignKey(
            entity = Chapter::class,
            parentColumns = ["id"],
            childColumns = ["chapterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["chapterId", "questionIndex"], unique = true)]
)
data class RevisionResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterId: String,
    val questionIndex: Int,
    val question: String,
    val userAnswer: String? = null,
    val imageUri: String? = null,
    val gemmaFeedback: String? = null,
    val feedbackCategory: String? = null, // "Needs Improvement", "Good Job", "Excellent"
    val isSubmitted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class FeedbackCategory(val displayName: String) {
    NEEDS_IMPROVEMENT("Needs Improvement"),
    GOOD_JOB("Good Job"),
    EXCELLENT("Excellent")
}