package com.edify.learning.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.edify.learning.data.model.ContentItem
import com.edify.learning.data.model.MessageType
import com.edify.learning.data.model.NoteType
import com.edify.learning.data.model.FeedbackCategory

class Converters {
    
    private val gson = Gson()
    
    @TypeConverter
    fun fromNoteType(noteType: NoteType): String {
        return noteType.name
    }
    
    @TypeConverter
    fun toNoteType(noteType: String): NoteType {
        return NoteType.valueOf(noteType)
    }
    
    @TypeConverter
    fun fromMessageType(messageType: MessageType): String {
        return messageType.name
    }
    
    @TypeConverter
    fun toMessageType(messageType: String): MessageType {
        return MessageType.valueOf(messageType)
    }
    
    @TypeConverter
    fun fromContentItemsList(contentItems: List<ContentItem>): String {
        return gson.toJson(contentItems)
    }
    
    @TypeConverter
    fun toContentItemsList(contentItemsString: String): List<ContentItem> {
        val listType = object : TypeToken<List<ContentItem>>() {}.type
        return gson.fromJson(contentItemsString, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromStringList(stringList: List<String>?): String? {
        return gson.toJson(stringList)
    }

    @TypeConverter
    fun toStringList(stringListString: String?): List<String>? {
        if (stringListString == null) return null
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(stringListString, listType)
    }
    
    @TypeConverter
    fun fromFeedbackCategory(feedbackCategory: FeedbackCategory?): String? {
        return feedbackCategory?.name
    }
    
    @TypeConverter
    fun toFeedbackCategory(feedbackCategory: String?): FeedbackCategory? {
        return feedbackCategory?.let { FeedbackCategory.valueOf(it) }
    }
}
