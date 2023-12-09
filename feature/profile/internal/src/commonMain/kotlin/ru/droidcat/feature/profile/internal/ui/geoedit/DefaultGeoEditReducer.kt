package ru.droidcat.feature.profile.internal.ui.geoedit

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState.Loaded
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message.SetGeo
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message.SetLocation

internal class DefaultGeoEditReducer : Reducer<GeoEditState, Message> {

    override fun GeoEditState.reduce(msg: Message): GeoEditState {
        return when (msg) {
            is SetGeo -> Loaded(
                lat = msg.geo.lat,
                lon = msg.geo.lon,
                radius = msg.geo.radius,
            )

            is SetLocation -> (this as? Loaded)?.copy(
                lat = msg.lat,
                lon = msg.lon,
            ) ?: this
        }
    }
}
