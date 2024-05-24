package ru.droidcat.feature.finders.api.ui.profile

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.mvi.IntentAcceptor
import ru.droidcat.feature.finders.api.ui.profile.model.FinderProfileIntent
import ru.droidcat.feature.finders.api.ui.profile.model.ProfileState

interface FinderProfileComponent : IntentAcceptor<FinderProfileIntent> {

    val viewState: StateFlow<ProfileState>
}
