package pro.juxt.flighttower.utils

import org.springframework.stereotype.Component
import pro.juxt.flighttower.models.DeleteEvent
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.models.StatusRequest
import pro.juxt.flighttower.services.FlightEventService
import java.time.LocalDateTime
import kotlin.system.exitProcess

@Component
class InputReader(
    val printHelper : PrintHelper,
    val flightEventService: FlightEventService
) {

    fun startUp() {
        printHelper.printWelcome()
        setInputMode()
    }

    fun setInputMode() {
        printHelper.printModeSelection()
        when (readln()) {
            "1" -> readEvent()
            "2" -> updateEvent()
            "3" -> deleteEvent()
            "4" -> getStatus()
            else -> {
                println("input not recognised.")
                setInputMode()
            }
        }
    }

    fun checkInput(input : String, callback: () -> Unit) {
        when (input.lowercase()) {
            "switch" -> setInputMode()
            "exit" -> exitSystem()
            "help" -> {
                printHelper.printHelp()
                callback()
            }
        }
    }

    fun readEvent() {
        catchErrors {
            val input = readln()
            checkInput(input) { readEvent() }
            val flightEvent = toEvent(input)
            if (flightEventService.recordNewEvent(flightEvent)) {
                printHelper.printEventSaved()
            } else printHelper.printSaveFailed()
        }
        runAgain { readEvent() }
    }

    fun updateEvent() {
        catchErrors {
            val input = readln()
            checkInput(input) { updateEvent() }
            val updateEvent = toEvent(input)
            val updateResult = flightEventService.updateEvent(updateEvent)
            if (updateResult.wasAcknowledged()) {
                if (updateResult.matchedCount > 0L) {
                    printHelper.printUpdateSuccess(updateResult.modifiedCount)
                } else printHelper.printEventSaved()
            } else printHelper.printUpdateNotSuccess()
        }
        runAgain { updateEvent() }
    }

    fun deleteEvent() {
        catchErrors {
            val input = readln()
            checkInput(input) { deleteEvent() }
            val deleteEvent = toDeleteEvent(input)
            val deleteCount = flightEventService.deleteEvent(deleteEvent)
            if (deleteCount > 0) printHelper.printDelete(deleteCount)
            else printHelper.printDeleteUnsuccessful()
        }
        runAgain { deleteEvent() }
    }

    fun getStatus() {
        catchErrors {
            val input = readln()
            checkInput(input) { getStatus() }
            val statusRequest = toStatusRequest(input)
            val statusResult = flightEventService.getStatusAt(statusRequest)
            printHelper.printStatus(statusResult)
        }
        runAgain { getStatus() }
    }

    fun runAgain(function: () -> Unit ) {
        function()
    }

    fun catchErrors(function: () -> Unit) {
        try {
            function()
        } catch (exception : java.lang.Exception) {
            printHelper.printErrorConnectingToDb()
        }
    }

    fun exitSystem() {
        printHelper.printBye()
        exitProcess(0)
    }

    private fun toEvent(input: String) : FlightEvent {
        val segments = input.split(" ")
        // TODO add validation here on
        return FlightEvent(segments[0], segments[1], segments[2], segments[3],
            segments[4], LocalDateTime.parse(segments[5]), segments[6].toInt())
    }

    private fun toDeleteEvent(input: String) : DeleteEvent {
        val segments = input.split(" ")
        // TODO add validation here
        return DeleteEvent(segments[0], LocalDateTime.parse(segments[1]))
    }

    private fun toStatusRequest(input: String) : StatusRequest {
        // TODO add validation here
        return StatusRequest(LocalDateTime.parse(input))
    }

}