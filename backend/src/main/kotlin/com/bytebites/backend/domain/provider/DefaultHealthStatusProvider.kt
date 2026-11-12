package com.bytebites.backend.domain.provider

import com.bytebites.backend.domain.model.HealthStatus
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class DefaultHealthStatusProvider : HealthStatusProvider {

    override suspend fun getHealthStatus(): HealthStatus =
        HealthStatus(
            status = "UP",
            timestamp = Instant.now().toString(),
            service = "bytebites-backend"
        )

}
