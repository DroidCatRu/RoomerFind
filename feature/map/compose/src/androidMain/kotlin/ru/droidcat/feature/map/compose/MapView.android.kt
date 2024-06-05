package ru.droidcat.feature.map.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import ru.droidcat.feature.map.api.MapComponent

@Composable
actual fun MapView(
    component: MapComponent,
    modifier: Modifier,
    nativeMapProvider: NativeMapProvider,
) {
    val viewState by component.viewState.collectAsState()

    var map: NativeMapView? by remember { mutableStateOf(null) }

    DisposableEffect(nativeMapProvider) {
        map = nativeMapProvider.createMap(component)

        onDispose {
            map = null
        }
    }

    LaunchedEffect(viewState, map) {
        map?.apply {
            controller.setCameraLocation(
                center = viewState.center,
                zoom = viewState.zoom,
            )
        }
    }

    map?.apply {
        AndroidView(
            modifier = modifier,
            factory = { view },
        )
    }
}
