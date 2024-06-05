package ru.droidcat.feature.profile.internal.ui.profileedit

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState.Loaded
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState.Loading
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetAge
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetContacts
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetDesc
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetLoading
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetName
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetProfile

internal class DefaultProfileEditReducer : Reducer<ProfileEditState, Message> {

    override fun ProfileEditState.reduce(msg: Message): ProfileEditState {
        return when (msg) {
            is SetName -> (this as? Loaded)?.copy(
                profile.copy(
                    name = msg.value,
                )
            ) ?: this

            is SetAge -> (this as? Loaded)?.copy(
                profile.copy(
                    age = msg.value.toIntOrNull() ?: 0,
                )
            ) ?: this

            is SetDesc -> (this as? Loaded)?.copy(
                profile.copy(
                    description = msg.value,
                )
            ) ?: this

            is SetContacts -> (this as? Loaded)?.copy(
                profile.copy(
                    contactInfo = msg.value,
                )
            ) ?: this

            is SetProfile -> Loaded(
                profile = msg.value,
            )

            is SetLoading -> Loading
        }
    }
}
