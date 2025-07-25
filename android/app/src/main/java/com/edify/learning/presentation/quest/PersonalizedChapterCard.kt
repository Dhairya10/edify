package com.edify.learning.presentation.quest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edify.learning.data.model.Quest
import com.edify.learning.ui.theme.*

@Composable
fun PersonalizedChapterCard(
    chapter: PersonalizedChapterQuest,
    onClick: () -> Unit,
    isEnabled: Boolean = true
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isEnabled) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) White else White.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header with subject
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                val subjectIcon = when (chapter.subject.lowercase()) {
                    "science" -> Icons.Default.Info
                    "english" -> Icons.Default.Create
                    "maths", "mathematics" -> Icons.Default.Add
                    else -> Icons.Default.Info
                }
                
                val subjectColor = when (chapter.subject.lowercase()) {
                    "science" -> androidx.compose.ui.graphics.Color(0xFF4CAF50) // Green
                    "english" -> androidx.compose.ui.graphics.Color(0xFF2196F3) // Blue
                    "maths", "mathematics" -> androidx.compose.ui.graphics.Color(0xFFFF9800) // Orange
                    else -> androidx.compose.ui.graphics.Color(0xFF2196F3) // Default blue
                }
                
                Icon(
                    imageVector = subjectIcon,
                    contentDescription = chapter.subject,
                    tint = subjectColor,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = chapter.subject,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = subjectColor
                )
            }
            
            // Chapter title
            Text(
                text = chapter.chapterTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Description
            Text(
                text = chapter.description,
                fontSize = 14.sp,
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Interest score indicator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "Interest Score: ",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
                Text(
                    text = String.format("%.1f", chapter.interestScore),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                Text(
                    text = "Tap to explore →",
                    fontSize = 12.sp,
                    color = if (isEnabled) MaterialTheme.colorScheme.primary else TextSecondary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
