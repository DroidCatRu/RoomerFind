package ru.droidcat.feature.map.api

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.feature.map.api.model.MapState

interface MapComponent {

    val viewState: StateFlow<MapState>

    val styleUrl: String

    fun onCameraMove(
        lat: Double,
        lon: Double,
        zoom: Double,
    )
}
