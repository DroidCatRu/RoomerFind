package ru.droidcat.feature.map.internal

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.map.api.MapComponent
import ru.droidcat.feature.map.api.model.MapState
import ru.droidcat.feature.map.internal.model.Intent
import ru.droidcat.feature.map.internal.model.Intent.OnLocationChange
import ru.droidcat.feature.map.internal.model.Label

internal class DefaultMapComponent(
    componentContext: ComponentContext,
    private val onLocationSet: (lat: Double, lon: Double) -> Unit,
) : MapComponent, BaseComponentWithStore<Intent, MapState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultMapStore>() },
) {

//    override val styleUrl = "https://maps.starline.ru/mapstyles/default/style.json"
    override val styleUrl = "https://demotiles.maplibre.org/style.json"

    override fun onCameraMove(lat: Double, lon: Double, zoom: Double) {
        accept(OnLocationChange(lat, lon, zoom))
        onLocationSet(lat, lon)
    }
}

fun createMapComponent(
    componentContext: ComponentContext,
    onLocationSet: (lat: Double, lon: Double) -> Unit = { _, _ -> },
): MapComponent = DefaultMapComponent(
    componentContext = componentContext,
    onLocationSet = onLocationSet,
)
