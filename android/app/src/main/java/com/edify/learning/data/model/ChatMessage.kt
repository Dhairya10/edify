package com.edify.learning.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey
    val id: String,
    val sessionId: String,
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val messageType: MessageType = MessageType.TEXT,
    val attachmentPath: String? = null
) : Parcelable

enum class MessageType {
    TEXT,
    IMAGE,
    VOICE
}

@Parcelize
data class ChatSession(
    val id: String,
    val title: String,
    val chapterId: String? = null,
    val contextContent: String? = null, // Selected text that started the chat
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable
