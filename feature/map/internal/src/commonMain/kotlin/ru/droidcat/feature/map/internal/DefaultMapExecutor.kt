package ru.droidcat.feature.map.internal

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.map.api.model.MapIntent
import ru.droidcat.feature.map.api.model.MapIntent.OnCameraMove
import ru.droidcat.feature.map.api.model.MapIntent.OnCameraMoveManual
import ru.droidcat.feature.map.api.model.MapState
import ru.droidcat.feature.map.internal.model.Action
import ru.droidcat.feature.map.internal.model.Label
import ru.droidcat.feature.map.internal.model.Label.CameraMoved
import ru.droidcat.feature.map.internal.model.Message
import ru.droidcat.feature.map.internal.model.Message.SetCameraPosition

internal class DefaultMapExecutor :
    CoroutineExecutor<MapIntent, Action, MapState, Message, Label>(uiDispatcher) {

    override fun executeIntent(intent: MapIntent) {
        super.executeIntent(intent)
        when (intent) {
            is OnCameraMoveManual -> {
                dispatch(
                    SetCameraPosition(
                        center = intent.center,
                        zoom = intent.zoom,
                    )
                )
                publish(
                    CameraMoved(
                        center = intent.center,
                        zoom = intent.zoom,
                        distanceToTop = intent.distanceToTop,
                    )
                )
            }

            is OnCameraMove -> {
                dispatch(
                    SetCameraPosition(
                        center = intent.center,
                        zoom = intent.zoom,
                    )
                )
            }
        }
    }
}
