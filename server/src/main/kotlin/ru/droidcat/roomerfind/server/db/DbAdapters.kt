package ru.droidcat.roomerfind.server.db

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.TimeZone.Companion
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime

val booleanAdapter by lazy {
    object : ColumnAdapter<Boolean, Int> {
        override fun decode(databaseValue: Int) = databaseValue != 0
        override fun encode(value: Boolean) = if (value) 1 else 0
    }
}

val instantAdapter by lazy {
    object : ColumnAdapter<Instant, LocalDateTime> {
        override fun decode(databaseValue: LocalDateTime) =
            databaseValue.toKotlinLocalDateTime().toInstant(TimeZone.UTC)

        override fun encode(value: Instant) =
            value.toLocalDateTime(TimeZone.UTC).toJavaLocalDateTime()
    }
}
