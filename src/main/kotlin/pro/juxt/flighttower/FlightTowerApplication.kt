package pro.juxt.flighttower

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import pro.juxt.flighttower.models.FlightEvent
import pro.juxt.flighttower.services.FlightEventService
import java.time.LocalDateTime

@SpringBootApplication
class FlightTowerApplication(val flightEventService: FlightEventService) : CommandLineRunner {

	override fun run(vararg args: String?) {
		println("Runnn")
		val event = FlightEvent("F123", "747", "Paris", "Berlin", "Landing",
			LocalDateTime.of(2022, 5, 4, 13, 30), 150)
		flightEventService.recordNewEvent(event);
	}

}

fun main(args: Array<String>) {
	runApplication<FlightTowerApplication>(*args)
}
