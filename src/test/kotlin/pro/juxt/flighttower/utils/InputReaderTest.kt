package pro.juxt.flighttower.utils

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pro.juxt.flighttower.helpers.Fixtures.eventString
import pro.juxt.flighttower.helpers.Fixtures.stubEvent
import pro.juxt.flighttower.services.FlightEventService
import java.io.ByteArrayInputStream

import java.io.InputStream

internal class InputReaderTest {

    private val mockEventService = mockk<FlightEventService>()
    private val mockPrintHelper = mockk<PrintHelper>()
    private val inputReader = spyk<InputReader>(InputReader(mockPrintHelper, mockEventService))

    @Test
    fun start_up_prints_welcome() {
        every { mockPrintHelper.printWelcome() } returns Unit
        every { mockPrintHelper.printModeSelection() } returns Unit
        every { inputReader.setInputMode() } returns Unit
        inputReader.startUp()
        verify(exactly = 1) { mockPrintHelper.printWelcome() }
    }

    @Test
    fun start_up_leads_to_mode_selection() {
        every { mockPrintHelper.printWelcome() } returns Unit
        every { mockPrintHelper.printModeSelection() } returns Unit
        every { inputReader.setInputMode() } returns Unit
        inputReader.startUp()
        verify(exactly = 1) { mockPrintHelper.printModeSelection() }
        verify(exactly = 1) { inputReader.setInputMode() }
    }

    @Test
    fun set_input_mode_1_to_update_event() {
        stdin("1")
        every { inputReader.readEvent() } returns Unit
        inputReader.setInputMode()
        verify(exactly = 1) { inputReader.readEvent() }
    }

    @Test
    fun set_input_mode_2_to_read_new_event() {
        stdin("2")
        every { inputReader.updateEvent() } returns Unit
        inputReader.setInputMode()
        verify(exactly = 1) { inputReader.updateEvent() }
    }

    @Test
    fun read_event_passes_flight_event_to_service() {
        stdin(eventString)
        every { mockEventService.recordNewEvent(stubEvent()) } returns true
        inputReader.readEvent()
        verify(exactly = 1) { mockEventService.recordNewEvent(stubEvent()) }
    }

    @Test
    fun update_event_passes_flight_event_to_service() {
        stdin(eventString)
        every { mockEventService.updateEvent(stubEvent()) } returns Unit
        inputReader.updateEvent()
        verify(exactly = 1) { mockEventService.updateEvent(stubEvent()) }
    }

    private fun stdin(string: String) {
        val bytes = string.toByteArray()
        val input: InputStream = ByteArrayInputStream(bytes)
        System.setIn(input)
    }

}