package com.edify.learning.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edify.learning.data.model.ChapterContent
import com.edify.learning.ui.theme.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

@Composable
fun HtmlRenderer(
    content: ChapterContent,
    modifier: Modifier = Modifier,
    onTextSelected: (String) -> Unit = {},
    onContentSelected: (String, Boolean) -> Unit = { _, _ -> },
    selectedContent: String = "",
    isSelectionMode: Boolean = false,
    onParagraphSelected: (String) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Parse and render HTML content
        val htmlElements = parseHtmlToElements(content.htmlContent)
        
        htmlElements.forEach { element ->
            when (element) {
                is HtmlElement.Text -> {
                    HtmlText(
                        text = element.content,
                        style = element.style,
                        onTextSelected = onTextSelected,
                        onContentSelected = { text -> onContentSelected(text, false) },
                        isSelected = selectedContent == element.content,
                        isSelectionMode = isSelectionMode,
                        onParagraphSelected = onParagraphSelected
                    )
                }
                is HtmlElement.Header -> {
                    HtmlHeader(
                        text = element.text,
                        level = element.level
                    )
                }
                is HtmlElement.List -> {
                    HtmlList(items = element.items)
                }
            }
        }
    }
}

@Composable
private fun HtmlText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    onTextSelected: (String) -> Unit = {},
    onContentSelected: (String) -> Unit = {},
    isSelected: Boolean = false,
    isSelectionMode: Boolean = false,
    onParagraphSelected: (String) -> Unit = {}
) {
    // Process HTML formatting to annotated string
    val annotatedString = buildAnnotatedString {
        processHtmlFormatting(text, this)
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        if (isSelectionMode) {
            // In selection mode, disable text selection and show as clickable paragraph
            Text(
                text = annotatedString,
                style = style.copy(color = TextPrimary),
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            // Normal mode with text selection enabled
            SelectionContainer(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = annotatedString,
                    style = style.copy(color = TextPrimary),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun HtmlHeader(
    text: String,
    level: Int
) {
    val style = when (level) {
        1 -> MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        2 -> MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        3 -> MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        4 -> MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        5 -> MaterialTheme.typography.titleMedium
        else -> MaterialTheme.typography.titleSmall
    }
    Text(
        text = text,
        style = style.copy(color = MaterialTheme.colorScheme.onBackground),
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun HtmlList(items: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items.forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "• ",
                    style = MaterialTheme.typography.bodyMedium.copy(color = SecondaryBlue),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// Data classes for HTML elements
sealed class HtmlElement {
    data class Text(val content: String, val style: TextStyle = TextStyle.Default) : HtmlElement()
    data class Header(val text: String, val level: Int) : HtmlElement()
    data class List(val items: kotlin.collections.List<String>) : HtmlElement()
}

// Function to parse HTML and create elements
fun parseHtmlToElements(htmlContent: String): List<HtmlElement> {
    val elements = mutableListOf<HtmlElement>()
    
    try {
        val doc: Document = Jsoup.parse(htmlContent)
        val body = doc.body()
        
        processHtmlNode(body, elements)
        
    } catch (e: Exception) {
        // If HTML parsing fails, return as simple text
        elements.add(HtmlElement.Text(htmlContent.replace(Regex("<[^>]*>"), "")))
    }
    
    // Ensure we always return at least one element
    if (elements.isEmpty()) {
        elements.add(HtmlElement.Text("Content could not be loaded properly."))
    }
    
    return elements
}

private fun processHtmlNode(element: Element, elements: MutableList<HtmlElement>) {
    for (child in element.children()) {
        when (child.tagName().lowercase()) {
            "h1" -> elements.add(HtmlElement.Header(child.text(), 1))
            "h2" -> elements.add(HtmlElement.Header(child.text(), 2))
            "h3" -> elements.add(HtmlElement.Header(child.text(), 3))
            "h4" -> elements.add(HtmlElement.Header(child.text(), 4))
            "h5" -> elements.add(HtmlElement.Header(child.text(), 5))
            "h6" -> elements.add(HtmlElement.Header(child.text(), 6))
            "p" -> {
                val text = child.html()
                if (text.isNotBlank()) {
                    // Replace multiple <br> tags with single line breaks to reduce spacing
                    val cleanText = text.replace(Regex("<br\\s*/?>"), "\n")
                                        .replace(Regex("\n\\s*\n"), "\n")
                                        .trim()
                    if (cleanText.isNotEmpty()) {
                        elements.add(HtmlElement.Text(cleanText))
                    }
                }
            }
            "ul", "ol" -> {
                val items = child.select("li").map { it.text() }
                if (items.isNotEmpty()) {
                    elements.add(HtmlElement.List(items))
                }
            }
            "br" -> {
                // Skip standalone br tags to reduce excessive spacing
                // They will be handled within paragraph processing
            }
            else -> {
                // For other elements, recursively process children or get text
                if (child.hasText()) {
                    val text = child.html()
                    if (text.isNotBlank()) {
                        elements.add(HtmlElement.Text(text))
                    }
                } else {
                    processHtmlNode(child, elements)
                }
            }
        }
    }
}

private fun processHtmlFormatting(htmlText: String, annotatedStringBuilder: androidx.compose.ui.text.AnnotatedString.Builder) {
    try {
        val doc = Jsoup.parseBodyFragment(htmlText)
        processElementForAnnotation(doc.body(), annotatedStringBuilder)
    } catch (e: Exception) {
        // Fallback: just clean HTML tags and add as plain text
        val cleanText = htmlText.replace(Regex("<[^>]*>"), "")
        annotatedStringBuilder.append(cleanText)
    }
}

private fun processElementForAnnotation(element: Element, builder: androidx.compose.ui.text.AnnotatedString.Builder) {
    for (node in element.childNodes()) {
        when (node) {
            is TextNode -> {
                builder.append(node.text())
            }
            is Element -> {
                when (node.tagName().lowercase()) {
                    "strong", "b" -> {
                        builder.withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            processElementForAnnotation(node, builder)
                        }
                    }
                    "em", "i" -> {
                        builder.withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            processElementForAnnotation(node, builder)
                        }
                    }
                    "br" -> {
                        builder.append(" ")
                    }
                    else -> {
                        processElementForAnnotation(node, builder)
                    }
                }
            }
        }
    }
}