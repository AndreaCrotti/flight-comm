package pro.juxt.flighttower.services

import pro.juxt.flighttower.models.FlightEvent

interface FlightEventService {

    fun recordNewEvent(flightEvent: FlightEvent)

}