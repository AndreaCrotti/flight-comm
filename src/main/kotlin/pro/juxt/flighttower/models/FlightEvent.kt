package pro.juxt.flighttower.models

import java.time.LocalDateTime

data class FlightEvent(
    val planeId : String,
    val model : String,
    val origin : String,
    val destination : String,
    val eventType : String,
    val timestamp: LocalDateTime,
    val fuelDelta : Int
)
