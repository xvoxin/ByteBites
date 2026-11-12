package com.bytebites.backend.domain.provider

import com.bytebites.backend.domain.model.HealthStatus

interface HealthStatusProvider {

    suspend fun getHealthStatus(): HealthStatus

}
