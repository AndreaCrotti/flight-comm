package pro.juxt.flighttower.fixtures

import pro.juxt.flighttower.models.FlightEvent
import java.time.LocalDateTime

object Fixtures {

    val eventString = "F123 747 PARIS BERLIN Land 2022-05-04T13:30:00 150"

    fun stubEvent() : FlightEvent {
        return FlightEvent("F123", "747", "PARIS", "BERLIN", "Land",
            LocalDateTime.of(2022, 5, 4, 13, 30), 150)
    }

}