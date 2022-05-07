package pro.juxt.flighttower.utils

import org.springframework.stereotype.Component

@Component
object PrintHelper {

    private val welcomeMessage = """
        
        #################################################################################
        ##                                                                             ##
        ##  Welcome to the Flight Tower Controller Command Line Interface App v1.8.12  ##
        ##                                                                             ##
        #################################################################################
        
    """.trimIndent()

    fun printWelcome() {
        println(welcomeMessage)
    }

    private val selectInputMode = """
        Select input mode mode:
        1 : Input new Flight Event
        2 : Update Flight Event
        3 : Delete Flight Event
        4 : Get Flight Status
        (enter: 1, 2, 3 or 4)
    """.trimIndent()

    fun printModeSelection() {
        println(selectInputMode)
    }
}