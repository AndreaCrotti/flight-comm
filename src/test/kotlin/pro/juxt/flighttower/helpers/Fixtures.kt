package pro.juxt.flighttower.helpers

import pro.juxt.flighttower.models.DeleteEvent
import pro.juxt.flighttower.models.FlightEvent
import java.time.LocalDateTime

object Fixtures {

    const val eventString = "F123 747 PARIS BERLIN Land 2022-05-04T13:30:00 150"

    fun stubEvent(
        planeId: String = "F123",
        model: String = "747",
        origin: String = "PARIS",
        destination: String = "BERLIN",
        eventType: String = "Land",
        dateTime: LocalDateTime = stubDateTime(),
        fuelDelta: Int = 150
    ) : FlightEvent {

        return FlightEvent(planeId, model, origin, destination, eventType, dateTime, fuelDelta)

    }

    fun stubDateTime(
        day : Int = 4,
        hour : Int = 13,
        minute : Int = 30
    ) : LocalDateTime = LocalDateTime.of(2022, 5, day, hour, minute)

    val stubDeleteEvent = DeleteEvent("F123", stubDateTime())

}