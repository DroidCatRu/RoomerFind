package ru.droidcat.feature.profile.internal.ui.profileedit.model

import ru.droidcat.feature.profile.api.model.Contacts
import ru.droidcat.feature.profile.api.model.UserProfile

internal sealed interface Message {

    data class SetName(
        val name: String,
    ) : Message

    data class SetAge(
        val age: String,
    ) : Message

    data class SetDesc(
        val desc: String,
    ) : Message

    data class SetEmail(
        val email: String,
    ) : Message

    data class SetPhone(
        val phone: String,
    ) : Message

    data class SetProfile(
        val profile: UserProfile.Defined,
        val contacts: Contacts.Defined,
    ) : Message
}
