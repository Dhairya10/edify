package com.edify.learning.data.util

import android.util.Log
import java.util.regex.Pattern

/**
 * Centralized utility for handling malformed JSON responses from Gemma LLM
 */
object JsonParsingHelper {
    
    private const val TAG = "JsonParsingHelper"
    
    /**
     * Extracts and cleans JSON from various malformed formats that Gemma produces
     */
    fun extractAndCleanJson(text: String, logPrefix: String = "JSON_PARSER"): String {
        var result = text.trim()
        Log.d(TAG, "$logPrefix: Starting with: '$result'")
        
        // Step 1: Extract from code blocks if present
        result = extractFromCodeBlocks(result, logPrefix)
        
        // Step 2: Remove common prefixes
        result = removeCommonPrefixes(result, logPrefix)
        
        // Step 3: Find JSON boundaries
        result = findJsonBoundaries(result, logPrefix)
        
        // Step 4: Basic validation - if it looks good, don't over-process
        if (isWellFormedJson(result)) {
            Log.d(TAG, "$logPrefix: JSON appears well-formed, skipping repair: '$result'")
            return result
        }
        
        // Step 5: Validate result
        if (!isValidJsonStructure(result)) {
            Log.w(TAG, "$logPrefix: Final result is not valid JSON: '$result'")
            return text // Return original if we can't fix it
        }
        
        Log.d(TAG, "$logPrefix: Successfully cleaned JSON: '$result'")
        return result
    }
    
    /**
     * Extract content from markdown code blocks
     */
    private fun extractFromCodeBlocks(text: String, logPrefix: String): String {
        if (!text.contains("```")) {
            return text
        }
        
        // Try multiple regex patterns for different code block formats
        val patterns = listOf(
            "```(?:json)?\\s*\\r?\\n?([\\s\\S]*?)\\r?\\n?```",  // Standard format
            "```([\\s\\S]*?)```",                               // Simple format
            "`([\\s\\S]*?)`"                                   // Single backticks
        )
        
        for (pattern in patterns) {
            val regex = Pattern.compile(pattern)
            val matcher = regex.matcher(text)
            
            if (matcher.find()) {
                val extracted = matcher.group(1)?.trim()
                if (!extracted.isNullOrEmpty()) {
                    Log.d(TAG, "$logPrefix: Extracted from code block: '$extracted'")
                    return extracted
                }
            }
        }
        
        Log.w(TAG, "$logPrefix: No valid content found in code blocks")
        return text
    }
    
    /**
     * Remove common malformed prefixes
     */
    private fun removeCommonPrefixes(text: String, logPrefix: String): String {
        var result = text.trim()
        
        // Remove "json" prefix
        if (result.startsWith("json{")) {
            result = result.substring(4)
            Log.d(TAG, "$logPrefix: Removed 'json' prefix: '$result'")
        }
        
        // Remove other common prefixes
        val prefixesToRemove = listOf("JSON:", "json:", "Response:", "```json", "```")
        for (prefix in prefixesToRemove) {
            if (result.startsWith(prefix)) {
                result = result.substring(prefix.length).trim()
                Log.d(TAG, "$logPrefix: Removed '$prefix' prefix: '$result'")
                break
            }
        }
        
        return result
    }
    
    /**
     * Find proper JSON boundaries
     */
    private fun findJsonBoundaries(text: String, logPrefix: String): String {
        var result = text.trim()
        
        // Find first opening brace
        if (!result.startsWith("{")) {
            val firstBrace = result.indexOf('{')
            if (firstBrace >= 0) {
                result = result.substring(firstBrace)
                Log.d(TAG, "$logPrefix: Found opening brace at position $firstBrace")
            }
        }
        
        // Find last closing brace
        if (!result.endsWith("}") && result.contains("}")) {
            val lastBrace = result.lastIndexOf('}')
            if (lastBrace >= 0) {
                result = result.substring(0, lastBrace + 1)
                Log.d(TAG, "$logPrefix: Found closing brace at position $lastBrace")
            }
        }
        
        return result
    }
    
    /**
     * Checks if JSON is already well-formed and doesn't need repair
     */
    private fun isWellFormedJson(jsonContent: String): Boolean {
        val trimmed = jsonContent.trim()
        
        // Must start and end with braces
        if (!trimmed.startsWith("{") || !trimmed.endsWith("}")) {
            return false
        }
        
        // Check for proper field-value structure with colons
        val hasProperColons = trimmed.contains("\"type\":")
        
        // Check that it doesn't have obvious malformations
        val hasObviousErrors = trimmed.contains("\" ") || // quotes followed by space (malformed)
                              trimmed.contains(" \"") || // space before quotes in wrong place
                              trimmed.matches(Regex(".*\\w+\\s+\".*")) // word followed by space and quote
        
        return hasProperColons && !hasObviousErrors
    }
    
