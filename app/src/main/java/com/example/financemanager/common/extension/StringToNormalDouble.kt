package com.example.financemanager.common.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun String.toNormalDouble(): Double {
    val stringWithoutComma = this.toDoubleOrNull() ?: 0.0
    return stringWithoutComma / 100
}

fun Long.getLocalDateTimeFroMillisecond(): LocalDateTime {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}