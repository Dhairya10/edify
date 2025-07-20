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
    val markdownText: String,
    val contentItems: List<ContentItem> = emptyList(),
    val images: List<ImageMetadata> = emptyList()
) : Parcelable

@Parcelize
@Serializable
data class ImageMetadata(
    val name: String,
    val path: String,
    val sizeBytes: Long,
    val description: String? = null,
    val descriptionGeneratedAt: String? = null
) : Parcelable
