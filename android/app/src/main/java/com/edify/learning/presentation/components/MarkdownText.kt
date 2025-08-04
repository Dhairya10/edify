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
    // Decode HTML entities before processing markdown
    val decodedMarkdown = markdown
        .replace("&nbsp;", " ")
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&#39;", "'")
        .replace("&#x27;", "'")
        .replace("&#x2019;", "'")
        .replace("&#x2013;", "–")
        .replace("&#x2014;", "—")
        .replace("&hellip;", "…")
        .replace("&mdash;", "—")
        .replace("&ndash;", "–")
        .replace("&rsquo;", "'")
        .replace("&lsquo;", "'")
        .replace("&rdquo;", """)
        .replace("&ldquo;", """)
        .replace("&times;", "×")
        .replace("&divide;", "÷")
        .replace("&plusmn;", "±")
        .replace("&radic;", "√")
        .replace("&infin;", "∞")
        .replace("&pi;", "π")
        .replace("&alpha;", "α")
        .replace("&beta;", "β")
        .replace("&gamma;", "γ")
        .replace("&delta;", "δ")
        .replace("&theta;", "θ")
        .replace("&lambda;", "λ")
        .replace("&mu;", "μ")
        .replace("&sigma;", "σ")
        .replace("&phi;", "φ")
        .replace("&omega;", "ω")
        .replace(Regex("<[^>]*>"), "") // Remove any HTML tags
    
    val lines = decodedMarkdown.split("\n")
    
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
    // Decode HTML entities in inline text as well
    val decodedText = text
        .replace("&nbsp;", " ")
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&#39;", "'")
        .replace("&#x27;", "'")
        .replace("&#x2019;", "'")
        .replace("&#x2013;", "–")
        .replace("&#x2014;", "—")
        .replace("&hellip;", "…")
        .replace("&mdash;", "—")
        .replace("&ndash;", "–")
        .replace("&rsquo;", "'")
        .replace("&lsquo;", "'")
        .replace("&rdquo;", """)
        .replace("&ldquo;", """)
        .replace("&times;", "×")
        .replace("&divide;", "÷")
        .replace("&plusmn;", "±")
        .replace("&radic;", "√")
        .replace("&infin;", "∞")
        .replace("&pi;", "π")
        .replace("&alpha;", "α")
        .replace("&beta;", "β")
        .replace("&gamma;", "γ")
        .replace("&delta;", "δ")
        .replace("&theta;", "θ")
        .replace("&lambda;", "λ")
        .replace("&mu;", "μ")
        .replace("&sigma;", "σ")
        .replace("&phi;", "φ")
        .replace("&omega;", "ω")
        .replace(Regex("<[^>]*>"), "") // Remove any HTML tags
    var currentIndex = 0
    val length = decodedText.length
    
    while (currentIndex < length) {
        when {
            // Bold text **text**
            currentIndex < length - 1 && decodedText.substring(currentIndex).startsWith("**") -> {
                val endIndex = decodedText.indexOf("**", currentIndex + 2)
                if (endIndex != -1) {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(decodedText.substring(currentIndex + 2, endIndex))
                    }
                    currentIndex = endIndex + 2
                } else {
                    append(decodedText[currentIndex])
                    currentIndex++
                }
            }
            
            // Italic text *text*
            currentIndex < length - 1 && decodedText[currentIndex] == '*' && 
            (currentIndex == 0 || decodedText[currentIndex - 1] != '*') -> {
                val endIndex = decodedText.indexOf('*', currentIndex + 1)
                if (endIndex != -1 && (endIndex == length - 1 || decodedText[endIndex + 1] != '*')) {
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(decodedText.substring(currentIndex + 1, endIndex))
                    }
                    currentIndex = endIndex + 1
                } else {
                    append(decodedText[currentIndex])
                    currentIndex++
                }
            }
            
            // Inline code `code`
            decodedText[currentIndex] == '`' -> {
                val endIndex = decodedText.indexOf('`', currentIndex + 1)
                if (endIndex != -1) {
                    withStyle(style = SpanStyle(
                        fontFamily = FontFamily.Monospace,
                        background = Color.Gray.copy(alpha = 0.1f)
                    )) {
                        append(decodedText.substring(currentIndex + 1, endIndex))
                    }
                    currentIndex = endIndex + 1
                } else {
                    append(decodedText[currentIndex])
                    currentIndex++
                }
            }
            
            // Strikethrough ~~text~~
            currentIndex < length - 1 && decodedText.substring(currentIndex).startsWith("~~") -> {
                val endIndex = decodedText.indexOf("~~", currentIndex + 2)
                if (endIndex != -1) {
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                        append(decodedText.substring(currentIndex + 2, endIndex))
                    }
                    currentIndex = endIndex + 2
                } else {
                    append(decodedText[currentIndex])
                    currentIndex++
                }
            }
            
            // Regular character
            else -> {
                append(decodedText[currentIndex])
                currentIndex++
            }
        }
    }
}
