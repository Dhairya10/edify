package com.edify.learning.data.util

import com.edify.learning.data.model.ChapterContent
import com.edify.learning.data.model.ContentItem
import com.edify.learning.data.model.ImageMetadata
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object ContentParser {
    private val json = Json.Default
    
    fun parseChapterContent(jsonString: String): ChapterContent? {
        return try {
            val jsonObject = json.parseToJsonElement(jsonString).jsonObject
            val content = jsonObject["content"]?.jsonObject
            
            val markdownText = content?.get("markdown_text")?.jsonPrimitive?.content ?: ""
            
            // Parse images
            val imagesArray = content?.get("images")?.jsonArray
            
            val images: List<ImageMetadata> = imagesArray?.mapNotNull { imageElement ->
                try {
                    val imageObj = imageElement.jsonObject
                    ImageMetadata(
                        name = imageObj["name"]?.jsonPrimitive?.content ?: "",
                        path = imageObj["path"]?.jsonPrimitive?.content ?: "",
                        sizeBytes = imageObj["size_bytes"]?.jsonPrimitive?.content?.toLongOrNull() ?: 0L,
                        description = imageObj["description"]?.jsonPrimitive?.content,
                        descriptionGeneratedAt = imageObj["description_generated_at"]?.jsonPrimitive?.content
                    )
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
            
            // Parse structured content items if available
            val contentItems: List<ContentItem> = content?.get("structured_data")?.jsonObject?.get("contentItems")?.jsonArray?.mapNotNull { itemElement ->
                try {
                    val itemObj = itemElement.jsonObject
                    val type = itemObj["type"]?.jsonPrimitive?.content
                    val blockId = itemObj["blockId"]?.jsonPrimitive?.content ?: ""
                    
                    when (type) {
                        "text" -> ContentItem.TextContent(
                            blockId = blockId,
                            text = itemObj["selectedText"]?.jsonPrimitive?.content ?: "",
                            startOffset = itemObj["startOffset"]?.jsonPrimitive?.content?.toIntOrNull(),
                            endOffset = itemObj["endOffset"]?.jsonPrimitive?.content?.toIntOrNull(),
                            selectedText = itemObj["selectedText"]?.jsonPrimitive?.content
                        )
                        "image" -> ContentItem.ImageContent(
                            blockId = blockId,
                            src = itemObj["src"]?.jsonPrimitive?.content ?: "",
                            caption = itemObj["caption"]?.jsonPrimitive?.content
                        )
                        else -> null
                    }
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
            
            ChapterContent(
                markdownText = markdownText,
                contentItems = contentItems,
                images = images
            )
        } catch (e: Exception) {
            null
        }
    }
    
    fun extractImageReferences(markdownText: String): List<String> {
        val imageRegex = """!\[.*?\]\((.*?)\)""".toRegex()
        return imageRegex.findAll(markdownText)
            .map { it.groupValues[1] }
            .toList()
    }
    
    fun replaceImagePaths(markdownText: String, baseImagePath: String): String {
        val imageRegex = """!\[([^\]]*)\]\(([^)]+)\)""".toRegex()
        return imageRegex.replace(markdownText) { matchResult ->
            val altText = matchResult.groupValues[1]
            val imagePath = matchResult.groupValues[2]
            val fullPath = if (imagePath.startsWith("/")) {
                "file://$baseImagePath${imagePath.substringAfterLast("/")}"
            } else {
                "file://$baseImagePath/$imagePath"
            }
            "![$altText]($fullPath)"
        }
    }
}
