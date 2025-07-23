package com.edify.learning.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.edify.learning.data.model.ChapterContent
import com.edify.learning.data.model.ImageMetadata
import com.edify.learning.ui.theme.*
import java.io.File

@Composable
fun MarkdownRenderer(
    content: ChapterContent,
    baseImagePath: String,
    modifier: Modifier = Modifier,
    onTextSelected: (String) -> Unit = {},
    onImageClick: (String) -> Unit = {},
    onContentSelected: (String, Boolean) -> Unit = { _, _ -> }, // content, isImage
    selectedContent: String = ""
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Parse and render markdown content
        val processedMarkdown = processMarkdownWithImages(content.markdownText, content.images, baseImagePath)
        
        processedMarkdown.forEach { element ->
            when (element) {
                is MarkdownElement.Text -> {
                    MarkdownText(
                        text = element.content,
                        style = element.style,
                        onTextSelected = onTextSelected,
                        onContentSelected = { text -> onContentSelected(text, false) },
                        isSelected = selectedContent == element.content
                    )
                }
                is MarkdownElement.Image -> {
                    MarkdownImage(
                        imagePath = element.path,
                        onImageClick = onImageClick,
                        onContentSelected = { path -> onContentSelected(path, true) },
                        isSelected = selectedContent == element.path
                    )
                }
                is MarkdownElement.Header -> {
                    MarkdownHeader(
                        text = element.text,
                        level = element.level
                    )
                }
                is MarkdownElement.List -> {
                    MarkdownList(items = element.items)
                }
                is MarkdownElement.Math -> {
                    MarkdownMath(expression = element.expression)
                }
            }
        }
    }
}

