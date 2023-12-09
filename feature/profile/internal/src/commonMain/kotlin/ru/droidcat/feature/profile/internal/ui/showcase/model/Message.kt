package ru.droidcat.feature.profile.internal.ui.showcase.model

import ru.droidcat.feature.profile.api.model.Contacts
import ru.droidcat.feature.profile.api.model.UserProfile

internal sealed interface Message {

    data class SetProfile(
        val profile: UserProfile.Defined,
        val contacts: Contacts.Defined,
    ) : Message
}
