package com.edify.learning.data.util

import com.edify.learning.data.model.ChapterContent
import com.edify.learning.data.model.QuestionItem
import com.edify.learning.data.model.SummaryItem
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
            
            val htmlContent = content?.get("html_content")?.jsonPrimitive?.content ?: ""
            
            // Parse questions
            val questionsArray = content?.get("questions")?.jsonArray
            
            val questions: List<QuestionItem> = questionsArray?.mapNotNull { questionElement ->
                try {
                    val questionObj = questionElement.jsonObject
                    QuestionItem(
                        question = questionObj["question"]?.jsonPrimitive?.content ?: "",
                        answer = questionObj["answer"]?.jsonPrimitive?.content ?: ""
                    )
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
            
            // Parse summary
            val summaryObj = content?.get("summary")?.jsonObject
            val summary = summaryObj?.let {
                try {
                    val keyConcepts = it["key_concepts"]?.jsonArray?.mapNotNull { concept ->
                        concept.jsonPrimitive.content
                    } ?: emptyList()
                    
                    SummaryItem(
                        title = it["title"]?.jsonPrimitive?.content ?: "",
                        keyConcepts = keyConcepts,
                        summary = it["summary"]?.jsonPrimitive?.content ?: ""
                    )
                } catch (e: Exception) {
                    null
                }
            }
            
            ChapterContent(
                htmlContent = htmlContent,
                questions = questions,
                summary = summary
            )
        } catch (e: Exception) {
            null
        }
    }
    
    // Helper functions for HTML content processing can be added here if needed
}
