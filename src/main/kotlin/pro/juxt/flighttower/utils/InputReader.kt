package pro.juxt.flighttower.utils

import org.springframework.stereotype.Component
import pro.juxt.flighttower.services.FlightEventService

@Component
class InputReader(val flightEventService: FlightEventService) {

    fun readEvent() {
        val input = readln()
        println(input)
    }


}