@Composable
private fun MarkdownText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    onTextSelected: (String) -> Unit = {},
    onContentSelected: (String) -> Unit = {},
    isSelected: Boolean = false
) {
    // Clean HTML tags and process markdown formatting
    val cleanedText = text
        .replace("<sup>", "^")
        .replace("</sup>", "")
        .replace("<sub>", "_")
        .replace("</sub>", "")
        .replace("<strong>", "**")
        .replace("</strong>", "**")
        .replace("<em>", "*")
        .replace("</em>", "*")
        .replace("<b>", "**")
        .replace("</b>", "**")
        .replace("<i>", "*")
        .replace("</i>", "*")
        .replace("&nbsp;", " ")
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&#39;", "'")
        .replace(Regex("<[^>]*>"), "") // Remove any remaining HTML tags
    
    // Process markdown formatting with proper bold and italic support
    val annotatedString = buildAnnotatedString {
        val boldRegex = "\\*\\*(.*?)\\*\\*".toRegex()
        val italicRegex = "\\*(.*?)\\*".toRegex()
        
        // First handle bold text
        val boldMatches = boldRegex.findAll(cleanedText).toList()
        var textIndex = 0
        
        boldMatches.forEach { match ->
            val beforeBold = cleanedText.substring(textIndex, match.range.first)
            val boldText = match.groupValues[1]
            
            // Add text before bold
            if (beforeBold.isNotEmpty()) {
                append(beforeBold.replace("**", "").replace("*", ""))
            }
            
            // Add bold text
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(boldText)
            }
            
            textIndex = match.range.last + 1
        }
        
        // Add remaining text
        if (textIndex < cleanedText.length) {
            val remainingPart = cleanedText.substring(textIndex).replace("**", "").replace("*", "")
            append(remainingPart)
        }
        
        // If no bold text was found, just add the cleaned text
        if (boldMatches.isEmpty()) {
            append(cleanedText.replace("**", "").replace("*", "").replace("_", ""))
        }
    }
    
    // Simple SelectionContainer for native copy functionality
    SelectionContainer {
        Text(
            text = annotatedString,
            style = style.copy(color = TextPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
private fun MarkdownImage(
    imagePath: String,
    onImageClick: (String) -> Unit = {},
    onContentSelected: (String) -> Unit = {},
    isSelected: Boolean = false
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imagePath)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) SecondaryBlue else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { 
                if (isSelected) {
                    onContentSelected("")
                } else {
                    onImageClick(imagePath) 
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onContentSelected(imagePath)
                    }
                )
            },
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun MarkdownHeader(
    text: String,
    level: Int
) {
    val style = when (level) {
        1 -> MaterialTheme.typography.headlineLarge
        2 -> MaterialTheme.typography.headlineMedium
        3 -> MaterialTheme.typography.headlineSmall
        4 -> MaterialTheme.typography.titleLarge
        5 -> MaterialTheme.typography.titleMedium
        else -> MaterialTheme.typography.titleSmall
    }
    
    Text(
        text = text,
        style = style.copy(
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
private fun MarkdownList(items: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items.forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
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

@Composable
private fun MarkdownMath(expression: String) {
    // Clean and safely render LaTeX expressions
    val cleanExpression = expression
        .replace("\\sqrt{", "√(")  // Convert LaTeX sqrt to Unicode
        .replace("\\frac{", "(")   // Simplify fractions
        .replace("\\text{", "")    // Remove text commands
        .replace("\\rightarrow", "→") // Convert arrows
        .replace("\\times", "×")    // Convert multiplication
        .replace("\\cdot", "·")     // Convert dot multiplication
        .replace("\\pi", "π")       // Convert pi
        .replace("\\infty", "∞")    // Convert infinity
        .replace("\\pm", "±")       // Convert plus-minus
        .replace("\\leq", "≤")      // Convert less than or equal
        .replace("\\geq", "≥")      // Convert greater than or equal
        .replace("\\neq", "≠")      // Convert not equal
        .replace("\\approx", "≈")   // Convert approximately
        .replace("\\sum", "Σ")      // Convert sum
        .replace("\\int", "∫")      // Convert integral
        .replace("\\alpha", "α")    // Convert alpha
        .replace("\\beta", "β")     // Convert beta
        .replace("\\gamma", "γ")    // Convert gamma
        .replace("\\delta", "δ")    // Convert delta
        .replace("\\theta", "θ")    // Convert theta
        .replace("\\lambda", "λ")   // Convert lambda
        .replace("\\mu", "μ")       // Convert mu
        .replace("\\sigma", "σ")    // Convert sigma
        .replace("\\phi", "φ")      // Convert phi
        .replace("\\omega", "ω")    // Convert omega
        .replace("{", "(")          // Replace braces with parentheses
        .replace("}", ")")
        .replace("\\n", " ")        // Replace newlines with spaces
        .replace("\\t", " ")        // Replace tabs with spaces
        .replace("\\\\+".toRegex(), "") // Remove remaining backslashes
        .trim()
    
    if (cleanExpression.isNotBlank()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundGray)
        ) {
            Text(
                text = cleanExpression,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    color = TextPrimary,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

// Data classes for markdown elements
sealed class MarkdownElement {
    data class Text(val content: String, val style: TextStyle = TextStyle.Default) : MarkdownElement()
    data class Image(val path: String, val caption: String? = null) : MarkdownElement()
    data class Header(val text: String, val level: Int) : MarkdownElement()
    data class List(val items: kotlin.collections.List<String>) : MarkdownElement()
    data class Math(val expression: String) : MarkdownElement()
}

// Function to process markdown and create elements
fun processMarkdownWithImages(
    markdownText: String,
    images: List<ImageMetadata>,
    baseImagePath: String
): List<MarkdownElement> {
    val elements = mutableListOf<MarkdownElement>()
    
    try {
        val lines = markdownText.split("\n")
        
        var i = 0
        while (i < lines.size) {
            val line = lines.getOrNull(i)?.trim() ?: continue
        
            when {
                // Handle images
                line.startsWith("![") && line.contains("](") -> {
                    val imageRegex = """!\[([^\]]*)\]\(([^)]+)\)""".toRegex()
                    val match = imageRegex.find(line)
                    if (match != null) {
                        val altText = match.groupValues[1]
                        val imageName = match.groupValues[2]
                    
                    // Find corresponding image metadata
                    val imageMetadata = images.find { it.name == imageName }
                    val imagePath = if (imageMetadata != null) {
                        // Extract the chapter folder from the image path
                        // e.g., "/home/content/converted_books/images/jeff102/_page_0_Picture_0.jpeg" -> "jeff102/_page_0_Picture_0.jpeg"
                        val pathParts = imageMetadata.path.split("/")
                        val chapterFolder = pathParts.getOrNull(pathParts.size - 2) // e.g., "jeff102"
                        val fileName = pathParts.lastOrNull() // e.g., "_page_0_Picture_0.jpeg"
                        
                        if (chapterFolder != null && fileName != null) {
                            "file:///android_asset/images/$chapterFolder/$fileName"
                        } else {
                            "file:///android_asset/images/${imageName}"
                        }
                    } else {
                        // Fallback to base path + image name
                        "$baseImagePath/$imageName"
                    }
                    
                    elements.add(
                        MarkdownElement.Image(
                            path = imagePath,
                            caption = null // Remove caption as requested
                        )
                    )
                }
            }
            
            // Handle headers
            line.startsWith("#") -> {
                val level = line.takeWhile { it == '#' }.length
                val text = line.drop(level).trim()
                elements.add(MarkdownElement.Header(text, level))
            }
            
            // Handle math expressions - both single line and block
            line.trim().startsWith("$$") -> {
                val mathLines = mutableListOf<String>()
                var j = i
                var foundEnd = false
                
                // Handle single line math expression
                if (line.trim().endsWith("$$") && line.trim().length > 4) {
                    val expression = line.trim().substring(2, line.trim().length - 2)
                    if (expression.isNotBlank()) {
                        elements.add(MarkdownElement.Math(expression))
                    }
                    foundEnd = true
                } else {
                    // Handle multi-line math expression
                    mathLines.add(line.trim().substring(2)) // Remove opening $$
                    j++
                    
                    while (j < lines.size && !foundEnd) {
                        val mathLine = lines[j].trim()
                        if (mathLine.endsWith("$$")) {
                            mathLines.add(mathLine.substring(0, mathLine.length - 2)) // Remove closing $$
                            foundEnd = true
                        } else {
                            mathLines.add(mathLine)
                        }
                        j++
                    }
                    
                    if (foundEnd) {
                        val combinedExpression = mathLines.joinToString("\n").trim()
                        if (combinedExpression.isNotBlank()) {
                            elements.add(MarkdownElement.Math(combinedExpression))
                        }
                    } else {
                        // If no closing $$, treat as regular text
                        val combinedText = (listOf(line) + mathLines).joinToString("\n")
                        elements.add(MarkdownElement.Text(combinedText))
                    }
                }
                
                if (foundEnd) {
                    i = j - 1
                }
            }
            
            // Handle bullet lists
            line.startsWith("•") || line.startsWith("-") || line.startsWith("*") -> {
                val listItems = mutableListOf<String>()
                var j = i
                while (j < lines.size) {
                    val listLine = lines[j].trim()
                    if (listLine.startsWith("•") || listLine.startsWith("-") || listLine.startsWith("*")) {
                        listItems.add(listLine.substring(1).trim())
                        j++
                    } else {
                        break
                    }
                }
                elements.add(MarkdownElement.List(listItems))
                i = j - 1
            }
            
            // Handle regular text
            line.isNotBlank() -> {
                try {
                    // Collect consecutive text lines
                    val textLines = mutableListOf<String>()
                    var j = i
                    while (j < lines.size) {
                        val textLine = lines.getOrNull(j)?.trim() ?: break
                        if (textLine.isNotBlank() && 
                            !textLine.startsWith("#") && 
                            !textLine.startsWith("![") &&
                            !textLine.startsWith("$$") &&
                            !textLine.startsWith("•") &&
                            !textLine.startsWith("-") &&
                            !textLine.startsWith("*") &&
                            !textLine.startsWith("|")) {  // Also skip table rows
                            textLines.add(textLine)
                            j++
                        } else {
                            break
                        }
                    }
                    
                    if (textLines.isNotEmpty()) {
                        val combinedText = textLines.joinToString("\n")
                        if (combinedText.isNotBlank()) {
                            elements.add(MarkdownElement.Text(combinedText))
                        }
                        i = j - 1
                    }
                } catch (e: Exception) {
                    // If text processing fails, add as simple text
                    elements.add(MarkdownElement.Text(line))
                }
            }
        }
        i++
    }
    
    } catch (e: Exception) {
        // If markdown processing fails completely, return simple text element
        elements.clear()
        elements.add(MarkdownElement.Text(markdownText.replace("$$", "").replace("\\n", "\n")))
    }
    
    // Ensure we always return at least one element
    if (elements.isEmpty()) {
        elements.add(MarkdownElement.Text("Content could not be loaded properly."))
    }
    
    return elements
}
