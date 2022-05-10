package pro.juxt.flighttower.fixtures

import pro.juxt.flighttower.models.DeleteEvent
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.models.FlightStatus
import pro.juxt.flighttower.models.StatusRequest
import java.time.LocalDateTime




object Fixtures {

    private const val LAND = "Land"
    private const val RE_FUEL = "Re-Fuel"
    private const val TAKE_OFF = "Take-Off"

    const val eventString = "F123 747 PARIS BERLIN Land 2022-05-04T13:30:00 150"
    const val deleteString = "F123 2022-05-04T13:30:00"
    const val getStatusString = "2022-05-04T13:30:00"

    const val eventStringInvalidDate = "F123 747 PARIS BERLIN Land 2022-05-04T13:xx:00 150"
    const val eventStringInvalidNumber = "F123 747 PARIS BERLIN Land 2022-05-04T13:30:00 xx"
    const val eventStringInvalidEvent = "F123 747 PARIS BERLIN Nothing 2022-05-04T13:30:00 150"
    const val eventStringInvalidSegments = "F123 747 PARIS BERLIN 2022-05-04T13:30:00 150"
    const val deleteStringInvalid = "F123 2022-05-04T13:30:00 123"

    /**
        Flight from London to Miami - End Status : Take-Off , Fuel 500
     */
    val stubEvent1 = stubEvent(planeId = "F101", origin = "LONDON", destination = "MIAMI",
                        eventType = LAND, timestamp = stubDateTime(hour = 7, minute = 0), fuelDelta = 0)
    val stubEvent2 = stubEvent(planeId = "F101", origin = "LONDON", destination = "MIAMI",
                        eventType = RE_FUEL, timestamp = stubDateTime(hour = 10, minute = 0), fuelDelta = 500)
    val stubEvent3 = stubEvent(planeId = "F101", origin = "LONDON", destination = "MIAMI",
                        eventType = TAKE_OFF, timestamp = stubDateTime(hour = 13, minute = 0), fuelDelta = 0)

    /**
        Flight from Reykjavik to Amsterdam - End Status : Land , Fuel 300
     */
    val stubEvent4 = stubEvent(planeId = "F102", origin = "REYKJAVIK", destination = "AMSTERDAM",
                        eventType = RE_FUEL, timestamp = stubDateTime(hour = 8, minute = 0), fuelDelta = 450)
    val stubEvent5 = stubEvent(planeId = "F102", origin = "REYKJAVIK", destination = "AMSTERDAM",
                        eventType = TAKE_OFF, timestamp = stubDateTime(hour = 11, minute = 0), fuelDelta = 0)
    val stubEvent6 = stubEvent(planeId = "F102", origin = "REYKJAVIK", destination = "AMSTERDAM",
                        eventType = LAND, timestamp = stubDateTime(hour = 14, minute = 0), fuelDelta = -150)

    /**
     * Flight from Lisbon to Sydney - End Status : Land , Fuel 225
     */
    val stubEvent7 = stubEvent(planeId = "F103", origin = "LISBON", destination = "SYDNEY",
                        eventType = RE_FUEL, timestamp = stubDateTime(hour = 9, minute = 0), fuelDelta = 800)
    val stubEvent8 = stubEvent(planeId = "F103", origin = "LISBON", destination = "SYDNEY",
                        eventType = TAKE_OFF, timestamp = stubDateTime(hour = 12, minute = 0), fuelDelta = 0)
    val stubEvent9 = stubEvent(planeId = "F103", origin = "LISBON", destination = "SYDNEY",
                        eventType = LAND, timestamp = stubDateTime(hour = 15, minute = 0), fuelDelta = -575)

    fun stubEvent(
        planeId: String = "F123",
        model: String = "747",
        origin: String = "PARIS",
        destination: String = "BERLIN",
        eventType: String = LAND,
        timestamp: LocalDateTime = stubDateTime(),
        fuelDelta: Int = 150
    ) : FlightEvent {
        return FlightEvent(planeId, model, origin, destination, eventType, timestamp, fuelDelta)
    }

    fun stubDateTime(
        day : Int = 4,
        hour : Int = 13,
        minute : Int = 30
    ) : LocalDateTime = LocalDateTime.of(2022, 5, day, hour, minute)

    val stubDeleteEvent = DeleteEvent("F123", stubDateTime())

    val stubStatusRequest = StatusRequest(stubDateTime())

    val stubFlightStatus = FlightStatus("F123", "In-Flight", 500)

    val stubFlightStatus1 = FlightStatus("F101", "In-Flight", 500)
    val stubFlightStatus2 = FlightStatus("F102", "Landed", 300)
    val stubFlightStatus3 = FlightStatus("F103", "Awaiting-Takeoff", 225)

    val stubFlightsStatusSet = listOf(stubFlightStatus1, stubFlightStatus2, stubFlightStatus3)


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

    val mockDataBaseError = java.lang.RuntimeException("Can't connect to DB")

}