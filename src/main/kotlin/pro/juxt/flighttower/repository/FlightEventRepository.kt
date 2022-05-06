package pro.juxt.flighttower.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pro.juxt.flighttower.models.FlightEvent
import java.time.LocalDateTime

@Repository
interface FlightEventRepository : MongoRepository<FlightEvent, String> , CustomRepository {

    fun deleteByPlaneIdAndTimestamp(planeId : String, timestamp: LocalDateTime) : Boolean

}