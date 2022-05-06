package pro.juxt.flighttower.repository

import pro.juxt.flighttower.models.FlightEvent

interface CustomRepository {

    fun update(flightEvent: FlightEvent) : Boolean

}