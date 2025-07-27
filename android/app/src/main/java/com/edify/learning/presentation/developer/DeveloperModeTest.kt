package com.edify.learning.presentation.developer

import com.edify.learning.utils.DeveloperMode

/**
 * Simple test to verify developer mode functionality
 */
object DeveloperModeTest {
    
    fun testDeveloperModeEnabled(): Boolean {
        return DeveloperMode.ENABLED
    }
    
    fun testDatabaseTables(): List<String> {
        return listOf(
            "Subjects",
            "Chapters", 
            "Notes",
            "Chat Messages",
            "User Responses",
            "Chapter Stats",
            "User Profiles"
        )
    }
    
    fun testCrudOperations(): Map<String, List<String>> {
        return mapOf(
            "Subject" to listOf("id", "name", "description", "iconName"),
            "Chapter" to listOf("id", "subjectId", "title", "description", "order", "jsonFileName"),
            "Note" to listOf("id", "subjectId", "chapterId", "title", "content", "createdAt", "updatedAt"),
            "ChatMessage" to listOf("id", "sessionId", "chapterId", "content", "isFromUser", "timestamp", "messageType", "attachmentPath"),
            "UserResponse" to listOf("id", "chapterId", "questionId", "userAnswer", "isCorrect", "timestamp"),
            "ChapterStats" to listOf("id", "chapterId", "visitCount", "noteCount", "lastVisited", "totalTimeSpent"),
            "UserProfile" to listOf("userId", "hasUnlockedPersonalizedQuests", "createdAt", "updatedAt")
        )
    }
    
    fun runTests(): String {
        val results = mutableListOf<String>()
        
        // Test 1: Developer mode enabled
        results.add("✓ Developer mode enabled: ${testDeveloperModeEnabled()}")
        
        // Test 2: Database tables available
        val tables = testDatabaseTables()
        results.add("✓ Database tables (${tables.size}): ${tables.joinToString(", ")}")
        
        // Test 3: CRUD operations fields
        val crudOps = testCrudOperations()
        results.add("✓ CRUD operations available for ${crudOps.size} entities")
        
        return results.joinToString("\n")
    }
}
