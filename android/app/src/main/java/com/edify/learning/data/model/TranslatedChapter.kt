package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "translated_chapters",
    indices = [Index(value = ["chapterId", "language"], unique = true)]
)
data class TranslatedChapter(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterId: String,
    val language: String,
    val originalContentHash: String, // Hash of original content to detect changes
    val translatedContent: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
