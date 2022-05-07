package pro.juxt.flighttower.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pro.juxt.flighttower.helpers.Fixtures.stubDateTime
import pro.juxt.flighttower.helpers.Fixtures.stubDeleteEvent
import pro.juxt.flighttower.helpers.Fixtures.stubEvent
import pro.juxt.flighttower.models.StatusRequest
import pro.juxt.flighttower.repository.FlightEventRepository

internal class FlightEventServiceImplTest {

    private val mockRepository = mockk<FlightEventRepository>()
    private val stubFlightEvent = stubEvent()
    private val flightEventService = FlightEventServiceImpl(mockRepository)

    @Test
    fun record_new_event_saves_to_repository() {
        every { mockRepository.save(stubFlightEvent) } returns stubFlightEvent
        flightEventService.recordNewEvent(stubFlightEvent)
        verify(exactly = 1) { mockRepository.save(stubFlightEvent) }
    }

    @Test
    fun update_event_updates_repository() {
        every { mockRepository.upsert(stubFlightEvent) } returns true
        flightEventService.updateEvent(stubFlightEvent)
        verify(exactly = 1) { mockRepository.upsert(stubFlightEvent) }
    }

    @Test
    fun delete_event_calls_repository() {
        every { mockRepository.deleteByPlaneIdAndTimestamp(
            stubDeleteEvent.planeId, stubDeleteEvent.timestamp) } returns 0
        flightEventService.deleteEvent(stubDeleteEvent)
    }

    @Test
    fun get_status_calls_repository() {
        val timestamp = stubDateTime(5, 14, 30)
        every { mockRepository.findAllByTimestampLessThanEqual(timestamp) } returns listOf(stubFlightEvent)
        flightEventService.getStatusAt(StatusRequest(timestamp))
        verify(exactly = 1) { mockRepository.findAllByTimestampLessThanEqual(timestamp) }
    }

}