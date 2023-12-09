package ru.droidcat.feature.map.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.droidcat.feature.map.api.MapComponent

@Composable
expect fun MapView(
    component: MapComponent,
    modifier: Modifier = Modifier,
    nativeMapProvider: NativeMapProvider = LocalMapProvider.current,
)
