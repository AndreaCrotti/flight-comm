package pro.juxt.flighttower.services

import pro.juxt.flighttower.FlightEventRepository
import pro.juxt.flighttower.models.FlightEvent

class FlightEventServiceImpl(private val flightEventRepository: FlightEventRepository) : FlightEventService {

    override fun recordNewEvent(flightEvent: FlightEvent) {
        flightEventRepository.save(flightEvent)
    }

}