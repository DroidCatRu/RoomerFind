package ru.droidcat.feature.map.api

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.mvi.IntentAcceptor
import ru.droidcat.feature.map.api.model.MapIntent
import ru.droidcat.feature.map.api.model.MapState

interface MapComponent : IntentAcceptor<MapIntent> {

    val viewState: StateFlow<MapState>

    val styleUrl: String
}
