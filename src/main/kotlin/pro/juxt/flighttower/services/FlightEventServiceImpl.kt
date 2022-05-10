package pro.juxt.flighttower.services

import com.mongodb.client.result.UpdateResult
import org.springframework.stereotype.Service
import pro.juxt.flighttower.models.DeleteEvent
import pro.juxt.flighttower.repository.FlightEventRepository
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.models.FlightStatus
import pro.juxt.flighttower.models.StatusRequest
import pro.juxt.flighttower.utils.DevData

@Service
class FlightEventServiceImpl(private val flightEventRepository: FlightEventRepository) : FlightEventService {

    override fun recordNewEvent(flightEvent: FlightEvent) : Boolean {
        return flightEventRepository.upsert(flightEvent).wasAcknowledged()
    }

    override fun updateEvent(flightEvent: FlightEvent) : UpdateResult {
        return flightEventRepository.upsert(flightEvent)
    }

    override fun deleteEvent(deleteEvent: DeleteEvent) : Long {
        return flightEventRepository.deleteByPlaneIdAndTimestamp(
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

    override fun runDevData() {
        DevData.devDataSet.forEach { flightEventRepository.upsert(it) }
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