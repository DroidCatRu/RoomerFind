package ru.droidcat.feature.map.internal

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.map.api.model.MapState
import ru.droidcat.feature.map.internal.model.Action
import ru.droidcat.feature.map.internal.model.Intent
import ru.droidcat.feature.map.internal.model.Intent.OnLocationChange
import ru.droidcat.feature.map.internal.model.Label
import ru.droidcat.feature.map.internal.model.Message
import ru.droidcat.feature.map.internal.model.Message.SetCameraPosition

internal class DefaultMapExecutor :
    CoroutineExecutor<Intent, Action, MapState, Message, Label>(uiDispatcher) {

    override fun executeIntent(intent: Intent) {
        super.executeIntent(intent)
        when (intent) {
            is OnLocationChange -> onLocationChange(
                lat = intent.lat,
                lon = intent.lon,
                zoom = intent.zoom,
            )
        }
    }

    private fun onLocationChange(
        lat: Double,
        lon: Double,
        zoom: Double,
    ) {
        dispatch(
            SetCameraPosition(
                lat = lat,
                lon = lon,
                zoom = zoom,
            )
        )
    }
}
