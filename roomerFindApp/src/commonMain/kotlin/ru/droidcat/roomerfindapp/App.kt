package ru.droidcat.roomerfindapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.droidcat.feature.auth.api.root.AuthComponent
import ru.droidcat.feature.auth.compose.root.AuthContent

@Composable
fun App(
    mapProvider: NativeMapProvider,
    authComponent: AuthComponent,
) {
    CompositionLocalProvider(LocalMapProvider provides mapProvider) {
        RoomerFindTheme {
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                AuthContent(
                    modifier = Modifier.fillMaxSize(),
                    component = authComponent,
                )
            }
        }
    }
}