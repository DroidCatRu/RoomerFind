package ru.droidcat.feature.profile.internal.ui.preferenceedit

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState.Loaded
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState.Loading
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetLoading
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMaxAge
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMaxValue
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMinAge
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMinValue
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetProfile

internal class DefaultPreferenceEditReducer : Reducer<PreferenceState, Message> {

    override fun PreferenceState.reduce(msg: Message): PreferenceState {
        return when (msg) {
            is SetMinValue -> (this as? Loaded)?.copy(
                profile.copy(
                    preferences = profile.preferences.copy(
                        minPrice = msg.value.toIntOrNull() ?: 0,
                    ),
                )
            ) ?: this

            is SetMaxValue -> (this as? Loaded)?.copy(
                profile.copy(
                    preferences = profile.preferences.copy(
                        maxPrice = msg.value.toIntOrNull() ?: 0,
                    ),
                )
            ) ?: this

            is SetMinAge -> (this as? Loaded)?.copy(
                profile.copy(
                    preferences = profile.preferences.copy(
                        minAge = msg.value.toIntOrNull() ?: 0,
                    ),
                )
            ) ?: this

            is SetMaxAge -> (this as? Loaded)?.copy(
                profile.copy(
                    preferences = profile.preferences.copy(
                        maxAge = msg.value.toIntOrNull() ?: 0,
                    ),
                )
            ) ?: this

            is SetProfile -> Loaded(
                profile = msg.value,
            )

            is SetLoading -> Loading
        }
    }
}
