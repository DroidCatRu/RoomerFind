package ru.droidcat.feature.profile.internal.ui.geoedit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.map.api.model.MapIntent.OnCameraMove
import ru.droidcat.feature.map.internal.createMapComponent
import ru.droidcat.feature.profile.api.ui.geoedit.GeoEditComponent
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent.OnLocationChange
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label.DismissRequested
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label.UserGeoChanged

private const val MAP_CHILD = "MapComponent"

internal class DefaultGeoEditComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : GeoEditComponent, BaseComponentWithStore<GeoEditIntent, GeoEditState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultGeoEditStore>() },
) {

    override val mapComponent = createMapComponent(
        componentContext = childContext(MAP_CHILD),
        onLocationSet = { center, zoom, radius ->
            accept(OnLocationChange(center, zoom, radius))
        }
    )

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is DismissRequested -> onBack()

            is UserGeoChanged -> mapComponent.accept(
                OnCameraMove(
                    center = label.center,
                    zoom = label.zoom,
                )
            )
        }
    }
}

fun createGeoEditComponent(
    componentContext: ComponentContext,
    onBack: () -> Unit = {},
): GeoEditComponent = DefaultGeoEditComponent(
    componentContext = componentContext,
    onBack = onBack,
)
