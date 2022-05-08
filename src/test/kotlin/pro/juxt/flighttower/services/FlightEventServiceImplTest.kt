package pro.juxt.flighttower.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pro.juxt.flighttower.helpers.Fixtures.stubDataSet
import pro.juxt.flighttower.helpers.Fixtures.stubDateTime
import pro.juxt.flighttower.helpers.Fixtures.stubDeleteEvent
import pro.juxt.flighttower.helpers.Fixtures.stubEvent
import pro.juxt.flighttower.helpers.Fixtures.stubEvent1
import pro.juxt.flighttower.helpers.Fixtures.stubEvent2
import pro.juxt.flighttower.helpers.Fixtures.stubEvent3
import pro.juxt.flighttower.helpers.Fixtures.stubEvent4
import pro.juxt.flighttower.helpers.Fixtures.stubEvent6
import pro.juxt.flighttower.helpers.Fixtures.stubEvent7
import pro.juxt.flighttower.helpers.Fixtures.stubEvent9
import pro.juxt.flighttower.models.FlightStatus
import pro.juxt.flighttower.models.StatusRequest
import pro.juxt.flighttower.repository.FlightEventRepository

internal class FlightEventServiceImplTest {

    private val mockRepository = mockk<FlightEventRepository>()
    private val stubFlightEvent = stubEvent()
    private val timestamp = stubDateTime(5, 17, 0)
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
        every { mockRepository.findAllByTimestampLessThanEqual(timestamp) } returns listOf(stubFlightEvent)
        flightEventService.getStatusAt(StatusRequest(timestamp))
        verify(exactly = 1) { mockRepository.findAllByTimestampLessThanEqual(timestamp) }
    }

    @Test
    fun get_status_converts_flight_event_to_flight_status() {
        every { mockRepository.findAllByTimestampLessThanEqual(timestamp) } returns listOf(stubEvent3, stubEvent6, stubEvent9)
        val result = flightEventService.getStatusAt(StatusRequest(timestamp))

        val expectedStatus3 = FlightStatus(stubEvent3.planeId, "In-Flight", stubEvent3.fuelDelta)
        val expectedStatus6 = FlightStatus(stubEvent6.planeId, "Landed", stubEvent6.fuelDelta)
        val expectedStatus9 = FlightStatus(stubEvent9.planeId, "Landed", stubEvent9.fuelDelta)

        assertEquals(listOf(expectedStatus3, expectedStatus6, expectedStatus9), result)
    }

    @Test
    fun get_status_tallies_fuel_deltas_for_flight() {
        every { mockRepository.findAllByTimestampLessThanEqual(timestamp) } returns listOf(stubEvent1, stubEvent2, stubEvent3)
        val result = flightEventService.getStatusAt(StatusRequest(timestamp))
        val expectedStatus = listOf(FlightStatus(stubEvent1.planeId, "In-Flight", 500))
        assertEquals(expectedStatus, result)
    }

    @Test
    fun get_status_tallies_fuel_deltas_for_multiple_flight() {
        every { mockRepository.findAllByTimestampLessThanEqual(timestamp) } returns stubDataSet
        val result = flightEventService.getStatusAt(StatusRequest(timestamp))
        val expectedStatus = listOf(
            FlightStatus(stubEvent1.planeId, "In-Flight", 500),
            FlightStatus(stubEvent4.planeId, "Landed", 300),
            FlightStatus(stubEvent7.planeId, "Landed", 225),
        )
        assertEquals(expectedStatus, result)
    }

}