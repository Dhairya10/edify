package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quest(
    val id: String,
    val title: String,
    val description: String,
    val subject: String, // "Science", "English", "Maths"
    val difficulty: QuestDifficulty,
    val estimatedTime: Int, // in minutes
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

enum class QuestDifficulty {
    EASY, MEDIUM, HARD
}

@Parcelize
data class QuestQuestion(
    val id: String,
    val questId: String,
    val question: String,
    val subject: String,
    val curiosityLevel: Int, // 1-5 scale
    val challenges: List<String> = emptyList(),
    val expectedAnswer: String? = null,
    val hints: List<String> = emptyList()
) : Parcelable
