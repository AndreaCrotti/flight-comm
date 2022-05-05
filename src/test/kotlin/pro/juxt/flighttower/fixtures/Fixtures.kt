package pro.juxt.flighttower.fixtures

import pro.juxt.flighttower.models.FlightEvent
import java.time.LocalDateTime

object Fixtures {

    fun stubEvent() : FlightEvent {
        return FlightEvent("F123", "747", "Paris", "Berlin", "Landing",
            LocalDateTime.of(2022, 5, 4, 13, 30), 150)
    }

}