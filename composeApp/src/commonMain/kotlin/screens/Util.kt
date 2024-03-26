package screens

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.TimeZone

val currentDate = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()

val dateFormat: DateFormat = SimpleDateFormat.getDateInstance().apply {
    timeZone = TimeZone.getTimeZone("UTC")
}