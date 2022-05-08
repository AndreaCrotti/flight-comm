package pro.juxt.flighttower.utils

import org.springframework.stereotype.Component
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.services.FlightEventService
import java.time.LocalDateTime

@Component
class InputReader(
    val printHelper : PrintHelper,
    val flightEventService: FlightEventService
) {

    fun startUp() {
        printHelper.printWelcome()
        printHelper.printModeSelection()
        setInputMode()
    }

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
        try {
            if (flightEventService.recordNewEvent(flightEvent)) {
                printHelper.printEventSaved()
            } else {
                printHelper.printSaveFailed()
            }
        } catch (exception : java.lang.Exception) {
            printHelper.printErrorConnectingToDb()
        }
    }

    fun updateEvent() {
        val updateEvent = toEvent(readln())
        try {
            val updateResult = flightEventService.updateEvent(updateEvent)
            if (updateResult.wasAcknowledged()) {
                if (updateResult.matchedCount > 0L) {
                    printHelper.printUpdateSuccess(updateResult.modifiedCount)
                } else {
                    printHelper.printEventSaved()
                }
            } else printHelper.printUpdateNotSuccess()
        } catch (exception : java.lang.Exception) {
            printHelper.printErrorConnectingToDb()
        }
    }

    private fun toEvent(input: String) : FlightEvent{
        val segments = input.split(" ")
        return FlightEvent(segments[0], segments[1], segments[2], segments[3],
            segments[4], LocalDateTime.parse(segments[5]), segments[6].toInt())
    }


}