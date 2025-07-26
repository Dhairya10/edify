package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "generated_quests")
data class GeneratedQuest(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val chapterId: String,
    val subjectName: String,
    val title: String,
    val question: String,
    val questType: String = "divergent", // "divergent" or "convergent"
    val involvedChapterIds: String = "", // Comma-separated list of chapter IDs for convergent quests
    val userId: String = "default_user",
    val isCompleted: Boolean = false,
    val userAnswer: String? = null,
    val completedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
