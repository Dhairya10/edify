package com.edify.learning.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
sealed class ContentItem : Parcelable {
    abstract val blockId: String
    
    @Parcelize
    @Serializable
    data class TextContent(
        override val blockId: String,
        val text: String,
        val startOffset: Int? = null,
        val endOffset: Int? = null,
        val selectedText: String? = null
    ) : ContentItem()
    
    @Parcelize
    @Serializable
    data class ImageContent(
        override val blockId: String,
        val src: String,
        val caption: String? = null,
        val description: String? = null
    ) : ContentItem()
}

@Parcelize
@Serializable
data class ChapterContent(
    val htmlContent: String,
    val questions: List<QuestionItem> = emptyList(),
    val summary: SummaryItem? = null
) : Parcelable

@Parcelize
@Serializable
data class QuestionItem(
    val question: String,
    val answer: String
) : Parcelable

@Parcelize
@Serializable
data class SummaryItem(
    val title: String,
    val keyConcepts: List<String>,
    val summary: String
) : Parcelable
