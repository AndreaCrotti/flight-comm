package pro.juxt.flighttower.repository

import com.mongodb.client.result.UpdateResult
import pro.juxt.flighttower.models.FlightEvent

interface CustomRepository {

    fun upsert(flightEvent: FlightEvent) : UpdateResult

}