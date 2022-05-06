package pro.juxt.flighttower.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.UpdateDefinition
import pro.juxt.flighttower.models.FlightEvent

private const val PLANE_ID = "planeId"
private const val TIMESTAMP = "timestamp"

private const val MODEL = "model"
private const val ORIGIN = "origin"
private const val DESTINATION = "destination"
private const val EVENT_TYPE = "eventType"
private const val FUEL_DELTA = "fuelDelta"


class CustomRepositoryImpl(private val mongoTemplate: MongoTemplate) : CustomRepository {

    override fun upsert(flightEvent: FlightEvent) : Boolean {

        val criteria: Criteria = Criteria.where(PLANE_ID).`is`(flightEvent.planeId)
            .and(TIMESTAMP).`is`(flightEvent.timestamp)

        val updateDefinition : UpdateDefinition = Update()
            .set(MODEL, flightEvent.model)
            .set(ORIGIN, flightEvent.origin)
            .set(DESTINATION, flightEvent.destination)
            .set(EVENT_TYPE, flightEvent.eventType)
            .set(FUEL_DELTA, flightEvent.fuelDelta)

        val updateResult = mongoTemplate.upsert(Query(criteria), updateDefinition, FlightEvent::class.java)
        return updateResult.wasAcknowledged()
    }

}