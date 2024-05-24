package ru.droidcat.feature.profile.api.ui.showcase

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.mvi.IntentAcceptor
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState

interface ProfileShowCaseComponent : IntentAcceptor<ProfileShowCaseIntent> {

    val viewState: StateFlow<ProfileShowCaseState>
}
