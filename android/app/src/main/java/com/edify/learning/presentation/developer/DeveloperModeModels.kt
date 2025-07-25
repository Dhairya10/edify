package com.edify.learning.presentation.developer

/**
 * Data models for developer mode
 */
data class DatabaseTable(
    val name: String,
    val entityClass: String,
    val recordCount: Int = 0
)

data class DatabaseRecord(
    val id: String,
    val data: Map<String, Any?>,
    val displayText: String
)

data class DeveloperModeUiState(
    val databaseTables: List<DatabaseTable> = emptyList(),
    val selectedTable: DatabaseTable? = null,
    val tableData: List<DatabaseRecord> = emptyList(),
    val selectedRecord: DatabaseRecord? = null,
    val showTableDataDialog: Boolean = false,
    val showAddRecordDialog: Boolean = false,
    val showEditRecordDialog: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
