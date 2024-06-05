package ru.droidcat.feature.profile.internal.ui.geoedit

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState.Loaded
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message.SetLocation
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message.SetProfile

internal class DefaultGeoEditReducer : Reducer<GeoEditState, Message> {

    override fun GeoEditState.reduce(msg: Message): GeoEditState {
        return when (msg) {
            is SetProfile -> Loaded(
                profile = msg.profile,
            )

            is SetLocation -> (this as? Loaded)?.copy(
                profile = profile.copy(
                    preferences = profile.preferences.copy(
                        lat = msg.lat,
                        long = msg.lon,
                        zoom = msg.zoom,
                        radius = msg.radius,
                    )
                )
            ) ?: this
        }
    }
}
