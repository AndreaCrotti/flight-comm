package pro.juxt.flighttower.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pro.juxt.flighttower.helpers.Fixtures.stubEvent
import pro.juxt.flighttower.repository.FlightEventRepository

internal class FlightEventServiceImplTest {

    private val mockRepository = mockk<FlightEventRepository>()
    private val stubFlightEvent = stubEvent()

    @Test
    fun record_new_event_saves_to_repository() {
        val flightEventService = FlightEventServiceImpl(mockRepository)
        every { mockRepository.save(stubFlightEvent) } returns stubFlightEvent
        flightEventService.recordNewEvent(stubFlightEvent)
        verify(exactly = 1) { mockRepository.save(stubFlightEvent) }
    }

    @Test
    fun update_event_updates_repository() {
        val flightEventService = FlightEventServiceImpl(mockRepository)
        every { mockRepository.upsert(stubFlightEvent) } returns true
        flightEventService.updateEvent(stubFlightEvent)
        verify(exactly = 1) { mockRepository.upsert(stubFlightEvent) }
    }

}