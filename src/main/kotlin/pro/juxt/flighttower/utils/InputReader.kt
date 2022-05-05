package pro.juxt.flighttower.utils

import org.springframework.stereotype.Component

@Component
object InputReader {

    fun readEvent() {
        val input = readln()
        println(input)
    }



}