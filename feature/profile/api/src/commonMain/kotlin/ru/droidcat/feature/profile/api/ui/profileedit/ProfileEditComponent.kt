package ru.droidcat.feature.profile.api.ui.profileedit

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.mvi.IntentAcceptor
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState

interface ProfileEditComponent : IntentAcceptor<ProfileEditIntent> {

    val viewState: StateFlow<ProfileEditState>
}
