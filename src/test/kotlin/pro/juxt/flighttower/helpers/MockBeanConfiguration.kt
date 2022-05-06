package pro.juxt.flighttower.helpers

import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import pro.juxt.flighttower.utils.InputReader
import pro.juxt.flighttower.utils.PrintHelper

@TestConfiguration
class MockBeanConfiguration {

    @Bean
    fun mockInputReader() : InputReader {
        val mockk = mockk<InputReader>()
        every { mockk.setInputMode() } returns Unit
        return mockk
    }

    @Bean
    fun mockPrintHelper() : PrintHelper {
        val mockk = mockk<PrintHelper>()
        every { mockk.printWelcome() } returns Unit
        every { mockk.printModeSelection() } returns Unit
        return mockk
    }

}