package pro.juxt.flighttower.utils

import com.mongodb.client.result.UpdateResult
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pro.juxt.flighttower.fixtures.Fixtures.deleteString
import pro.juxt.flighttower.fixtures.Fixtures.eventString
import pro.juxt.flighttower.fixtures.Fixtures.getStatusString
import pro.juxt.flighttower.fixtures.Fixtures.mockDataBaseError
import pro.juxt.flighttower.fixtures.Fixtures.stubDeleteEvent
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent
import pro.juxt.flighttower.fixtures.Fixtures.stubFlightStatus
import pro.juxt.flighttower.fixtures.Fixtures.stubFlightsStatusSet
import pro.juxt.flighttower.fixtures.Fixtures.stubStatusRequest
import pro.juxt.flighttower.services.FlightEventService
import java.io.ByteArrayInputStream

import java.io.InputStream

private const val SWITCH = "switch"
private const val HELP = "help"
private const val EXIT = "exit"

internal class InputReaderTest {

    private val mockEventService = mockk<FlightEventService>()
    private val mockPrintHelper = mockk<PrintHelper>(relaxUnitFun = true)
    private val inputReader = spyk<InputReader>(InputReader(mockPrintHelper, mockEventService))

    @BeforeEach
    fun setup() {
        // stop recursion running infinitely
        every { inputReader.runAgain(any()) } returns Unit
    }

    @Test
    fun start_up_prints_welcome() {
        every { inputReader.setInputMode() } returns Unit
        inputReader.startUp()
        verify(exactly = 1) { mockPrintHelper.printWelcome() }
    }

    @Test
    fun start_up_leads_to_mode_selection() {
        every { inputReader.setInputMode() } returns Unit
        inputReader.startUp()
        verify(exactly = 1) { inputReader.setInputMode() }
    }

    @Test
    fun set_input_mode_prints_options() {
        stdin("1")
        every { inputReader.readEvent() } returns Unit
        inputReader.setInputMode()
        verify(exactly = 1) { mockPrintHelper.printModeSelection() }
    }

    @Test
    fun set_input_mode_1_to_read_new_event() {
        stdin("1")
        every { inputReader.readEventWrapper() } returns Unit
        inputReader.setInputMode()
        verify(exactly = 1) { inputReader.readEventWrapper() }
    }

    @Test
    fun set_input_mode_2_to_update_event() {
        stdin("2")
        every { inputReader.updateEventWrapper() } returns Unit
        inputReader.setInputMode()
        verify(exactly = 1) { inputReader.updateEventWrapper() }
    }

    @Test
    fun set_input_mode_3_to_delete_event() {
        stdin("3")
        every { inputReader.deleteEventWrapper() } returns Unit
        inputReader.setInputMode()
        verify(exactly = 1) { inputReader.deleteEventWrapper() }
    }

    @Test
    fun read_event_passes_flight_event_to_service() {
        stdin(eventString)
        every { mockEventService.recordNewEvent(stubEvent()) } returns true
        inputReader.readEvent()
        verify(exactly = 1) { mockEventService.recordNewEvent(stubEvent()) }
    }

    @Test
    fun read_event_prints_save_successful() {
        stdin(eventString)
        every { mockEventService.recordNewEvent(stubEvent()) } returns true
        inputReader.readEvent()
        verify(exactly = 1) { mockPrintHelper.printEventSaved() }
    }

    @Test
    fun read_event_prints_save_failed() {
        stdin(eventString)
        every { mockEventService.recordNewEvent(stubEvent()) } returns false
        inputReader.readEvent()
        verify(exactly = 1) { mockPrintHelper.printSaveFailed() }
    }

    @Test
    fun read_event_prints_error_on_exception() {
        stdin(eventString)
        every { mockEventService.recordNewEvent(stubEvent()) } throws mockDataBaseError
        inputReader.readEvent()
        verify(exactly = 1) { mockPrintHelper.printErrorConnectingToDb() }
    }

