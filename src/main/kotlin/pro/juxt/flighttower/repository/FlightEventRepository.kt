package pro.juxt.flighttower.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pro.juxt.flighttower.models.FlightEvent

@Repository
interface FlightEventRepository : MongoRepository<FlightEvent, String> {

}