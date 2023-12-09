package ru.droidcat.feature.profile.internal.ui.geoedit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.map.internal.createMapComponent
import ru.droidcat.feature.profile.api.ui.geoedit.GeoEditComponent
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Intent
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Intent.OnConfirm
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Intent.OnLocationChange
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label.GeoSaved

private const val MAP_CHILD = "MapComponent"

internal class DefaultGeoEditComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : GeoEditComponent, BaseComponentWithStore<Intent, GeoEditState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultGeoEditStore>() },
) {

    override val mapComponent = createMapComponent(
        componentContext = childContext(MAP_CHILD),
        onLocationSet = { lat, lon ->
            accept(OnLocationChange(lat, lon))
        }
    )

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is GeoSaved -> onBack()
        }
    }

    override fun onConfirm() {
        accept(OnConfirm)
    }

    override fun onDismiss() {
        onBack()
    }
}

fun createGeoEditComponent(
    componentContext: ComponentContext,
    onBack: () -> Unit,
): GeoEditComponent = DefaultGeoEditComponent(
    componentContext = componentContext,
    onBack = onBack,
)
