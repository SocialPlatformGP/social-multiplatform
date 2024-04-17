package com.gp.socialapp.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object LocalDateTimeUtil {
    fun LocalDateTime.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    fun LocalDateTime.toYYYYMMDD() = "${this.year} / ${this.monthNumber} / ${this.dayOfMonth}"
    fun LocalDateTime.toDDMMYYYY() = "${this.dayOfMonth} / ${this.monthNumber} / ${this.year}"
    fun LocalDateTime.toMillis() = this.toInstant(TimeZone.UTC).toEpochMilliseconds()
    fun Long.toHHMMTimestamp(): String {
        val localDateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
        return "${localDateTime.hour}:${localDateTime.minute}"
    }

    fun Long.toLocalDateTime() = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
    fun Long.getDateHeader(): String {
        val localDateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
        val today = LocalDateTime.now().date
        return when {
            localDateTime.date == today -> "Today"
            localDateTime.date == today.minus(1, DateTimeUnit.DAY) -> "Yesterday"
            localDateTime.date.daysUntil(today) < 7 -> localDateTime.date.dayOfWeek.name
            else -> localDateTime.toDDMMYYYY()
        }
    }

    fun convertEpochToTime(epoch: Long): String {
        val localDateTime = Instant.fromEpochMilliseconds(epoch).toLocalDateTime(TimeZone.UTC)
        val hour = (localDateTime.hour + 2)
        val minute = localDateTime.minute
        val amOrPm = if (hour < 12) "AM" else "PM"
        val hourIn12HrFormat = if (hour > 12) hour - 12 else hour
        return "$hourIn12HrFormat:$minute: $amOrPm"
    }
}