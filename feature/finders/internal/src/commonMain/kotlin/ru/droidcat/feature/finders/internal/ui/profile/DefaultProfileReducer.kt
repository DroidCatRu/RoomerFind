package ru.droidcat.feature.finders.internal.ui.profile

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.finders.api.ui.profile.model.ProfileState
import ru.droidcat.feature.finders.api.ui.profile.model.ProfileState.Loaded
import ru.droidcat.feature.finders.internal.ui.profile.model.Message
import ru.droidcat.feature.finders.internal.ui.profile.model.Message.SetProfile
import ru.droidcat.roomerfind.model.network.AVATAR_ENDPOINT
import ru.droidcat.roomerfind.model.network.UrlProvider
import ru.droidcat.roomerfind.model.network.UserInfoDTO
import ru.droidcat.roomerfind.model.network.UserPreferencesDTO

internal class DefaultProfileReducer(
    private val urlProvider: UrlProvider,
) : Reducer<ProfileState, Message> {

    override fun ProfileState.reduce(msg: Message): ProfileState {
        return when (msg) {
            is SetProfile -> Loaded(
                title = msg.value.title,
                priceRange = msg.value.preferences.priceRange,
                description = msg.value.description.takeIf { it.isNotBlank() },
                avatar = msg.value.avatarUrl,
                contacts = msg.value.contactInfo,
            )
        }
    }

    private val UserInfoDTO.title: String get() =
        name.orDefaultName() + age.takeIf { it > 0 }?.let { ", $it" }.orEmpty()

    private fun String.orDefaultName() = this.takeIf { it.isNotBlank() } ?: "Нет имени"

    private val UserPreferencesDTO.priceRange: String? get() = buildString {
        if (minPrice == maxPrice) append(minPrice)
        minPrice.takeIf { it > 0 }?.let { append("от $it") }
        append(" ")
        maxPrice.takeIf { it > 0 }?.let { append("до $it") }
    }.trim().takeIf { it.isNotBlank() }

    private val UserInfoDTO.avatarUrl: String? get() =
        avatar?.let { "${urlProvider.getBasePath()}/$AVATAR_ENDPOINT/$it" }
}
