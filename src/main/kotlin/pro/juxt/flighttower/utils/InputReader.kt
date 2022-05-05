package pro.juxt.flighttower.utils

import org.springframework.stereotype.Component
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.services.FlightEventService
import java.time.LocalDateTime

@Component
class InputReader(val flightEventService: FlightEventService) {

    fun readEvent() {
        val input = readln()
        val segments = input.split(" ")
        val event = FlightEvent(segments[0], segments[1], segments[2], segments[3],
            segments[4], LocalDateTime.parse(segments[5]), segments[6].toInt())
        flightEventService.recordNewEvent(event)
    }

    fun setInputMode() {
        when (readln()) {
            "1" -> readEvent()
            "2" -> TODO("handle update" )
            "3" -> TODO("handle delete" )
            "4" -> TODO("handle get info" )
            else -> {
                println("input not recognised.")
                setInputMode()
            }
        }
    }


}