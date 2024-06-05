package ru.droidcat.roomerfindapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import ru.droidcat.feature.map.compose.NativeMapProvider
import ru.droidcat.feature.root.api.RootComponent

@OptIn(ExperimentalDecomposeApi::class)
fun MainViewController(
    rootComponent: RootComponent,
    backDispatcher: BackDispatcher,
    mapProvider: NativeMapProvider,
) = ComposeUIViewController {
    PredictiveBackGestureOverlay(
        backDispatcher = backDispatcher,
        backIcon = null,
        modifier = Modifier.fillMaxSize(),
    ) {
        App(
            rootComponent = rootComponent,
            nativeMapProvider = mapProvider,
        )
    }
}
