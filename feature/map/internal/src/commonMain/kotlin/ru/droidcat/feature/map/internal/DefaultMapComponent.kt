package ru.droidcat.feature.map.internal

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.map.api.MapComponent
import ru.droidcat.feature.map.api.model.LatLng
import ru.droidcat.feature.map.api.model.MapIntent
import ru.droidcat.feature.map.api.model.MapState
import ru.droidcat.feature.map.internal.model.Label
import ru.droidcat.feature.map.internal.model.Label.CameraMoved

internal class DefaultMapComponent(
    componentContext: ComponentContext,
    private val onLocationSet: (center: LatLng, zoom: Double, distanceToTop: Double) -> Unit,
) : MapComponent, BaseComponentWithStore<MapIntent, MapState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultMapStore>() },
) {

    override val styleUrl = "https://maps.starline.ru/mapstyles/default/style.json"
//    override val styleUrl = "https://demotiles.maplibre.org/style.json"

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is CameraMoved -> onLocationSet(label.center, label.zoom, label.distanceToTop)
        }
    }
}

fun createMapComponent(
    componentContext: ComponentContext,
    onLocationSet: (center: LatLng, zoom: Double, distanceToTop: Double) -> Unit = { _, _, _ -> },
): MapComponent = DefaultMapComponent(
    componentContext = componentContext,
    onLocationSet = onLocationSet,
)
