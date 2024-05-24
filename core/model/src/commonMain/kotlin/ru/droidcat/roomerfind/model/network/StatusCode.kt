package ru.droidcat.roomerfind.model.network

data class StatusCode(
    val value: Int,
    val description: String,
) {

    companion object {
        val UserAlreadyExists = StatusCode(
            value = 422,
            description = "User already exists",
        )
    }
}
