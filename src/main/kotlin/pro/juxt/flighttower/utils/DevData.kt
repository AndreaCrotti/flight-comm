package pro.juxt.flighttower.utils

import pro.juxt.flighttower.models.FlightEvent
import java.time.LocalDate
import java.time.LocalDateTime

object DevData {

    private const val LAND = "Land"
    private const val RE_FUEL = "Re-Fuel"
    private const val TAKE_OFF = "Take-Off"

    private const val m747 = "747"

    private const val F101 = "F101"
    private const val LONDON = "LONDON"
    private const val MIAMI = "MIAMI"

    private const val F102 = "F102"
    private const val REYKJAVIK = "REYKJAVIK"
    private const val AMSTERDAM = "AMSTERDAM"

    private const val F103 = "F103"
    private const val LISBON = "LISBON"
    private const val SYDNEY = "SYDNEY"

    /**
    Flight from London to Miami - End Status : Take-Off , Fuel 500
     */
    private val event1 = FlightEvent(F101, m747, LONDON, MIAMI, LAND, stubDateTime(hour = 7, minute = 0), 0)
    private val event2 = FlightEvent(F101, m747, LONDON, MIAMI, RE_FUEL, stubDateTime(hour = 10, minute = 0), 500)
    private val event3 = FlightEvent(F101, m747, LONDON, MIAMI, TAKE_OFF, stubDateTime(hour = 13, minute = 0), 0)

    /**
    Flight from Reykjavik to Amsterdam - End Status : Land , Fuel 300
     */
    private val event4 = FlightEvent(F102, m747, REYKJAVIK, AMSTERDAM, RE_FUEL, stubDateTime(hour = 8, minute = 0), 450)
    private val event5 = FlightEvent(F102, m747, REYKJAVIK, AMSTERDAM, TAKE_OFF, stubDateTime(hour = 11, minute = 0), 0)
    private val event6 = FlightEvent(F102, m747, REYKJAVIK, AMSTERDAM, LAND, stubDateTime(hour = 14, minute = 0), -150)

    /**
     * Flight from Lisbon to Sydney - End Status : Land , Fuel 225
     */
    private val event7 = FlightEvent(F103, m747, LISBON, SYDNEY, RE_FUEL, stubDateTime(hour = 9, minute = 0), 800)
    private val event8 = FlightEvent(F103, m747, LISBON, SYDNEY, TAKE_OFF, stubDateTime(hour = 12, minute = 0), 0)
    private val event9 = FlightEvent(F103, m747, LISBON, SYDNEY, LAND, stubDateTime(hour = 15, minute = 0), -575)

    private fun stubDateTime(hour : Int, minute : Int) : LocalDateTime {
        val today = LocalDate.now()
        return LocalDateTime.of(today.year, today.month, today.dayOfMonth, hour, minute)
    }

    val devDataSet : List<FlightEvent> = listOf(event1, event2, event3, event4,
                                            event5, event6, event7, event8, event9)

}