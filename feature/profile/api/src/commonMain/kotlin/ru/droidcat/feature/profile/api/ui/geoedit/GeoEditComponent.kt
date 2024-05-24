package ru.droidcat.feature.profile.api.ui.geoedit

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.mvi.IntentAcceptor
import ru.droidcat.feature.map.api.MapComponent
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState

interface GeoEditComponent : IntentAcceptor<GeoEditIntent> {

    val viewState: StateFlow<GeoEditState>

    val mapComponent: MapComponent
}
