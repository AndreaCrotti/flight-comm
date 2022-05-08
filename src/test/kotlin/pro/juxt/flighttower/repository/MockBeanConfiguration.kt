package pro.juxt.flighttower.repository

import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import pro.juxt.flighttower.utils.InputReader

@TestConfiguration
class MockBeanConfiguration {

    @Bean
    fun mockInputReader() : InputReader {
        val mockk = mockk<InputReader>()
        every { mockk.startUp() } returns Unit
        return mockk
    }

}