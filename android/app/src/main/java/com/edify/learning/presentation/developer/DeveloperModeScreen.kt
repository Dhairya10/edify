package com.edify.learning.presentation.developer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeveloperModeScreen(
    viewModel: DeveloperModeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showConfirmDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Developer Mode",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { viewModel.refreshData() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Data",
                            tint = Color.White
                        )
                    }
                }
                Text(
                    text = "Database Viewer & CRUD Operations",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Database Tables List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.databaseTables) { table ->
                DatabaseTableCard(
                    table = table,
                    onViewData = { viewModel.viewTableData(it) },
                    onAddRecord = { viewModel.showAddRecordDialog(it) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Clear Data Button
        Button(
            onClick = { showConfirmDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Clear All Data",
                fontWeight = FontWeight.Medium
            )
        }
    }
    
    // Show table data dialog
    if (uiState.showTableDataDialog) {
        TableDataDialog(
            table = uiState.selectedTable,
            data = uiState.tableData,
            onDismiss = { viewModel.hideTableDataDialog() },
            onEdit = { table, record -> viewModel.showEditRecordDialog(table, record) },
            onDelete = { table, record -> viewModel.deleteRecord(table, record) }
        )
    }
    
    // Show add record dialog
    if (uiState.showAddRecordDialog) {
        AddRecordDialog(
            table = uiState.selectedTable,
            onDismiss = { viewModel.hideAddRecordDialog() },
            onSave = { table, record -> viewModel.addRecord(table, record) }
        )
    }
    
    // Show edit record dialog
    if (uiState.showEditRecordDialog) {
        EditRecordDialog(
            table = uiState.selectedTable,
            record = uiState.selectedRecord,
            onDismiss = { viewModel.hideEditRecordDialog() },
            onSave = { table, record -> viewModel.updateRecord(table, record) }
        )
    }
    
    // Confirmation Dialog
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            containerColor = Color.DarkGray,
            titleContentColor = Color.White,
            textContentColor = Color.LightGray,
            title = {
                Text(
                    text = "Clear All Data?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "This will permanently delete all data. This action cannot be undone.\n\nAre you sure you want to continue?"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearAllData()
                        showConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Clear Data")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmDialog = false }
                ) {
                    Text("Cancel", color = Color.White)
                }
            }
        )
    }
}

@Composable
fun DatabaseTableCard(
    table: DatabaseTable,
    onViewData: (DatabaseTable) -> Unit,
    onAddRecord: (DatabaseTable) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onViewData(table) },
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = table.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    text = "${table.recordCount} records",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }
            IconButton(onClick = { onAddRecord(table) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Record",
                    tint = Color.White
                )
            }
        }
    }
}
