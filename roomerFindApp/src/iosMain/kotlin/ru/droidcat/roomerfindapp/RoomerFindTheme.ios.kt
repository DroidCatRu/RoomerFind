package ru.droidcat.roomerfindapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
actual fun RoomerFindTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        content = content,
    )
}
