package ru.droidcat.feature.finders.internal.ui.search

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.finders.api.ui.search.model.SearchState
import ru.droidcat.feature.finders.api.ui.search.model.SearchState.Loaded
import ru.droidcat.feature.finders.api.ui.search.model.SearchState.Loading
import ru.droidcat.feature.finders.api.ui.search.model.SearchState.NoProfile
import ru.droidcat.feature.finders.internal.ui.search.model.Message
import ru.droidcat.feature.finders.internal.ui.search.model.Message.SetLoading
import ru.droidcat.feature.finders.internal.ui.search.model.Message.SetNoProfile
import ru.droidcat.feature.finders.internal.ui.search.model.Message.SetProfile
import ru.droidcat.roomerfind.model.network.AVATAR_ENDPOINT
import ru.droidcat.roomerfind.model.network.UrlProvider
import ru.droidcat.roomerfind.model.network.UserInfoDTO
import ru.droidcat.roomerfind.model.network.UserPreferencesDTO

internal class DefaultSearchReducer(
    private val urlProvider: UrlProvider,
) : Reducer<SearchState, Message> {

    override fun SearchState.reduce(msg: Message): SearchState = when (msg) {
        is SetProfile -> Loaded(
            id = msg.value.id,
            title = msg.value.title,
            priceRange = msg.value.preferences.priceRange,
            description = msg.value.description.takeIf { it.isNotBlank() },
            avatar = msg.value.avatarUrl,
        )

        is SetLoading -> Loading

        is SetNoProfile -> NoProfile
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
