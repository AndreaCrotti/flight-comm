package pro.juxt.flighttower.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pro.juxt.flighttower.models.FlightEvent

interface FlightEventRepository : MongoRepository<FlightEvent, String> {



}