package ru.roomerfind.backend.utils

val String.isValidEmail get() = matches(EMAIL_REGEX.toRegex())

val String.isValidPassword get() = length >= MIN_PASSWORD_LENGTH

private const val EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*" +
    "@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
private const val MIN_PASSWORD_LENGTH = 5
