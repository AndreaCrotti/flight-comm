package pro.juxt.flighttower.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent
import pro.juxt.flighttower.repository.FlightEventRepository

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

}