package pro.juxt.flighttower.services

import org.springframework.stereotype.Service
import pro.juxt.flighttower.models.DeleteEvent
import pro.juxt.flighttower.repository.FlightEventRepository
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.models.FlightStatus
import pro.juxt.flighttower.models.StatusRequest

@Service
class FlightEventServiceImpl(private val flightEventRepository: FlightEventRepository) : FlightEventService {

    override fun recordNewEvent(flightEvent: FlightEvent) {
        flightEventRepository.save(flightEvent)
    }

    override fun updateEvent(flightEvent: FlightEvent) {
        flightEventRepository.upsert(flightEvent)
    }

    override fun deleteEvent(deleteEvent: DeleteEvent) {
        flightEventRepository.deleteByPlaneIdAndTimestamp(
            deleteEvent.planeId, deleteEvent.timestamp)
    }

    override fun getStatusAt(statusRequest: StatusRequest): List<FlightStatus> {
        val flightEvents = flightEventRepository.findAllByTimestampLessThanEqual(statusRequest.timestamp)
        return flightEvents.map { FlightStatus(it.planeId, it.eventType, it.fuelDelta) }
    }

}