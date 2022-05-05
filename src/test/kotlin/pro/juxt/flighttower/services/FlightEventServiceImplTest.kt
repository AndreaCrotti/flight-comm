package pro.juxt.flighttower.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pro.juxt.flighttower.repository.FlightEventRepository
import pro.juxt.flighttower.models.FlightEvent
import java.time.LocalDateTime

internal class FlightEventServiceImplTest {

    private val mockRepository = mockk<FlightEventRepository>()
    private val stubFlightEvent = stubEvent()

    @Test
    fun calls_repository() {
        val flightEventService = FlightEventServiceImpl(mockRepository)
        every { mockRepository.save(stubFlightEvent) } returns stubFlightEvent
        flightEventService.recordNewEvent(stubFlightEvent)
        verify(exactly = 1) { mockRepository.save(stubFlightEvent) }
    }

    private fun stubEvent() : FlightEvent {
        return FlightEvent("F123", "747", "Paris", "Berlin", "Landing",
            LocalDateTime.of(2022, 5, 4, 13, 30), 150)
    }

}