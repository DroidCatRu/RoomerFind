package ru.droidcat.feature.profile.internal.ui.preferenceedit.model

import ru.droidcat.feature.profile.api.model.UserPreference

internal sealed interface Message {

    data class SetMinValue(
        val value: String,
    ) : Message

    data class SetMaxValue(
        val value: String,
    ) : Message

    data class SetMinAge(
        val age: String,
    ) : Message

    data class SetMaxAge(
        val age: String,
    ) : Message

    data class SetPreference(
        val preference: UserPreference.Defined,
    ) : Message
}
