package com.edify.learning.presentation.developer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.database.EdifyDatabase
import com.edify.learning.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeveloperModeViewModel @Inject constructor(
    private val database: EdifyDatabase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DeveloperModeUiState())
    val uiState: StateFlow<DeveloperModeUiState> = _uiState.asStateFlow()
    
    init {
        loadDatabaseTables()
    }
    
    fun refreshData() {
        loadDatabaseTables()
    }
    
    fun clearAllData() {
        // For now, just call clearAllQuestData() since that's the only data we're clearing
        clearAllQuestData()
    }
    
    fun clearAllQuestData() {
        viewModelScope.launch {
            try {
                // Clear ChapterStats (contains interest scores and engagement data)
                database.chapterStatsDao().deleteAll()
                
                // Clear generated quests
                database.generatedQuestDao().deleteAllQuests()
                
                // Reset user profile quest unlock status
                val userProfiles = database.userProfileDao().getAllProfiles()
                userProfiles.forEach { profile: UserProfile ->
                    val updatedProfile = profile.copy(
                        hasUnlockedPersonalizedQuests = false,
                        updatedAt = System.currentTimeMillis()
                    )
                    database.userProfileDao().insertOrUpdate(updatedProfile)
                }
                
                // Refresh the data to show updated counts
                loadDatabaseTables()
                
                println("Quest data cleared successfully")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Failed to clear Quest data: ${e.message}")
                println("Error clearing Quest data: ${e.message}")
            }
        }
    }
    
    private fun loadDatabaseTables() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val tables = listOf(
                    DatabaseTable("Subjects", "Subject", getSubjectCount()),
                    DatabaseTable("Chapters", "Chapter", getChapterCount()),
                    DatabaseTable("Notes", "Note", getNoteCount()),
                    DatabaseTable("Chat Messages", "ChatMessage", getChatMessageCount()),
                    DatabaseTable("User Responses", "UserResponse", getUserResponseCount()),
                    DatabaseTable("Chapter Stats", "ChapterStats", getChapterStatsCount()),
                    DatabaseTable("User Profiles", "UserProfile", getUserProfileCount())
                )
                
                _uiState.value = _uiState.value.copy(
                    databaseTables = tables,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
    
    fun viewTableData(table: DatabaseTable) {
        viewModelScope.launch {
            try {
                val data = when (table.entityClass) {
                    "Subject" -> getSubjectData()
                    "Chapter" -> getChapterData()
                    "Note" -> getNoteData()
                    "ChatMessage" -> getChatMessageData()
                    "UserResponse" -> getUserResponseData()
                    "ChapterStats" -> getChapterStatsData()
                    "UserProfile" -> getUserProfileData()
                    else -> emptyList()
                }
                
                _uiState.value = _uiState.value.copy(
                    selectedTable = table,
                    tableData = data,
                    showTableDataDialog = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun hideTableDataDialog() {
        _uiState.value = _uiState.value.copy(
            showTableDataDialog = false,
            selectedTable = null,
            tableData = emptyList()
        )
    }
    
    fun showAddRecordDialog(table: DatabaseTable) {
        _uiState.value = _uiState.value.copy(
            selectedTable = table,
            showAddRecordDialog = true
        )
    }
    
    fun hideAddRecordDialog() {
        _uiState.value = _uiState.value.copy(
            showAddRecordDialog = false,
            selectedTable = null
        )
    }
    
    fun showEditRecordDialog(table: DatabaseTable, record: DatabaseRecord) {
        _uiState.value = _uiState.value.copy(
            selectedTable = table,
            selectedRecord = record,
            showEditRecordDialog = true
        )
    }
    
    fun hideEditRecordDialog() {
        _uiState.value = _uiState.value.copy(
            showEditRecordDialog = false,
            selectedTable = null,
            selectedRecord = null
        )
    }
    
    fun addRecord(table: DatabaseTable, recordData: Map<String, Any?>) {
        viewModelScope.launch {
            try {
                when (table.entityClass) {
                    "Subject" -> addSubject(recordData)
                    "Chapter" -> addChapter(recordData)
                    "Note" -> addNote(recordData)
                    "ChatMessage" -> addChatMessage(recordData)
                    "UserResponse" -> addUserResponse(recordData)
                    "ChapterStats" -> addChapterStats(recordData)
                    "UserProfile" -> addUserProfile(recordData)
                }
                
                // Refresh the table data
                loadDatabaseTables()
                if (_uiState.value.showTableDataDialog) {
                    viewTableData(table)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun updateRecord(table: DatabaseTable, recordData: Map<String, Any?>) {
        viewModelScope.launch {
            try {
                when (table.entityClass) {
                    "Subject" -> updateSubject(recordData)
                    "Chapter" -> updateChapter(recordData)
                    "Note" -> updateNote(recordData)
                    "ChatMessage" -> updateChatMessage(recordData)
                    "UserResponse" -> updateUserResponse(recordData)
                    "ChapterStats" -> updateChapterStats(recordData)
                    "UserProfile" -> updateUserProfile(recordData)
                }
                
                // Refresh the table data
                loadDatabaseTables()
                if (_uiState.value.showTableDataDialog) {
                    viewTableData(table)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun deleteRecord(table: DatabaseTable, record: DatabaseRecord) {
        viewModelScope.launch {
            try {
                when (table.entityClass) {
                    "Subject" -> database.subjectDao().deleteById(record.id)
                    "Chapter" -> database.chapterDao().deleteById(record.id)
                    "Note" -> database.noteDao().deleteById(record.id.toLong())
                    "ChatMessage" -> database.chatDao().deleteById(record.id.toLong())
                    "UserResponse" -> database.userResponseDao().deleteById(record.id.toLong())
                    "ChapterStats" -> database.chapterStatsDao().deleteById(record.id.toLong())
                    "UserProfile" -> database.userProfileDao().deleteById(record.id)
                }
                
                // Refresh the table data
                loadDatabaseTables()
                if (_uiState.value.showTableDataDialog) {
                    viewTableData(table)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    // Count methods
    private suspend fun getSubjectCount(): Int = database.subjectDao().getAllSubjects().first().size
    private suspend fun getChapterCount(): Int = database.chapterDao().getAllChapters().size
    private suspend fun getNoteCount(): Int = database.noteDao().getAllNotes().first().size
    private suspend fun getChatMessageCount(): Int = database.chatDao().getAllMessages().size
    private suspend fun getUserResponseCount(): Int = database.userResponseDao().getAllResponses().size
    private suspend fun getChapterStatsCount(): Int = database.chapterStatsDao().getAllStats().size
    private suspend fun getUserProfileCount(): Int = database.userProfileDao().getAllProfiles().size
    
    // Data retrieval methods
    private suspend fun getSubjectData(): List<DatabaseRecord> {
        return database.subjectDao().getAllSubjects().first().map { subject ->
            DatabaseRecord(
                id = subject.id,
                data = mapOf(
                    "id" to subject.id,
                    "name" to subject.name,
                    "description" to subject.description,
                    "color" to subject.color,
                    "iconRes" to subject.iconRes,
                    "totalChapters" to subject.totalChapters,
                    "completedChapters" to subject.completedChapters
                ),
                displayText = "Name: ${subject.name}\nDescription: ${subject.description}"
            )
        }
    }
    
    private suspend fun getChapterData(): List<DatabaseRecord> {
        return database.chapterDao().getAllChapters().map { chapter ->
            DatabaseRecord(
                id = chapter.id,
                data = mapOf(
                    "id" to chapter.id,
                    "subjectId" to chapter.subjectId,
                    "title" to chapter.title,
                    "description" to chapter.description,
                    "content" to chapter.content,
                    "chapterNumber" to chapter.chapterNumber,
                    "estimatedReadingTime" to chapter.estimatedReadingTime
                ),
                displayText = "Title: ${chapter.title}\nSubject: ${chapter.subjectId}\nChapter: ${chapter.chapterNumber}"
            )
        }
    }
    
    private suspend fun getNoteData(): List<DatabaseRecord> {
        return database.noteDao().getAllNotes().first().map { note ->
            DatabaseRecord(
                id = note.id,
                data = mapOf(
                    "id" to note.id,
                    "chapterId" to note.chapterId,
                    "title" to note.title,
                    "content" to note.content,
                    "type" to note.type.name,
                    "createdAt" to note.createdAt,
                    "updatedAt" to note.updatedAt
                ),
                displayText = "Title: ${note.title}\nChapter: ${note.chapterId}\nCreated: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(java.util.Date(note.createdAt))}"
            )
        }
    }
    
    private suspend fun getChatMessageData(): List<DatabaseRecord> {
        return database.chatDao().getAllMessages().map { message ->
            DatabaseRecord(
                id = message.id,
                data = mapOf(
                    "id" to message.id,
                    "sessionId" to message.sessionId,
                    "content" to message.content,
                    "isFromUser" to message.isFromUser,
                    "timestamp" to message.timestamp,
                    "messageType" to message.messageType.name,
                    "attachmentPath" to message.attachmentPath
                ),
                displayText = "${if (message.isFromUser) "User" else "AI"}: ${message.content.take(50)}${if (message.content.length > 50) "..." else ""}"
            )
        }
    }
    
    private suspend fun getUserResponseData(): List<DatabaseRecord> {
        return database.userResponseDao().getAllResponses().map { response ->
            DatabaseRecord(
                id = response.id.toString(),
                data = mapOf(
                    "id" to response.id,
                    "chapterId" to response.chapterId,
                    "exerciseIndex" to response.exerciseIndex,
                    "textResponse" to response.textResponse,
                    "imageUri" to response.imageUri,
                    "createdAt" to response.createdAt,
                    "updatedAt" to response.updatedAt
                ),
                displayText = "Chapter: ${response.chapterId}\nExercise: ${response.exerciseIndex}\nResponse: ${response.textResponse ?: "Image"}"
            )
        }
    }
    
    private suspend fun getChapterStatsData(): List<DatabaseRecord> {
        return database.chapterStatsDao().getAllStats().map { stats ->
            val interestScore = stats.calculateInterestScore()
            DatabaseRecord(
                id = stats.id.toString(),
                data = mapOf(
                    "id" to stats.id,
                    "chapterId" to stats.chapterId,
                    "userId" to stats.userId,
                    "visitCount" to stats.visitCount,
                    "noteCount" to stats.noteCount,
                    "revisionHistory" to stats.revisionHistory.size,
                    "chatHistory" to stats.chatHistory.size,
                    "interestScore" to String.format("%.2f", interestScore),
                    "lastVisited" to stats.lastVisited
                ),
                displayText = "Chapter: ${stats.chapterId}\nUser: ${stats.userId}\nVisits: ${stats.visitCount}\nNotes: ${stats.noteCount}\nInterest Score: ${String.format("%.2f", interestScore)}"
            )
        }
    }
    
    private suspend fun getUserProfileData(): List<DatabaseRecord> {
        return database.userProfileDao().getAllProfiles().map { profile ->
            DatabaseRecord(
                id = profile.userId,
                data = mapOf(
                    "userId" to profile.userId,
                    "hasUnlockedPersonalizedQuests" to profile.hasUnlockedPersonalizedQuests,
                    "createdAt" to profile.createdAt,
                    "updatedAt" to profile.updatedAt
                ),
                displayText = "User: ${profile.userId}\nQuests Unlocked: ${profile.hasUnlockedPersonalizedQuests}"
            )
        }
    }
    
    // Add methods
    private suspend fun addSubject(data: Map<String, Any?>) {
        val subject = Subject(
            id = data["id"]?.toString() ?: "",
            name = data["name"]?.toString() ?: "",
            description = data["description"]?.toString() ?: "",
            color = data["color"]?.toString() ?: "#FF6B35",
            iconRes = data["iconRes"]?.toString() ?: "ic_subject",
            totalChapters = data["totalChapters"]?.toString()?.toIntOrNull() ?: 0,
            completedChapters = data["completedChapters"]?.toString()?.toIntOrNull() ?: 0
        )
        database.subjectDao().insertSubject(subject)
    }
    
    private suspend fun addChapter(data: Map<String, Any?>) {
        val chapter = Chapter(
            id = data["id"]?.toString() ?: "",
            subjectId = data["subjectId"]?.toString() ?: "",
            title = data["title"]?.toString() ?: "",
            description = data["description"]?.toString() ?: "",
            content = data["content"]?.toString() ?: "{}",
            chapterNumber = data["chapterNumber"]?.toString()?.toIntOrNull() ?: 1,
            estimatedReadingTime = data["estimatedReadingTime"]?.toString()?.toIntOrNull() ?: 10
        )
        database.chapterDao().insertChapter(chapter)
    }
    
    private suspend fun addNote(data: Map<String, Any?>) {
        val note = Note(
            id = data["id"]?.toString() ?: "",
            chapterId = data["chapterId"]?.toString() ?: "",
            title = data["title"]?.toString() ?: "",
            content = data["content"]?.toString() ?: "",
            type = when (data["type"]?.toString()) {
                "HIGHLIGHT" -> NoteType.HIGHLIGHT
                "GEMMA_EXPLANATION" -> NoteType.GEMMA_EXPLANATION
                else -> NoteType.USER_NOTE
            }
        )
        database.noteDao().insertNote(note)
    }
    
    private suspend fun addChatMessage(data: Map<String, Any?>) {
        val message = ChatMessage(
            id = data["id"]?.toString() ?: "",
            sessionId = data["sessionId"]?.toString() ?: "default_session",
            content = data["content"]?.toString() ?: "",
            isFromUser = data["isFromUser"]?.toString()?.toBoolean() ?: false,
            timestamp = data["timestamp"]?.toString()?.toLongOrNull() ?: System.currentTimeMillis(),
            messageType = when (data["messageType"]?.toString()) {
                "IMAGE" -> MessageType.IMAGE
                "VOICE" -> MessageType.VOICE
                else -> MessageType.TEXT
            },
            attachmentPath = data["attachmentPath"]?.toString()
        )
        database.chatDao().insertMessage(message)
    }
    
    private suspend fun addUserResponse(data: Map<String, Any?>) {
        val response = UserResponse(
            id = data["id"]?.toString()?.toLongOrNull() ?: 0L,
            chapterId = data["chapterId"]?.toString() ?: "",
            exerciseIndex = data["exerciseIndex"]?.toString()?.toIntOrNull() ?: 0,
            textResponse = data["textResponse"]?.toString(),
            imageUri = data["imageUri"]?.toString()
        )
        database.userResponseDao().insertOrUpdateResponse(response)
    }
    
    private suspend fun addChapterStats(data: Map<String, Any?>) {
        val stats = ChapterStats(
            id = 0, // Auto-generated
            chapterId = data["chapterId"]?.toString() ?: "",
            userId = data["userId"]?.toString() ?: "default_user",
            visitCount = data["visitCount"]?.toString()?.toIntOrNull() ?: 0,
            noteCount = data["noteCount"]?.toString()?.toIntOrNull() ?: 0,
            revisionHistory = emptyList(),
            chatHistory = emptyList(),
            lastVisited = System.currentTimeMillis()
        )
        database.chapterStatsDao().insertOrUpdate(stats)
    }
    
    private suspend fun addUserProfile(data: Map<String, Any?>) {
        val profile = UserProfile(
            userId = data["userId"]?.toString() ?: "",
            hasUnlockedPersonalizedQuests = data["hasUnlockedPersonalizedQuests"]?.toString()?.toBoolean() ?: false,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        database.userProfileDao().insertOrUpdate(profile)
    }
    
    // Update methods
    private suspend fun updateSubject(data: Map<String, Any?>) {
        val subject = Subject(
            id = data["id"]?.toString() ?: "",
            name = data["name"]?.toString() ?: "",
            description = data["description"]?.toString() ?: "",
            color = data["color"]?.toString() ?: "#FF6B35",
            iconRes = data["iconRes"]?.toString() ?: "ic_subject",
            totalChapters = data["totalChapters"]?.toString()?.toIntOrNull() ?: 0,
            completedChapters = data["completedChapters"]?.toString()?.toIntOrNull() ?: 0
        )
        database.subjectDao().updateSubject(subject)
    }
    
    private suspend fun updateChapter(data: Map<String, Any?>) {
        val chapter = Chapter(
            id = data["id"]?.toString() ?: "",
            subjectId = data["subjectId"]?.toString() ?: "",
            title = data["title"]?.toString() ?: "",
            description = data["description"]?.toString() ?: "",
            content = data["content"]?.toString() ?: "{}",
            chapterNumber = data["chapterNumber"]?.toString()?.toIntOrNull() ?: 1,
            estimatedReadingTime = data["estimatedReadingTime"]?.toString()?.toIntOrNull() ?: 10
        )
        database.chapterDao().updateChapter(chapter)
    }
    
    private suspend fun updateNote(data: Map<String, Any?>) {
        val note = Note(
            id = data["id"]?.toString() ?: "",
            chapterId = data["chapterId"]?.toString() ?: "",
            title = data["title"]?.toString() ?: "",
            content = data["content"]?.toString() ?: "",
            type = when (data["type"]?.toString()) {
                "HIGHLIGHT" -> NoteType.HIGHLIGHT
                "GEMMA_EXPLANATION" -> NoteType.GEMMA_EXPLANATION
                else -> NoteType.USER_NOTE
            }
        )
        database.noteDao().updateNote(note)
    }
    
    private suspend fun updateChatMessage(data: Map<String, Any?>) {
        val message = ChatMessage(
            id = data["id"]?.toString() ?: "",
            sessionId = data["sessionId"]?.toString() ?: "default_session",
            content = data["content"]?.toString() ?: "",
            isFromUser = data["isFromUser"]?.toString()?.toBoolean() ?: false,
            timestamp = data["timestamp"]?.toString()?.toLongOrNull() ?: System.currentTimeMillis(),
            messageType = when (data["messageType"]?.toString()) {
                "IMAGE" -> MessageType.IMAGE
                "VOICE" -> MessageType.VOICE
                else -> MessageType.TEXT
            },
            attachmentPath = data["attachmentPath"]?.toString()
        )
        database.chatDao().updateMessage(message)
    }
    
    private suspend fun updateUserResponse(data: Map<String, Any?>) {
        val response = UserResponse(
            id = data["id"]?.toString()?.toLongOrNull() ?: 0L,
            chapterId = data["chapterId"]?.toString() ?: "",
            exerciseIndex = data["exerciseIndex"]?.toString()?.toIntOrNull() ?: 0,
            textResponse = data["textResponse"]?.toString(),
            imageUri = data["imageUri"]?.toString()
        )
        database.userResponseDao().updateResponse(response)
    }
    
    private suspend fun updateChapterStats(data: Map<String, Any?>) {
        val stats = ChapterStats(
            id = data["id"]?.toString()?.toLongOrNull() ?: 0L,
            chapterId = data["chapterId"]?.toString() ?: "",
            userId = data["userId"]?.toString() ?: "default_user",
            visitCount = data["visitCount"]?.toString()?.toIntOrNull() ?: 0,
            noteCount = data["noteCount"]?.toString()?.toIntOrNull() ?: 0,
            revisionHistory = emptyList(), // Keep existing or empty for now
            chatHistory = emptyList(), // Keep existing or empty for now
            lastVisited = data["lastVisited"]?.toString()?.toLongOrNull() ?: System.currentTimeMillis()
        )
        database.chapterStatsDao().insertOrUpdate(stats)
    }
    
    private suspend fun updateUserProfile(data: Map<String, Any?>) {
        val profile = UserProfile(
            userId = data["userId"]?.toString() ?: "",
            hasUnlockedPersonalizedQuests = data["hasUnlockedPersonalizedQuests"]?.toString()?.toBoolean() ?: false,
            createdAt = data["createdAt"]?.toString()?.toLongOrNull() ?: System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        database.userProfileDao().insertOrUpdate(profile)
    }
}
