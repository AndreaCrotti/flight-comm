package pro.juxt.flighttower.utils

import org.springframework.stereotype.Component
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.services.FlightEventService
import java.time.LocalDateTime

@Component
class InputReader(val flightEventService: FlightEventService) {

    fun setInputMode() {
        when (readln()) {
            "1" -> readEvent()
            "2" -> updateEvent()
            "3" -> TODO("handle delete" )
            "4" -> TODO("handle get info" )
            else -> {
                println("input not recognised.")
                setInputMode()
            }
        }
    }

    fun readEvent() {
        val flightEvent = toEvent(readln())
        flightEventService.recordNewEvent(flightEvent)
    }

    fun updateEvent() {
        val updateEvent = toEvent(readln())
        flightEventService.updateEvent(updateEvent)
    }

    private fun toEvent(input: String) : FlightEvent{
        val segments = input.split(" ")
        return FlightEvent(segments[0], segments[1], segments[2], segments[3],
            segments[4], LocalDateTime.parse(segments[5]), segments[6].toInt())
    }


}