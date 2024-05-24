package ru.droidcat.feature.finders.internal.ui.matches

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.finders.api.ui.matches.model.Match
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState
import ru.droidcat.feature.finders.internal.ui.matches.model.Message
import ru.droidcat.feature.finders.internal.ui.matches.model.Message.SetMatches
import ru.droidcat.roomerfind.model.network.AVATAR_ENDPOINT
import ru.droidcat.roomerfind.model.network.UrlProvider
import ru.droidcat.roomerfind.model.network.UserInfoDTO
import ru.droidcat.roomerfind.model.network.UserPreferencesDTO

internal class DefaultMatchesReducer(
    private val urlProvider: UrlProvider,
) : Reducer<MatchesState, Message> {

    override fun MatchesState.reduce(msg: Message): MatchesState {
        return when (msg) {
            is SetMatches -> MatchesState.Loaded(
                matches = msg.matches.map {
                    Match(
                        id = it.id,
                        title = it.title,
                        priceRange = it.preferences.priceRange,
                        description = it.description.takeIf { it.isNotBlank() },
                        avatar = it.avatarUrl,
                    )
                },
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
