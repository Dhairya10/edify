package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "chapters",
    foreignKeys = [
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Chapter(
    @PrimaryKey
    val id: String,
    val subjectId: String,
    val title: String,
    val description: String,
    val content: String, // JSON format content
    val chapterNumber: Int,
    val isCompleted: Boolean = false,
    val readingProgress: Float = 0f, // 0.0 to 1.0
    val estimatedReadingTime: Int, // in minutes
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable
