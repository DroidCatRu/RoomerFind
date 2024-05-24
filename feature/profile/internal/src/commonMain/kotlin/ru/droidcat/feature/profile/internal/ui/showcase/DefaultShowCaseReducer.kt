package ru.droidcat.feature.profile.internal.ui.showcase

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState
import ru.droidcat.feature.profile.internal.ui.showcase.model.Message
import ru.droidcat.feature.profile.internal.ui.showcase.model.Message.SetLoading
import ru.droidcat.feature.profile.internal.ui.showcase.model.Message.SetProfile
import ru.droidcat.roomerfind.model.network.AVATAR_ENDPOINT
import ru.droidcat.roomerfind.model.network.UrlProvider

internal class DefaultShowCaseReducer(
    private val urlProvider: UrlProvider,
) : Reducer<ProfileShowCaseState, Message> {

    override fun ProfileShowCaseState.reduce(msg: Message): ProfileShowCaseState {
        return when (msg) {
            is SetProfile -> ProfileShowCaseState.Loaded(
                name = msg.profile.name,
                age = msg.profile.age.coerceAtLeast(0).toString(),
                avatar = msg.profile.avatar?.let { "${urlProvider.getBasePath()}/$AVATAR_ENDPOINT/$it" },
                description = msg.profile.description,
                contacts = msg.profile.contactInfo.orEmpty(),
                minPrice = msg.profile.preferences.minPrice,
                maxPrice = msg.profile.preferences.maxPrice,
                minAge = msg.profile.preferences.minAge,
                maxAge = msg.profile.preferences.maxAge,
                lat = msg.profile.preferences.lat,
                long = msg.profile.preferences.long,
                radius = msg.profile.preferences.radius,
            )

            is SetLoading -> ProfileShowCaseState.Loading
        }
    }
}