    /**
     * Basic validation of JSON structure
     */
    private fun isValidJsonStructure(jsonContent: String): Boolean {
        val trimmed = jsonContent.trim()
        
        // Must start and end with braces
        if (!trimmed.startsWith("{") || !trimmed.endsWith("}")) {
            return false
        }
        
        // Must have some content between braces
        if (trimmed.length <= 2) {
            return false
        }
        
        // Should not start with malformed patterns
        if (trimmed.startsWith("\"{") || trimmed.startsWith("\"\"")) {
            return false
        }
        
        return true
    }
    
    /**
     * Validates if a classification response contains malformed JSON-like content
     */
    fun isClassificationResponseMalformed(response: String?): Boolean {
        if (response == null) return false
        
        val trimmed = response.trim()
        return trimmed.startsWith("{") || 
               trimmed.contains("\"type\"") || 
               trimmed.contains("simple_query") ||
               trimmed.contains("complex_query") ||
               trimmed.contains("```")
    }
    
    /**
     * Attempts to repair common JSON syntax errors
     */
    fun repairJsonSyntax(json: String, logPrefix: String = "JSON_REPAIR"): String {
        var result = json.trim()
        Log.d(TAG, "$logPrefix: Starting repair on: '$result'")
        
        // Fix the most common pattern: {  title " Quest a Solution -> { "title": "Quest a Solution"
        // This pattern matches field names without quotes and colons
        result = result.replace(Regex("\\{\\s+(\\w+)\\s+\""), "{ \"$1\": \"")
        result = result.replace(Regex(",\\s+(\\w+)\\s+\""), ", \"$1\": \"")
        
        // Fix patterns like 'title " Quest' -> 'title": "Quest'  
        result = result.replace(Regex("(\\w+)\\s+\""), "$1\": \"")
        
        // Fix missing quotes around field names that have colons
        result = result.replace(Regex("\\{\\s*(\\w+)\\s*:"), "{ \"$1\":")
        result = result.replace(Regex(",\\s*(\\w+)\\s*:"), ", \"$1\":")
        
        // Fix patterns like: "Text " Mandela -> "questionText": "Mandela
        result = result.replace(Regex("\"Text\"\\s+\""), "\"questionText\": \"")
        
        // Remove extra spaces before quotes in values
        result = result.replace(Regex(":\\s+\""), ": \"")
        
        // Ensure proper quote closure for values that end at braces or commas
        result = result.replace(Regex("([^\"\\}\\,])\\}"), "$1\"}")
        result = result.replace(Regex("([^\"\\,])\\,"), "$1\",")
        
        // Fix malformed structure like removing space before quotes in field names
        result = result.replace(Regex("\"\\s*([^\"]+)\\s*\"\\s*:"), "\"$1\":")
        
        Log.d(TAG, "$logPrefix: Repaired JSON: '$result'")
        return result
    }
    
    /**
     * Handles the specific "Expected BEGIN_OBJECT but was STRING" error
     * by attempting to reconstruct JSON from plain text responses
     */
    fun handlePlainTextResponse(text: String, logPrefix: String = "PLAIN_TEXT_HANDLER"): String {
        val trimmed = text.trim()
        Log.d(TAG, "$logPrefix: Attempting to handle plain text: '$trimmed'")
        
        // Common patterns when Gemma returns descriptive text instead of JSON
        
        // Pattern 1: "The type is simple_query and the response is..."
        val typeResponsePattern = Regex("type is (simple_query|complex_query).*?(?:response is (.+?))?(?:\\.|$)")
        val match = typeResponsePattern.find(trimmed)
        
        if (match != null) {
            val type = match.groupValues[1]
            val response = match.groupValues.getOrNull(2)?.trim()
            
            return if (response.isNullOrEmpty() || response == "null" || type == "complex_query") {
                "{\"type\": \"$type\", \"response\": null}"
            } else {
                "{\"type\": \"$type\", \"response\": \"$response\"}"
            }
        }
        
        // Pattern 2: Just classification without explicit structure
        when {
            trimmed.contains("simple_query") || trimmed.contains("simple query") -> {
                // Extract potential answer text
                val answerPattern = Regex("(?:answer|response).*?[:.]\\s*(.+?)(?:\\.|$)")
                val answerMatch = answerPattern.find(trimmed)
                val answer = answerMatch?.groupValues?.get(1)?.trim()
                
                return if (answer.isNullOrEmpty()) {
                    "{\"type\": \"simple_query\", \"response\": null}"
                } else {
                    "{\"type\": \"simple_query\", \"response\": \"$answer\"}"
                }
            }
            trimmed.contains("complex_query") || trimmed.contains("complex query") -> {
                return "{\"type\": \"complex_query\", \"response\": null}"
            }
            else -> {
                Log.w(TAG, "$logPrefix: Could not extract classification from text")
                throw IllegalArgumentException("Cannot extract JSON structure from: $trimmed")
            }
        }
    }
}