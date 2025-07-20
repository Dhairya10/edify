package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val color: String,
    val iconRes: String,
    val totalChapters: Int,
    val completedChapters: Int,
    val lastReadChapterId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable {
    
    val progressPercentage: Int
        get() = if (totalChapters > 0) (completedChapters * 100) / totalChapters else 0
}
