package ru.droidcat.roomerfindapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import ru.droidcat.feature.map.compose.LocalMapProvider
import ru.droidcat.feature.map.compose.NativeMapProvider
import ru.droidcat.feature.root.api.RootComponent
import ru.droidcat.feature.root.compose.RootContent

@Composable
fun App(
    mapProvider: NativeMapProvider,
    rootComponent: RootComponent,
) {
    CompositionLocalProvider(LocalMapProvider provides mapProvider) {
        RoomerFindTheme {
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                RootContent(
                    modifier = Modifier.fillMaxSize(),
                    component = rootComponent,
                )
            }
        }
    }
}
