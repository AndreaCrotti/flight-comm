package pro.juxt.flighttower.utils

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pro.juxt.flighttower.fixtures.Fixtures.eventString
import pro.juxt.flighttower.fixtures.Fixtures.stubEvent
import pro.juxt.flighttower.services.FlightEventService
import java.io.ByteArrayInputStream

import java.io.InputStream

internal class InputReaderTest {

    private val mockEventService = mockk<FlightEventService>()
    private val inputReader = InputReader(mockEventService)

    @Test
    fun readEvent_converts_input_to_flight_event() {
        val bytes = eventString.toByteArray()
        val input: InputStream = ByteArrayInputStream(bytes)
        System.setIn(input)
        every { mockEventService.recordNewEvent(stubEvent()) } returns Unit
        inputReader.readEvent()
        verify { mockEventService.recordNewEvent(stubEvent()) }
    }

}