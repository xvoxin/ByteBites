package com.bytebites.backend.web.controller

import com.bytebites.backend.domain.model.HealthStatus
import com.bytebites.backend.domain.provider.HealthStatusProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthController(
    private val healthStatusProvider: HealthStatusProvider
) {

    @GetMapping
    suspend fun checkHealth(): HealthStatus =
        healthStatusProvider.getHealthStatus()

}
