package ru.droidcat.roomerfindapp

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.droidcat.feature.root.api.RootComponent
import ru.droidcat.feature.root.compose.RootContent

@Composable
fun App(
    rootComponent: RootComponent,
) {
    RoomerFindTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            RootContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.ime.asPaddingValues()),
                component = rootComponent,
            )
        }
    }
}
