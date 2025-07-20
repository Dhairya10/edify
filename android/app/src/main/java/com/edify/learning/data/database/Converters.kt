package com.edify.learning.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.edify.learning.data.model.ContentItem
import com.edify.learning.data.model.MessageType
import com.edify.learning.data.model.NoteType

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
}
