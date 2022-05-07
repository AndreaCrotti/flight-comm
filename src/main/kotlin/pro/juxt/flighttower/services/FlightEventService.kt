package pro.juxt.flighttower.services

import pro.juxt.flighttower.models.DeleteEvent
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.models.StatusRequest

interface FlightEventService {

    fun recordNewEvent(flightEvent: FlightEvent)

    fun updateEvent(flightEvent: FlightEvent)

    fun deleteEvent(deleteEvent: DeleteEvent)

    fun getStatusAt(statusRequest: StatusRequest)

}