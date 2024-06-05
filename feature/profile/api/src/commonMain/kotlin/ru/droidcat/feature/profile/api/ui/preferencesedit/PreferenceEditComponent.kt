package ru.droidcat.feature.profile.api.ui.preferencesedit

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.mvi.IntentAcceptor
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState

interface PreferenceEditComponent : IntentAcceptor<PreferenceEditIntent> {

    val viewState: StateFlow<PreferenceState>
}
