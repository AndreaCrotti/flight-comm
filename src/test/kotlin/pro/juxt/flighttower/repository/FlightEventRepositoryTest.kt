package pro.juxt.flighttower.repository

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import pro.juxt.flighttower.fixtures.Fixtures.stubDataSet
import pro.juxt.flighttower.fixtures.Fixtures.stubDateTime
import pro.juxt.flighttower.fixtures.Fixtures.stubDeleteEvent
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent1
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent2
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent4
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent5
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent7
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent8
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
        flightEventRepository.upsert(updatedEvent)
        val database : List<FlightEvent> = flightEventRepository.findAll()

        assertEquals(1, database.size)
        assertEquals(updatedEvent, database.first())
    }

    @Test
    fun upsert_inserts_new_event_if_not_found() {
        flightEventRepository.upsert(stubFlightEvent)
        val database : List<FlightEvent> = flightEventRepository.findAll()

        assertEquals(1, database.size)
        assertEquals(stubFlightEvent, database.first())
    }

    @Test
    fun upsert_returns_results_of_update_action() {
        flightEventRepository.save(stubFlightEvent)
        val updatedEvent = stubEvent(fuelDelta = 300)
        val updateResult = flightEventRepository.upsert(updatedEvent)

        assertTrue(updateResult.wasAcknowledged())
        assertEquals(1, updateResult.modifiedCount)
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

    @Test
    fun find_all_before_timestamp_returns_all_events_before_timestamp() {
        saveBunchOfStuff()
        val statusTime = stubDateTime(hour = 15, minute = 0)
        val result = flightEventRepository.findAllByTimestampLessThanEqual(statusTime)
        assertEquals(9, result.size)
        assertTrue(result.containsAll(stubDataSet))
    }

    @Test
    fun find_all_before_timestamp_does_not_return_events_after_timestamp() {
        saveBunchOfStuff()
        val statusTime = stubDateTime(hour = 12, minute = 0)
        val result = flightEventRepository.findAllByTimestampLessThanEqual(statusTime)
        val expected = listOf(stubEvent1, stubEvent2, stubEvent4, stubEvent5, stubEvent7, stubEvent8)
        assertEquals(6, result.size)
        assertTrue(result.containsAll(expected))
    }

    private fun saveBunchOfStuff() {
        stubDataSet.forEach { flightEventRepository.save(it) }
    }

}