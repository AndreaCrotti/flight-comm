package pro.juxt.flighttower.repository

import org.springframework.data.mongodb.core.MongoTemplate
import pro.juxt.flighttower.models.FlightEvent

class CustomRepositoryImpl(val mongoTemplate: MongoTemplate) : CustomRepository {

    override fun update(flightEvent: FlightEvent) : Boolean {
        TODO()
    }

}