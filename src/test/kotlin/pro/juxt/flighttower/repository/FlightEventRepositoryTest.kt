package pro.juxt.flighttower.repository

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent
import pro.juxt.flighttower.models.FlightEvent

@SpringBootTest
class FlightEventRepositoryTest @Autowired constructor (
    private val flightEventRepository : FlightEventRepository
){

    private val stubFlightEvent = stubEvent()

    // TODO remove once initial run-in removed
    @BeforeEach
    fun setup() {
        flightEventRepository.deleteAll()
    }

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

}