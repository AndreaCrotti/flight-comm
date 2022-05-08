package pro.juxt.flighttower.utils

import com.mongodb.client.result.UpdateResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pro.juxt.flighttower.fixtures.Fixtures.deleteString
import pro.juxt.flighttower.fixtures.Fixtures.eventString
import pro.juxt.flighttower.fixtures.Fixtures.mockDataBaseError
import pro.juxt.flighttower.fixtures.Fixtures.stubDeleteEvent
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent
import pro.juxt.flighttower.services.FlightEventService
import java.io.ByteArrayInputStream

import java.io.InputStream

internal class InputReaderTest {

    private val mockEventService = mockk<FlightEventService>()
    private val mockPrintHelper = mockk<PrintHelper>()
    private val inputReader = spyk<InputReader>(InputReader(mockPrintHelper, mockEventService))

    @BeforeEach
    fun setup() {
        every { mockPrintHelper.printWelcome() } returns Unit
        every { mockPrintHelper.printModeSelection() } returns Unit
        every { mockPrintHelper.printUpdateSuccess(1) } returns Unit
        every { mockPrintHelper.printUpdateNotSuccess() } returns Unit
        every { mockPrintHelper.printEventSaved() } returns Unit
        every { mockPrintHelper.printErrorConnectingToDb() } returns Unit
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
        verify(exactly = 1) { mockPrintHelper.printModeSelection() }
        verify(exactly = 1) { inputReader.setInputMode() }
    }

    @Test
    fun set_input_mode_1_to_read_new_event() {
        stdin("1")
        every { inputReader.readEvent() } returns Unit
        inputReader.setInputMode()
        verify(exactly = 1) { inputReader.readEvent() }
    }

    @Test
    fun set_input_mode_2_to_update_event() {
        stdin("2")
        every { inputReader.updateEvent() } returns Unit
        inputReader.setInputMode()
        verify(exactly = 1) { inputReader.updateEvent() }
    }

    @Test
    fun set_input_mode_3_to_delete_event() {
        stdin("3")
        every { inputReader.deleteEvent() } returns Unit
        inputReader.setInputMode()
        verify(exactly = 1) { inputReader.deleteEvent() }
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
    fun update_event_prints_event_saved_on_upsert() {
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

    private fun stdin(string: String) {
        val bytes = string.toByteArray()
        val input: InputStream = ByteArrayInputStream(bytes)
        System.setIn(input)
    }

}