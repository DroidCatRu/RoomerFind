package ru.droidcat.feature.map.internal

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.map.api.model.MapState
import ru.droidcat.feature.map.api.model.MarkerPosition
import ru.droidcat.feature.map.internal.model.Message
import ru.droidcat.feature.map.internal.model.Message.SetCameraPosition

internal class DefaultMapReducer : Reducer<MapState, Message> {

    override fun MapState.reduce(msg: Message): MapState {
        return when (msg) {
            is SetCameraPosition -> this.copy(
                markerPosition = MarkerPosition(
                    lat = msg.lat,
                    lon = msg.lon,
                    zoom = msg.zoom,
                )
            )
        }
    }
}
