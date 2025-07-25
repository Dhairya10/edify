package com.edify.learning.presentation.quest

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

// Helper functions for consistent subject icon and color usage
fun getSubjectIcon(subject: String): ImageVector {
    return when (subject.lowercase()) {
        "science" -> Icons.Default.Info
        "english" -> Icons.Default.Create
        "maths", "mathematics" -> Icons.Default.Add
        else -> Icons.Default.Info
    }
}

fun getSubjectColor(subject: String): Color {
    return when (subject.lowercase()) {
        "science" -> Color(0xFF4CAF50) // Green
        "english" -> Color(0xFF2196F3) // Blue
        "maths", "mathematics" -> Color(0xFFFF9800) // Orange
        else -> Color(0xFF2196F3) // Default blue
    }
}
