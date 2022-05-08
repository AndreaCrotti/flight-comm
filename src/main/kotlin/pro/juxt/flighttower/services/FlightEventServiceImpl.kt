package pro.juxt.flighttower.services

import org.springframework.stereotype.Service
import pro.juxt.flighttower.models.DeleteEvent
import pro.juxt.flighttower.repository.FlightEventRepository
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.models.FlightStatus
import pro.juxt.flighttower.models.StatusRequest

@Service
class FlightEventServiceImpl(private val flightEventRepository: FlightEventRepository) : FlightEventService {

    override fun recordNewEvent(flightEvent: FlightEvent) : Boolean {
        return try {
            val savedEvent = flightEventRepository.save(flightEvent)
            flightEvent == savedEvent
        } catch (exception: Exception) {
            false
        }
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
        val groupedEvents = flightEvents.groupBy { it.planeId }
        val talliedEvents = groupedEvents.mapValues { entry ->
            entry.value.sortedByDescending { it.timestamp }
                .foldRight(Pair("No Data", 0)) { flightEvent, pair ->
                    Pair(flightEvent.eventType, flightEvent.fuelDelta + pair.second)
            }
        }
        return talliedEvents.map { FlightStatus(it.key, getStatus(it.value.first), it.value.second) }
    }

    private fun getStatus(eventType : String) : String {
        return when (eventType) {
            "Land" -> "Landed"
            "Re-Fuel" -> "Awaiting-Takeoff"
            "Take-Off" -> "In-Flight"
            else -> eventType
        }
    }

}