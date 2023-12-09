package ru.droidcat.feature.map.api

import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.map.api.model.MapState

interface MapComponent {

    val viewState: Value<MapState>

    val styleUrl: String

    fun onCameraMove(
        lat: Double,
        lon: Double,
        zoom: Double,
    )
}
