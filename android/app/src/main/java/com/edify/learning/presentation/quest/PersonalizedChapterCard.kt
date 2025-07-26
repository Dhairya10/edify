package com.edify.learning.presentation.quest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edify.learning.data.model.PersonalizedChapterQuest
import com.edify.learning.data.model.QuestState
import com.edify.learning.ui.theme.*

@Composable
fun PersonalizedChapterCard(
    chapter: PersonalizedChapterQuest,
    onClick: () -> Unit,
    isEnabled: Boolean = true
) {
    val isClickable = chapter.state != QuestState.LOCKED
    val containerColor = when (chapter.state) {
        QuestState.LOCKED -> Color(0xFFF5F5F5)
        QuestState.UNLOCKED -> White
        QuestState.COMPLETED -> Color(0xFFF0F8F0)
    }
    val textColor = when (chapter.state) {
        QuestState.LOCKED -> Color(0xFF9E9E9E)
        QuestState.UNLOCKED -> TextPrimary
        QuestState.COMPLETED -> Color(0xFF2E7D32)
    }
    val subjectColor = when (chapter.state) {
        QuestState.LOCKED -> Color(0xFFBDBDBD)
        QuestState.UNLOCKED -> TextSecondary
        QuestState.COMPLETED -> Color(0xFF4CAF50)
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isClickable && isEnabled) { 
                if (isClickable) onClick() 
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    // Subject name
                    Text(
                        text = chapter.subject,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = subjectColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    // Chapter title
                    Text(
                        text = chapter.chapterTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }
                
                // State indicator icon
                when (chapter.state) {
                    QuestState.LOCKED -> {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = Color(0xFFBDBDBD),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    QuestState.COMPLETED -> {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    QuestState.UNLOCKED -> {
                        // No icon for unlocked state - clean design
                    }
                }
            }
        }
    }
}
