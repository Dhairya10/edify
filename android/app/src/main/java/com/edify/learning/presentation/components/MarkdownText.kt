package com.edify.learning.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 20.sp
) {
    val lines = markdown.split("\n")
    
    Column(modifier = modifier) {
        var i = 0
        while (i < lines.size) {
            val line = lines[i].trim()
            
            when {
                // Headers
                line.startsWith("# ") -> {
                    Text(
                        text = line.substring(2),
                        style = MaterialTheme.typography.headlineSmall,
                        color = color,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                line.startsWith("## ") -> {
                    Text(
                        text = line.substring(3),
                        style = MaterialTheme.typography.titleMedium,
                        color = color,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                }
                line.startsWith("### ") -> {
                    Text(
                        text = line.substring(4),
                        style = MaterialTheme.typography.titleSmall,
                        color = color,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                
                // Bullet points
                line.startsWith("- ") || line.startsWith("* ") -> {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "•",
                            color = color,
                            fontSize = fontSize,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = parseInlineMarkdown(line.substring(2)),
                            color = color,
                            fontSize = fontSize,
                            lineHeight = lineHeight
                        )
                    }
                }
                
                // Numbered lists
                line.matches(Regex("^\\d+\\. .*")) -> {
                    val parts = line.split(". ", limit = 2)
                    if (parts.size == 2) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "${parts[0]}.",
                                color = color,
                                fontSize = fontSize,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = parseInlineMarkdown(parts[1]),
                                color = color,
                                fontSize = fontSize,
                                lineHeight = lineHeight
                            )
                        }
                    }
                }
                
                // Code blocks (simple single line)
                line.startsWith("```") && line.endsWith("```") && line.length > 6 -> {
                    Text(
                        text = line.substring(3, line.length - 3),
                        fontFamily = FontFamily.Monospace,
                        color = color.copy(alpha = 0.8f),
                        fontSize = fontSize * 0.9f,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                
                // Empty line
                line.isEmpty() -> {
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
                
                // Regular text with inline formatting
                else -> {
                    if (line.isNotEmpty()) {
                        Text(
                            text = parseInlineMarkdown(line),
                            color = color,
                            fontSize = fontSize,
                            lineHeight = lineHeight,
                            modifier = Modifier.padding(vertical = 1.dp)
                        )
                    }
                }
            }
            i++
        }
    }
}

@Composable
private fun parseInlineMarkdown(text: String) = buildAnnotatedString {
    var currentIndex = 0
    val length = text.length
    
    while (currentIndex < length) {
        when {
            // Bold text **text**
            currentIndex < length - 1 && text.substring(currentIndex).startsWith("**") -> {
                val endIndex = text.indexOf("**", currentIndex + 2)
                if (endIndex != -1) {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(text.substring(currentIndex + 2, endIndex))
                    }
                    currentIndex = endIndex + 2
                } else {
                    append(text[currentIndex])
                    currentIndex++
                }
            }
            
            // Italic text *text*
            currentIndex < length - 1 && text[currentIndex] == '*' && 
            (currentIndex == 0 || text[currentIndex - 1] != '*') -> {
                val endIndex = text.indexOf('*', currentIndex + 1)
                if (endIndex != -1 && (endIndex == length - 1 || text[endIndex + 1] != '*')) {
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(text.substring(currentIndex + 1, endIndex))
                    }
                    currentIndex = endIndex + 1
                } else {
                    append(text[currentIndex])
                    currentIndex++
                }
            }
            
            // Inline code `code`
            text[currentIndex] == '`' -> {
                val endIndex = text.indexOf('`', currentIndex + 1)
                if (endIndex != -1) {
                    withStyle(style = SpanStyle(
                        fontFamily = FontFamily.Monospace,
                        background = Color.Gray.copy(alpha = 0.1f)
                    )) {
                        append(text.substring(currentIndex + 1, endIndex))
                    }
                    currentIndex = endIndex + 1
                } else {
                    append(text[currentIndex])
                    currentIndex++
                }
            }
            
            // Strikethrough ~~text~~
            currentIndex < length - 1 && text.substring(currentIndex).startsWith("~~") -> {
                val endIndex = text.indexOf("~~", currentIndex + 2)
                if (endIndex != -1) {
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                        append(text.substring(currentIndex + 2, endIndex))
                    }
                    currentIndex = endIndex + 2
                } else {
                    append(text[currentIndex])
                    currentIndex++
                }
            }
            
            // Regular character
            else -> {
                append(text[currentIndex])
                currentIndex++
            }
        }
    }
}
