package ru.droidcat.feature.profile.internal.ui.showcase

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState
import ru.droidcat.feature.profile.internal.ui.showcase.model.Message
import ru.droidcat.feature.profile.internal.ui.showcase.model.Message.SetProfile

internal class DefaultShowCaseReducer : Reducer<ProfileShowCaseState, Message> {

    override fun ProfileShowCaseState.reduce(msg: Message): ProfileShowCaseState {
        return when (msg) {
            is SetProfile -> ProfileShowCaseState.Loaded(
                name = msg.profile.name,
                age = msg.profile.age,
                description = msg.profile.description,
                email = msg.contacts.email,
                phone = msg.contacts.phone,
            )
        }
    }
}
