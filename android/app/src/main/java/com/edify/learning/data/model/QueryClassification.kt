package com.edify.learning.data.model

/**
 * Data class representing the result of query classification
 */
data class QueryClassification(
    val type: String, // "simple_query" or "complex_query"
    val response: String? // Direct response for simple queries, null for complex queries
)
