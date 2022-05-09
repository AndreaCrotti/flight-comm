package pro.juxt.flighttower.utils

import org.springframework.stereotype.Component
import org.valiktor.ConstraintViolationException
import pro.juxt.flighttower.models.DeleteEvent
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.models.StatusRequest
import pro.juxt.flighttower.services.FlightEventService
import java.time.LocalDateTime
import java.time.format.DateTimeParseException
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
            "1" -> readEventWrapper()
            "2" -> updateEventWrapper()
            "3" -> deleteEventWrapper()
            "4" -> getStatusWrapper()
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

    fun readEventWrapper() {
        printHelper.printReadMode()
        printHelper.eventFormat()
        readEvent()
    }

    fun updateEventWrapper() {
        printHelper.printUpdateMode()
        printHelper.eventFormat()
        updateEvent()
    }

    fun deleteEventWrapper() {
        printHelper.printDeleteMode()
        printHelper.deleteFormat()
        deleteEvent()
    }

    fun getStatusWrapper() {
        printHelper.printGetStatusMode()
        printHelper.timestampFormat()
        getStatus()
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
        } catch (numberException : NumberFormatException) {
            printHelper.invalidNumber()
        } catch (illegalArgument : IllegalArgumentException) {
            handleIllegalArgument(illegalArgument)
        } catch (dateTimeException : DateTimeParseException) {
            printHelper.invalidDateInput()
            printHelper.timestampFormat()
        } catch (constraintViolation : ConstraintViolationException) {
            printHelper.invalidEventInput()
            printHelper.eventFormat()
        } catch (exception : Exception) {
            printHelper.printErrorConnectingToDb()
        }
    }

    private fun handleIllegalArgument(exception: IllegalArgumentException) {
        when (exception.message) {
            "input event invalid" -> {
                printHelper.invalidEventInput()
                printHelper.eventFormat()
            }
            "delete event invalid" -> {
                printHelper.invalidDeleteInput()
                printHelper.deleteFormat()
            }
        }
    }

    fun exitSystem() {
        printHelper.printBye()
        exitProcess(0)
    }

    private fun toEvent(input: String) : FlightEvent {
        val segments = input.split(" ")
        validateEvent(segments, 7, "input event invalid")
        return FlightEvent(segments[0], segments[1], segments[2], segments[3],
            segments[4], LocalDateTime.parse(segments[5]), segments[6].toInt())
    }

    private fun toDeleteEvent(input: String) : DeleteEvent {
        val segments = input.split(" ")
        validateEvent(segments, 2, "delete event invalid")
        return DeleteEvent(segments[0], LocalDateTime.parse(segments[1]))
    }

    private fun toStatusRequest(input: String) : StatusRequest {
        return StatusRequest(LocalDateTime.parse(input))
    }

    private fun validateEvent(segments: List<String>, size : Int, msg : String) {
        if (segments.isEmpty()) throw IllegalArgumentException(msg)
        if (segments.size != size) throw IllegalArgumentException(msg)
    }

}