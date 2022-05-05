package pro.juxt.flighttower

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import pro.juxt.flighttower.utils.InputReader
import pro.juxt.flighttower.utils.PrintHelper

@SpringBootApplication
class FlightTowerApplication(val inputReader: InputReader, val printHelper: PrintHelper) : CommandLineRunner {

	override fun run(vararg args: String?) {
		printHelper.printWelcome()
		printHelper.printModeSelection()
		inputReader.setInputMode()
	}

}

fun main(args: Array<String>) {
	runApplication<FlightTowerApplication>(*args)
}
