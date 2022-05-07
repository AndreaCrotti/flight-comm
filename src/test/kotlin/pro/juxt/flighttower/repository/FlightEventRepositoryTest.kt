package pro.juxt.flighttower.repository

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import pro.juxt.flighttower.helpers.Fixtures.stubDateTime
import pro.juxt.flighttower.helpers.Fixtures.stubDeleteEvent
import pro.juxt.flighttower.helpers.Fixtures.stubEvent
import pro.juxt.flighttower.helpers.MockBeanConfiguration
import pro.juxt.flighttower.models.FlightEvent


@DataMongoTest
@Import(MockBeanConfiguration::class)
class FlightEventRepositoryTest @Autowired constructor (
    private val flightEventRepository : FlightEventRepository,
){

    private val stubFlightEvent = stubEvent()

    @AfterEach
    fun clearData() {
        flightEventRepository.deleteAll()
    }

    @Test
    fun repository_saves_flight_event() {
        flightEventRepository.save(stubFlightEvent)
        val database : List<FlightEvent> = flightEventRepository.findAll()
        assertEquals(1, database.size)
        assertEquals(stubFlightEvent, database.first())
    }

    @Test
    fun upsert_updates_existing_flight_event() {
        flightEventRepository.save(stubFlightEvent)

        val updatedEvent = stubEvent(fuelDelta = 300)
        val acknowledgement = flightEventRepository.upsert(updatedEvent)
        val database : List<FlightEvent> = flightEventRepository.findAll()

        assertTrue(acknowledgement)
        assertEquals(1, database.size)
        assertEquals(updatedEvent, database.first())
    }

    @Test
    fun upsert_inserts_new_event_if_not_found() {
        val acknowledgement = flightEventRepository.upsert(stubFlightEvent)
        val database : List<FlightEvent> = flightEventRepository.findAll()

        assertTrue(acknowledgement)
        assertEquals(1, database.size)
        assertEquals(stubFlightEvent, database.first())
    }

    @Test
    fun delete_removes_event_from_repository() {
        flightEventRepository.save(stubFlightEvent)

        val deleteCount = flightEventRepository.deleteByPlaneIdAndTimestamp(
            stubDeleteEvent.planeId, stubDeleteEvent.timestamp)
        val database : List<FlightEvent> = flightEventRepository.findAll()

        assertEquals(1, deleteCount)
        assertEquals(0, database.size)
    }

    @Test
    fun delete_only_removes_target_event_from_repository() {
        flightEventRepository.save(stubFlightEvent)
        flightEventRepository.save(stubEvent(timestamp = stubDateTime(hour = 11, minute = 15)))
        flightEventRepository.save(stubEvent(timestamp = stubDateTime(hour = 8, minute = 50)))
        flightEventRepository.save(stubEvent(planeId = "F456"))
        flightEventRepository.save(stubEvent(planeId = "F789"))

        val deleteCount = flightEventRepository.deleteByPlaneIdAndTimestamp(
            stubDeleteEvent.planeId, stubDeleteEvent.timestamp)
        val database : List<FlightEvent> = flightEventRepository.findAll()

        assertEquals(1, deleteCount)
        assertEquals(4, database.size)
    }

}