    @Test
    fun update_event_passes_flight_event_to_service() {
        stdin(eventString)
        every { mockEventService.updateEvent(stubEvent()) } returns
                UpdateResult.acknowledged(1, 1, null)
        inputReader.updateEvent()
        verify(exactly = 1) { mockEventService.updateEvent(stubEvent()) }
    }

    @Test
    fun update_event_prints_success_on_update() {
        stdin(eventString)
        every { mockEventService.updateEvent(stubEvent()) } returns
                UpdateResult.acknowledged(1, 1, null)
        inputReader.updateEvent()
        verify(exactly = 1) { mockPrintHelper.printUpdateSuccess(1) }
    }

    @Test
    fun update_event_prints_on_upsert() {
        stdin(eventString)
        every { mockEventService.updateEvent(stubEvent()) } returns
                UpdateResult.acknowledged(0, 1, null)
        inputReader.updateEvent()
        verify(exactly = 1) { mockPrintHelper.printEventSaved() }
    }

    @Test
    fun update_event_prints_unsuccessful_on_failed_update() {
        stdin(eventString)
        every { mockEventService.updateEvent(stubEvent()) } returns
                UpdateResult.unacknowledged()
        inputReader.updateEvent()
        verify(exactly = 1) { mockPrintHelper.printUpdateNotSuccess() }
    }

    @Test
    fun update_event_prints_error_on_exception() {
        stdin(eventString)
        every { mockEventService.updateEvent(stubEvent()) } throws mockDataBaseError
        inputReader.updateEvent()
        verify(exactly = 1) { mockPrintHelper.printErrorConnectingToDb() }
    }

    @Test
    fun delete_passes_delete_event_to_service() {
        stdin(deleteString)
        every { mockEventService.deleteEvent(stubDeleteEvent) } returns 1
        inputReader.deleteEvent()
        verify(exactly = 1) { mockEventService.deleteEvent(stubDeleteEvent) }
    }

    @Test
    fun delete_prints_successful() {
        stdin(deleteString)
        every { mockEventService.deleteEvent(stubDeleteEvent) } returns 1
        inputReader.deleteEvent()
        verify(exactly = 1) { mockPrintHelper.printDelete(1) }
    }

    @Test
    fun delete_prints_unsuccessful() {
        stdin(deleteString)
        every { mockEventService.deleteEvent(stubDeleteEvent) } returns 0
        inputReader.deleteEvent()
        verify(exactly = 1) { mockPrintHelper.printDeleteUnsuccessful() }
    }

    @Test
    fun delete_prints_error_on_exception() {
        stdin(deleteString)
        every { mockEventService.deleteEvent(stubDeleteEvent)  } throws mockDataBaseError
        inputReader.deleteEvent()
        verify(exactly = 1) { mockPrintHelper.printErrorConnectingToDb() }
    }

    @Test
    fun get_status_passes_request_to_service() {
        stdin(getStatusString)
        every { mockEventService.getStatusAt(stubStatusRequest) } returns listOf(stubFlightStatus)
        inputReader.getStatus()
        verify(exactly = 1) { mockEventService.getStatusAt(stubStatusRequest) }
    }

    @Test
    fun get_status_prints_all_returned_flight_status() {
        stdin(getStatusString)
        every { mockEventService.getStatusAt(stubStatusRequest) } returns stubFlightsStatusSet
        inputReader.getStatus()
        verify { mockPrintHelper.printStatus(stubFlightsStatusSet) }
    }

    @Test
    fun get_status_prints_error_on_exception() {
        stdin(getStatusString)
        every { mockEventService.getStatusAt(stubStatusRequest) } throws mockDataBaseError
        inputReader.getStatus()
        verify { mockPrintHelper.printErrorConnectingToDb() }
    }

    @Test
    fun read_event_works_recursively() {
        stdin(eventString)
        every { mockEventService.recordNewEvent(stubEvent()) } returns true
        inputReader.readEvent()
        verify(exactly = 1) { inputReader.runAgain(any()) }
    }

