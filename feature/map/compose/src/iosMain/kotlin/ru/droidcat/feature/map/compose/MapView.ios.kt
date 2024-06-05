package ru.droidcat.feature.map.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import ru.droidcat.feature.map.api.MapComponent

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapView(
    component: MapComponent,
    modifier: Modifier,
    nativeMapProvider: NativeMapProvider,
) {
    val viewState by component.viewState.collectAsState()

    val map = remember { nativeMapProvider.createMap(component) }

    LaunchedEffect(viewState, map) {
        map.controller.setCameraLocation(
            center = viewState.center,
            zoom = viewState.zoom,
        )
    }

    UIKitView(
        modifier = modifier,
        factory = { map.view },
        onResize = { view, rect ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            view.setFrame(rect)
            CATransaction.commit()
        },
    )
}
