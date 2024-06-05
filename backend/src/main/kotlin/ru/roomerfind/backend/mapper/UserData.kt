package ru.roomerfind.backend.mapper

import ru.droidcat.roomerfind.model.network.UserInfoDTO
import ru.droidcat.roomerfind.model.network.UserPreferencesDTO
import ru.roomerfind.backend.database.entity.UserData

fun UserData.toUserInfoDTO() = UserInfoDTO(
    id = id,
    name = name,
    age = age,
    avatar = avatar,
    description = description,
    contactInfo = contactInfo,
    preferences = toUserPreferences(),
)

fun UserData.toUserPreferences() = UserPreferencesDTO(
    minPrice = minPrice,
    maxPrice = maxPrice,
    minAge = minAge,
    maxAge = maxAge,
    lat = lat,
    long = long,
    zoom = zoom,
    radius = radiusKm,
)
