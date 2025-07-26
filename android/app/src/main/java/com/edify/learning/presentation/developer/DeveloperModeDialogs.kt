package com.edify.learning.presentation.developer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun TableDataDialog(
    table: DatabaseTable?,
    data: List<DatabaseRecord>,
    onDismiss: () -> Unit,
    onEdit: (DatabaseTable, DatabaseRecord) -> Unit,
    onDelete: (DatabaseTable, DatabaseRecord) -> Unit
) {
    if (table == null) return
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${table.name} Data",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Data list
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(data) { record ->
                        RecordCard(
                            record = record,
                            onEdit = { onEdit(table, record) },
                            onDelete = { onDelete(table, record) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecordCard(
    record: DatabaseRecord,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "ID: ${record.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = record.displayText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddRecordDialog(
    table: DatabaseTable?,
    onDismiss: () -> Unit,
    onSave: (DatabaseTable, Map<String, Any?>) -> Unit
) {
    if (table == null) return
    
    var recordData by remember { mutableStateOf(mapOf<String, String>()) }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add ${table.name} Record",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Form fields based on table type
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(getFieldsForTable(table.entityClass)) { field ->
                        OutlinedTextField(
                            value = recordData[field] ?: "",
                            onValueChange = { recordData = recordData + (field to it) },
                            label = { Text(field) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = if (field.contains("id") || field.contains("count") || field.contains("time")) {
                                KeyboardOptions(keyboardType = KeyboardType.Number)
                            } else {
                                KeyboardOptions.Default
                            }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onSave(table, recordData)
                            onDismiss()
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
fun EditRecordDialog(
    table: DatabaseTable?,
    record: DatabaseRecord?,
    onDismiss: () -> Unit,
    onSave: (DatabaseTable, Map<String, Any?>) -> Unit
) {
    if (table == null || record == null) return
    
    var recordData by remember { 
        mutableStateOf(record.data.mapValues { it.value?.toString() ?: "" })
    }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Edit ${table.name} Record",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Form fields
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recordData.keys.toList()) { field ->
                        OutlinedTextField(
                            value = recordData[field] ?: "",
                            onValueChange = { recordData = recordData + (field to it) },
                            label = { Text(field) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = field != "id", // Don't allow editing IDs
                            keyboardOptions = if (field.contains("id") || field.contains("count") || field.contains("time")) {
                                KeyboardOptions(keyboardType = KeyboardType.Number)
                            } else {
                                KeyboardOptions.Default
                            }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onSave(table, recordData)
                            onDismiss()
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

private fun getFieldsForTable(entityClass: String): List<String> {
    return when (entityClass) {
        "Subject" -> listOf("id", "name", "description", "iconName")
        "Chapter" -> listOf("id", "subjectId", "title", "description", "order", "jsonFileName")
        "Note" -> listOf("id", "subjectId", "chapterId", "title", "content", "createdAt", "updatedAt")
        "ChatMessage" -> listOf("id", "sessionId", "chapterId", "content", "isFromUser", "timestamp", "messageType", "attachmentPath")
        "UserResponse" -> listOf("id", "chapterId", "questionId", "userAnswer", "isCorrect", "timestamp")
        "ChapterStats" -> listOf("id", "chapterId", "userId", "visitCount", "noteCount", "revisionHistory", "chatHistory", "interestScore", "lastVisited")
        "UserProfile" -> listOf("userId", "hasUnlockedPersonalizedQuests", "createdAt", "updatedAt")
        else -> emptyList()
    }
}
