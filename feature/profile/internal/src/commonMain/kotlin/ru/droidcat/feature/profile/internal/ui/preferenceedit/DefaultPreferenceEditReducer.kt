package ru.droidcat.feature.profile.internal.ui.preferenceedit

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState.Loaded
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMaxAge
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMaxValue
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMinAge
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMinValue
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetPreference

internal class DefaultPreferenceEditReducer : Reducer<PreferenceState, Message> {

    override fun PreferenceState.reduce(msg: Message): PreferenceState {
        return when (msg) {
            is SetMinValue -> (this as? Loaded)?.copy(
                minValue = msg.value,
            ) ?: this

            is SetMaxValue -> (this as? Loaded)?.copy(
                maxValue = msg.value,
            ) ?: this

            is SetMinAge -> (this as? Loaded)?.copy(
                minAge = msg.age,
            ) ?: this

            is SetMaxAge -> (this as? Loaded)?.copy(
                maxAge = msg.age,
            ) ?: this

            is SetPreference -> Loaded(
                minValue = msg.preference.minValue,
                maxValue = msg.preference.maxValue,
                minAge = msg.preference.minAge,
                maxAge = msg.preference.maxAge,
            )
        }
    }
}
