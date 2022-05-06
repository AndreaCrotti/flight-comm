package pro.juxt.flighttower.models

import java.time.LocalDateTime

data class DeleteEvent(val planeId : String, val timestamp : LocalDateTime)
