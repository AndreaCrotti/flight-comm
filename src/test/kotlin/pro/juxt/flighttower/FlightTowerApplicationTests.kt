package pro.juxt.flighttower

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import pro.juxt.flighttower.helpers.MockBeanConfiguration


@SpringBootTest
@Import(MockBeanConfiguration::class)
class FlightTowerApplicationTests {

//	@Test
//	fun contextLoads() {
//	}

}