    @Test
    fun update_event_works_recursively() {
        stdin(eventString)
        every { mockEventService.updateEvent(stubEvent()) } returns
                UpdateResult.acknowledged(0, 1, null)
        inputReader.updateEvent()
        verify(exactly = 1) { inputReader.runAgain(any()) }
    }

    @Test
    fun delete_event_works_recursively() {
        stdin(deleteString)
        every { mockEventService.deleteEvent(stubDeleteEvent) } returns 1
        inputReader.deleteEvent()
        verify(exactly = 1) { inputReader.runAgain(any()) }
    }

    @Test
    fun get_status_works_recursively() {
        stdin(getStatusString)
        every { mockEventService.getStatusAt(stubStatusRequest) } returns listOf(stubFlightStatus)
        inputReader.getStatus()
        verify(exactly = 1) { inputReader.runAgain(any()) }
    }

    @Test
    fun check_input_allows_switching_modes() {
        every { inputReader.setInputMode() } returns Unit
        inputReader.checkInput(SWITCH) { }
        verify(exactly = 1) { inputReader.setInputMode() }
    }

    @Test
    fun check_input_prints_help_message() {
        inputReader.checkInput(HELP) { }
        verify(exactly = 1) { mockPrintHelper.printHelp() }
    }

    @Test
    fun check_input_calls_callback_on_help() {
        every { inputReader.readEvent() } returns Unit
        inputReader.checkInput(HELP) { inputReader.readEvent() }
        verify(exactly = 1) { inputReader.readEvent() }
    }

    @Test
    fun check_input_calls_exit() {
        every { inputReader.exitSystem() } returns Unit
        inputReader.checkInput(EXIT) { }
        verify(exactly = 1) { inputReader.exitSystem() }
    }

    @Test
    fun read_event_checks_input_actions() {
        stdin(SWITCH)
        every { inputReader.setInputMode() } returns Unit
        inputReader.readEvent()
        verify(exactly = 1) { inputReader.setInputMode() }
    }

    @Test
    fun update_event_checks_input_actions() {
        stdin(SWITCH)
        every { inputReader.setInputMode() } returns Unit
        inputReader.updateEvent()
        verify(exactly = 1) { inputReader.setInputMode() }
    }

    @Test
    fun delete_event_checks_input_actions() {
        stdin(SWITCH)
        every { inputReader.setInputMode() } returns Unit
        inputReader.deleteEvent()
        verify(exactly = 1) { inputReader.setInputMode() }
    }

    @Test
    fun get_status_checks_input_actions() {
        stdin(SWITCH)
        every { inputReader.setInputMode() } returns Unit
        inputReader.deleteEvent()
        verify(exactly = 1) { inputReader.setInputMode() }
    }

    @Test
    fun read_event_wrapper_prints_then_calls_method() {
        every { inputReader.readEvent() } returns Unit
        inputReader.readEventWrapper()
        verify { mockPrintHelper.printReadMode() }
        verify { inputReader.readEvent() }
    }

    @Test
    fun update_event_wrapper_prints_then_calls_method() {
        every { inputReader.updateEvent() } returns Unit
        inputReader.updateEventWrapper()
        verify { mockPrintHelper.printUpdateMode() }
        verify { inputReader.updateEvent() }
    }

    @Test
    fun delete_event_wrapper_prints_then_calls_method() {
        every { inputReader.deleteEvent() } returns Unit
        inputReader.deleteEventWrapper()
        verify { mockPrintHelper.printDeleteMode() }
        verify { inputReader.deleteEvent() }
    }

    @Test
    fun get_status_wrapper_prints_then_calls_method() {
        every { inputReader.getStatus() } returns Unit
        inputReader.getStatusWrapper()
        verify { mockPrintHelper.printGetStatusMode() }
        verify { inputReader.getStatus() }
    }

    private fun stdin(string: String) {
        val bytes = string.toByteArray()
        val input: InputStream = ByteArrayInputStream(bytes)
        System.setIn(input)
    }

}