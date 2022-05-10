package pro.juxt.flighttower.utils

import pro.juxt.flighttower.models.FlightEvent
import java.time.LocalDate
import java.time.LocalDateTime

object DevData {

    private const val LAND = "Land"
    private const val RE_FUEL = "Re-Fuel"
    private const val TAKE_OFF = "Take-Off"

    /**
    Flight from London to Miami - End Status : Take-Off , Fuel 500
     */
    private val stubEvent1 = stubEvent(planeId = "F101", origin = "LONDON", destination = "MIAMI",
        eventType = LAND, timestamp = stubDateTime(hour = 7, minute = 0), fuelDelta = 0)
    private val stubEvent2 = stubEvent(planeId = "F101", origin = "LONDON", destination = "MIAMI",
        eventType = RE_FUEL, timestamp = stubDateTime(hour = 10, minute = 0), fuelDelta = 500)
    private val stubEvent3 = stubEvent(planeId = "F101", origin = "LONDON", destination = "MIAMI",
        eventType = TAKE_OFF, timestamp = stubDateTime(hour = 13, minute = 0), fuelDelta = 0)

    /**
    Flight from Reykjavik to Amsterdam - End Status : Land , Fuel 300
     */
    private val stubEvent4 = stubEvent(planeId = "F102", origin = "REYKJAVIK", destination = "AMSTERDAM",
        eventType = RE_FUEL, timestamp = stubDateTime(hour = 8, minute = 0), fuelDelta = 450)
    private val stubEvent5 = stubEvent(planeId = "F102", origin = "REYKJAVIK", destination = "AMSTERDAM",
        eventType = TAKE_OFF, timestamp = stubDateTime(hour = 11, minute = 0), fuelDelta = 0)
    private val stubEvent6 = stubEvent(planeId = "F102", origin = "REYKJAVIK", destination = "AMSTERDAM",
        eventType = LAND, timestamp = stubDateTime(hour = 14, minute = 0), fuelDelta = -150)

    /**
     * Flight from Lisbon to Sydney - End Status : Land , Fuel 225
     */
    private val stubEvent7 = stubEvent(planeId = "F103", origin = "LISBON", destination = "SYDNEY",
        eventType = RE_FUEL, timestamp = stubDateTime(hour = 9, minute = 0), fuelDelta = 800)
    private val stubEvent8 = stubEvent(planeId = "F103", origin = "LISBON", destination = "SYDNEY",
        eventType = TAKE_OFF, timestamp = stubDateTime(hour = 12, minute = 0), fuelDelta = 0)
    private val stubEvent9 = stubEvent(planeId = "F103", origin = "LISBON", destination = "SYDNEY",
        eventType = LAND, timestamp = stubDateTime(hour = 15, minute = 0), fuelDelta = -575)

    private fun stubEvent(
        planeId: String,
        model: String = "747",
        origin: String,
        destination: String,
        eventType: String,
        timestamp: LocalDateTime,
        fuelDelta: Int
    ) : FlightEvent {
        return FlightEvent(planeId, model, origin, destination, eventType, timestamp, fuelDelta)
    }

    private fun stubDateTime(hour : Int, minute : Int) : LocalDateTime {
        val today = LocalDate.now()
        return LocalDateTime.of(today.year, today.month, today.dayOfMonth, hour, minute)
    }

    val stubDataSet : List<FlightEvent> = listOf(
        stubEvent1,
        stubEvent2,
        stubEvent3,
        stubEvent4,
        stubEvent5,
        stubEvent6,
        stubEvent7,
        stubEvent8,
        stubEvent9
    )

}