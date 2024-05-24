package ru.droidcat.feature.map.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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

    val cameraPosition by remember { derivedStateOf { viewState.markerPosition } }

    var map: NativeMapView? by remember { mutableStateOf(null) }

    DisposableEffect(nativeMapProvider) {
        map = nativeMapProvider.createMap(component)

        onDispose {
            map = null
        }
    }

    LaunchedEffect(cameraPosition, map) {
        map?.apply {
            controller.setCameraLocation(
                lat = cameraPosition.lat,
                lon = cameraPosition.lon,
                zoom = cameraPosition.zoom,
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
