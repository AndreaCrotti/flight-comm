package pro.juxt.flighttower.services

import org.springframework.stereotype.Service
import pro.juxt.flighttower.repository.FlightEventRepository
import pro.juxt.flighttower.models.FlightEvent

@Service
class FlightEventServiceImpl(private val flightEventRepository: FlightEventRepository) : FlightEventService {

    override fun recordNewEvent(flightEvent: FlightEvent) {
        flightEventRepository.save(flightEvent)
    }

    override fun updateEvent(flightEvent: FlightEvent) {
        flightEventRepository.upsert(flightEvent)
    }

}