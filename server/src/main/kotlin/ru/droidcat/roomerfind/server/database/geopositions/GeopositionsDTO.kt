package ru.droidcat.roomerfind.server.database.geopositions

import kotlinx.serialization.Serializable

@Serializable
class GeopositionsDTO(
    val userId: Int,
    val lat: Double,
    val lon: Double,
    val rad: Double
)