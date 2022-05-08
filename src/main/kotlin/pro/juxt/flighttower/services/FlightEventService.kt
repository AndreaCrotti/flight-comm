package pro.juxt.flighttower.services

import com.mongodb.client.result.UpdateResult
import pro.juxt.flighttower.models.DeleteEvent
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.models.FlightStatus
import pro.juxt.flighttower.models.StatusRequest

interface FlightEventService {

    fun recordNewEvent(flightEvent: FlightEvent) : Boolean

    fun updateEvent(flightEvent: FlightEvent) : UpdateResult

    fun deleteEvent(deleteEvent: DeleteEvent) : Long

    fun getStatusAt(statusRequest: StatusRequest) : List<FlightStatus>

}