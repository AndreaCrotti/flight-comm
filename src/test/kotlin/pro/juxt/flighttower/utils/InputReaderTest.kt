package pro.juxt.flighttower.utils

import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

import java.io.InputStream
import java.io.PrintStream

internal class InputReaderTest {

    private val eventString = "F551 747 PARIS LONDON Re-Fuel 2021-03-29T10:00:00 345"
    private val inputReader = InputReader

    @Test
    fun test_input() {
        val bytes = eventString.toByteArray()
        val input: InputStream = ByteArrayInputStream(bytes)
        System.setIn(input)
        inputReader.readEvent()
    }

}