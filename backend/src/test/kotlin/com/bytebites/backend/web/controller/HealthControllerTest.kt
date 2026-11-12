package com.bytebites.backend.web.controller

import com.bytebites.backend.domain.model.HealthStatus
import com.bytebites.backend.domain.provider.HealthStatusProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HealthControllerTest {

    private val healthStatusProvider: HealthStatusProvider = mockk()
    private val controller = HealthController(healthStatusProvider)

    @Test
    fun `return health status UP when checking application health`() = runTest {
        val expectedStatus = HealthStatus(
            status = "UP",
            timestamp = "2026-03-27T10:00:00Z",
            service = "bytebites-backend"
        )
        coEvery { healthStatusProvider.getHealthStatus() } returns expectedStatus

        val actualStatus = controller.checkHealth()

        assertEquals(expectedStatus, actualStatus)
        coVerify(exactly = 1) { healthStatusProvider.getHealthStatus() }
    }

}
