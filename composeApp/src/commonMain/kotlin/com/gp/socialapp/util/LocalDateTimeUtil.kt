package com.gp.socialapp.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object LocalDateTimeUtil {
    fun LocalDateTime.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    fun LocalDateTime.toYYYYMMDD() = "${this.year} / ${this.monthNumber} / ${this.dayOfMonth}"
    fun LocalDateTime.toDDMMYYYY() = "${this.dayOfMonth} / ${this.monthNumber} / ${this.year}"

}