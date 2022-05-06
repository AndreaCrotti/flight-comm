package pro.juxt.flighttower.repository

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
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
        val result : List<FlightEvent> = flightEventRepository.findAll()
        assertEquals(1, result.size)
        assertEquals(stubFlightEvent, result.first())
    }

    @Test
    fun repository_updates_existing_flight_event() {
        flightEventRepository.save(stubFlightEvent)

        val updatedEvent = stubEvent(fuelDelta = 300)
        val acknowledgement = flightEventRepository.update(updatedEvent)
        val result : List<FlightEvent> = flightEventRepository.findAll()

        assertTrue(acknowledgement)
        assertEquals(1, result.size)
        assertEquals(updatedEvent, result.first())
    }

}