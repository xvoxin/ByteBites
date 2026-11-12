package com.bytebites.backend.domain.model

data class HealthStatus(
    val status: String,
    val timestamp: String,
    val service: String
)
