package com.bytebites.backend.domain.provider

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DefaultHealthStatusProviderTest {

    private val provider = DefaultHealthStatusProvider()

    @Test
    fun `provide valid UP health status with timestamp and service name`() = runTest {
        val healthStatus = provider.getHealthStatus()

        assertEquals("UP", healthStatus.status)
        assertEquals("bytebites-backend", healthStatus.service)
        assertNotNull(healthStatus.timestamp)
    }

}
