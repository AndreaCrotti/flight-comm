package pro.juxt.flighttower.utils

import org.springframework.stereotype.Component
import pro.juxt.flighttower.models.FlightStatus

@Component
object PrintHelper {

    private val welcomeMessage = """
        
        #########################################################
        ##                                                     ##
        ##           *** Welcome to Flight Comm ***            ## 
        ##                                                     ## 
        ##    The Flight Controller Command Line Interface     ##
        ##                      v1.8.12                        ##
        ##                                                     ##
        #########################################################
        
    """.trimIndent()

    fun printWelcome() {
        println(welcomeMessage)
    }

    private val selectInputMode = """
        Select input mode:
        1 : Input new Flight Event
        2 : Update Flight Event
        3 : Delete Flight Event
        4 : Get Flight Status
        5 : (Dev mode) Run test data
        (enter: 1, 2, 3, 4 or 5)
    """.trimIndent()

    fun printModeSelection() {
        println(selectInputMode)
    }

    fun printEventSaved() = println("New event successfully saved")

    fun printSaveFailed() = println("Failed to save event")

    fun printUpdateSuccess(updates : Long) = println("Update successful, $updates records updated")

    fun printUpdateNotSuccess() = println("Update unsuccessful")

    fun printErrorConnectingToDb() = println("Error connecting to database")

    fun printDelete(count : Long) = println("Record deleted, count: $count")

    fun printDeleteUnsuccessful() = println("Delete unsuccessful, no matching event found")

    fun printStatus(flightStatusList: List<FlightStatus>) {
        println("====")
            if (flightStatusList.isEmpty()) println("No Data")
            else flightStatusList.forEach { println("${it.planeId} ${it.status} ${it.fuel}") }
        println("====")
    }

    fun printReadMode() = println("Input new Flight Event :")

    fun printUpdateMode() = println("Update Flight Event :")

    fun printDeleteMode() = println("Delete Flight Event :")

    fun printGetStatusMode() = println("Get Flight Status - enter timestamp in format :")

    private val helpMessage = """
        Help Options:
        Type any of the following commands, at any time...
        
        'switch'    - go back to the main menu to select input modes
        'help'      - print help options
        'exit'      - exit program
        
    """.trimIndent()

    fun printHelp() = println(helpMessage)

    fun invalidEventInput() = println("Invalid event input, format should be:")

    fun invalidDeleteInput() = println("Invalid delete input, format should be:")

    fun invalidDateInput() = println("Invalid date-time input, format should be:")

    fun invalidNumber() = println("Invalid number input")

    fun eventFormat() = println("\$plane-id \$model \$origin \$destination \$event-type \$timestamp \$fuel-delta")

    fun deleteFormat() = println("\$plane-id \$timestamp")

    fun timestampFormat() = println("yyyy-MM-ddTHH:mm:ss")

    private val devDataUploaded = """
        
        *** Dev Data successfully uploaded ***
        
    """.trimIndent()

    fun devDataUploaded() = println(devDataUploaded)

    fun printBye() = println("\n... bye! ...")

}