package com.edify.learning.presentation.developer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeveloperModeScreen(
    viewModel: DeveloperModeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    
    val tabs = listOf("Database", "Quest Scoring")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
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
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Developer Mode",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { viewModel.refreshData() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Data",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                Text(
                    text = "Database Viewer & CRUD Operations",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tab Content
        when (selectedTab) {
            0 -> {
                // Database Tables List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.databaseTables) { table ->
                        DatabaseTableCard(
                            table = table,
                            onViewData = { viewModel.viewTableData(it) },
                            onAddRecord = { viewModel.showAddRecordDialog(it) }
                        )
                    }
                }
            }
            1 -> {
                // Quest Scoring Analysis
                QuestScoringScreen()
            }
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
}

@Composable
fun DatabaseTableCard(
    table: DatabaseTable,
    onViewData: (DatabaseTable) -> Unit,
    onAddRecord: (DatabaseTable) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = table.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${table.recordCount} records",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Row {
                    IconButton(onClick = { onAddRecord(table) }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Record"
                        )
                    }
                    IconButton(onClick = { onViewData(table) }) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "View Data"
                        )
                    }
                }
            }
        }
    }
}
