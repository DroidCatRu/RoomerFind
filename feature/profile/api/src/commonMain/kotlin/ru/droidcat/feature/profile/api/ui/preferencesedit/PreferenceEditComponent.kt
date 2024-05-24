package ru.droidcat.feature.profile.api.ui.preferencesedit

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState

interface PreferenceEditComponent {

    val viewState: StateFlow<PreferenceState>

    fun onMinValueChange(minValue: String)

    fun onMaxValueChange(maxValue: String)

    fun onMinAgeChange(minAge: String)

    fun onMaxAgeChange(maxAge: String)

    fun onConfirm()

    fun onDismiss()
}
