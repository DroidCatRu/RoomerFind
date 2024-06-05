package ru.droidcat.feature.profile.internal.ui.geoedit

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.map.api.model.LatLng
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent.OnConfirm
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent.OnDismiss
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent.OnLocationChange
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState.Loaded
import ru.droidcat.feature.profile.api.usecase.GetProfileUseCase
import ru.droidcat.feature.profile.api.usecase.SaveProfileUseCase
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Action
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Action.GetUserGeo
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Action.SaveProfile
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label.DismissRequested
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label.UserGeoChanged
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message.SetLocation
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message.SetProfile

internal class DefaultGeoEditExecutor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
) : CoroutineExecutor<GeoEditIntent, Action, GeoEditState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action) {
        super.executeAction(action)
        when (action) {
            is GetUserGeo ->
                getProfileUseCase.requests
                    .onEach {
                        it.onSuccess {
                            dispatch(SetProfile(it))
                            publish(
                                UserGeoChanged(
                                    center = LatLng(
                                        lat = it.preferences.lat,
                                        long = it.preferences.long,
                                    ),
                                    zoom = it.preferences.zoom,
                                )
                            )
                        }.onFailure {
                            publish(DismissRequested)
                        }
                    }
                    .launchIn(scope)

            is SaveProfile -> scope.launch {
                (state() as? Loaded)?.let {
                    saveProfileUseCase.saveProfile(it.profile).onSuccess {
                        publish(DismissRequested)
                    }
                }
            }
        }
    }

    override fun executeIntent(intent: GeoEditIntent) {
        super.executeIntent(intent)
        when (intent) {
            is OnLocationChange -> dispatch(
                SetLocation(
                    lat = intent.center.lat,
                    lon = intent.center.long,
                    zoom = intent.zoom,
                    radius = intent.radius,
                )
            )

            OnConfirm -> forward(SaveProfile)
            OnDismiss -> publish(DismissRequested)
        }
    }
}
