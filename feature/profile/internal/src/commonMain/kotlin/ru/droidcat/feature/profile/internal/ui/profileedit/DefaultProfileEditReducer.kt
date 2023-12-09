package ru.droidcat.feature.profile.internal.ui.profileedit

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState.Loaded
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetAge
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetDesc
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetEmail
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetName
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetPhone
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetProfile

internal class DefaultProfileEditReducer : Reducer<ProfileEditState, Message> {

    override fun ProfileEditState.reduce(msg: Message): ProfileEditState {
        return when (msg) {
            is SetName -> (this as? Loaded)?.copy(
                name = msg.name,
            ) ?: this

            is SetAge -> (this as? Loaded)?.copy(
                age = msg.age,
            ) ?: this

            is SetDesc -> (this as? Loaded)?.copy(
                description = msg.desc,
            ) ?: this

            is SetEmail -> (this as? Loaded)?.copy(
                email = msg.email,
            ) ?: this

            is SetPhone -> (this as? Loaded)?.copy(
                phone = msg.phone,
            ) ?: this

            is SetProfile -> Loaded(
                name = msg.profile.name,
                age = msg.profile.age,
                description = msg.profile.description,
                email = msg.contacts.email,
                phone = msg.contacts.phone,
            )
        }
    }
}
