package pro.juxt.flighttower.utils

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pro.juxt.flighttower.fixtures.Fixtures.eventString
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent
import pro.juxt.flighttower.services.FlightEventService
import java.io.ByteArrayInputStream

import java.io.InputStream

internal class InputReaderTest {

    private val mockEventService = mockk<FlightEventService>()
    private val inputReader = spyk<InputReader>(InputReader(mockEventService))

    @Test
    fun readEvent_converts_input_to_flight_event() {
        sendToInput(eventString)
        every { mockEventService.recordNewEvent(stubEvent()) } returns Unit
        inputReader.readEvent()
        verify(exactly = 1) { mockEventService.recordNewEvent(stubEvent()) }
    }

    @Test
    fun set_input_mode_calls_read_event() {
        sendToInput("1")
        every { inputReader.readEvent() } returns Unit
        inputReader.setInputMode()
        verify(exactly = 1) { inputReader.readEvent() }
    }

    private fun sendToInput(string: String) {
        val bytes = string.toByteArray()
        val input: InputStream = ByteArrayInputStream(bytes)
        System.setIn(input)
    }

}