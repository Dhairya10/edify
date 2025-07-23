package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "user_responses",
    foreignKeys = [
        ForeignKey(
            entity = Chapter::class,
            parentColumns = ["id"],
            childColumns = ["chapterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["chapterId", "exerciseIndex"], unique = true)]
)
data class UserResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterId: String,
    val exerciseIndex: Int, // Index of the exercise in the chapter's exercises array
    val textResponse: String? = null,
    val imageUri: String? = null, // Local file path for uploaded image
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
