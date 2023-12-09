package ru.droidcat.feature.profile.internal.ui.geoedit

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.droidcat.core.mvi.uiDispatcher
import ru.droidcat.feature.profile.api.model.Geo
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState.Loaded
import ru.droidcat.feature.profile.api.usecase.GetGeoUseCase
import ru.droidcat.feature.profile.api.usecase.SaveGeoUseCase
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Action
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Action.GetUserGeo
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Intent
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Intent.OnConfirm
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Intent.OnLocationChange
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label.GeoSaved
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message.SetGeo
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message.SetLocation

internal class DefaultGeoEditExecutor(
    private val getGeoAction: GetGeoUseCase,
    private val saveGeoAction: SaveGeoUseCase,
) : CoroutineExecutor<Intent, Action, GeoEditState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action, getState: () -> GeoEditState) {
        super.executeAction(action, getState)
        when (action) {
            is GetUserGeo -> getUserGeo()
        }
    }

    override fun executeIntent(intent: Intent, getState: () -> GeoEditState) {
        super.executeIntent(intent, getState)
        when (intent) {
            is OnLocationChange -> onLocationChange(
                lat = intent.lat,
                lon = intent.lon,
            )

            is OnConfirm -> onConfirm(
                state = getState(),
            )
        }
    }

    private fun getUserGeo() {
        scope.launch {
            val geoResult = getGeoAction().getOrNull() ?: return@launch
            dispatch(SetGeo(geoResult))
        }
    }

    private fun onLocationChange(
        lat: Double,
        lon: Double,
    ) {
        dispatch(SetLocation(lat, lon))
    }

    private fun onConfirm(
        state: GeoEditState,
    ) {
        if (state !is Loaded) return
        scope.launch {
            val geoResult = saveGeoAction(
                Geo.Defined(
                    lat = state.lat,
                    lon = state.lon,
                    radius = state.radius,
                )
            )
            if (geoResult.isSuccess) {
                publish(GeoSaved)
            }
        }
    }
}
