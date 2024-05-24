package ru.droidcat.roomerfindapp

import androidx.compose.ui.window.ComposeUIViewController
import ru.droidcat.feature.root.api.RootComponent

fun MainViewController(
    rootComponent: RootComponent,
) = ComposeUIViewController {
    App(
        rootComponent = rootComponent,
    )
}
