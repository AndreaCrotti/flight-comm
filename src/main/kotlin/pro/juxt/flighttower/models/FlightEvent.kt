package pro.juxt.flighttower.models

import org.valiktor.functions.isIn
import org.valiktor.validate
import java.time.LocalDateTime

data class FlightEvent(
    val planeId : String,
    val model : String,
    val origin : String,
    val destination : String,
    val eventType : String,
    val timestamp: LocalDateTime,
    val fuelDelta : Int
) {
    init {
        validate(this) {
            validate(FlightEvent::eventType).isIn("Land", "Take-Off", "Re-Fuel")
        }
    }
}
