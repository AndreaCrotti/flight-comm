package pro.juxt.flighttower

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import pro.juxt.flighttower.utils.InputReader

@SpringBootApplication
class FlightTowerApplication(val inputReader: InputReader) : CommandLineRunner {

	override fun run(vararg args: String?) {
		inputReader.startUp()
	}

}

fun main(args: Array<String>) {
	runApplication<FlightTowerApplication>(*args)
}
