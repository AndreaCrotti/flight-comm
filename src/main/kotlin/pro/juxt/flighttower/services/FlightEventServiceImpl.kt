package pro.juxt.flighttower.services

import pro.juxt.flighttower.FlightEventRepository
import pro.juxt.flighttower.models.FlightEvent

class FlightEventServiceImpl(val flightEventRepository: FlightEventRepository) : FlightEventService {

    override fun recordNewEvent(flightEvent: FlightEvent): Void {
        TODO("Not yet implemented")
    }

}