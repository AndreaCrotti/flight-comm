package pro.juxt.flighttower

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlightTowerApplication : CommandLineRunner {

	override fun run(vararg args: String?) {
		println("Runnn")
	}

}

fun main(args: Array<String>) {
	runApplication<FlightTowerApplication>(*args)
}
