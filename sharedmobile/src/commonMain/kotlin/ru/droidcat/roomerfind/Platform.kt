package ru.droidcat.roomerfind

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform