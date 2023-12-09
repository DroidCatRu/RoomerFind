package ru.droidcat.feature.profile.api.ui.preferencesedit

import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState

interface PreferenceEditComponent {

    val viewState: Value<PreferenceState>

    fun onMinValueChange(minValue: String)

    fun onMaxValueChange(maxValue: String)

    fun onMinAgeChange(minAge: String)

    fun onMaxAgeChange(maxAge: String)

    fun onConfirm()

    fun onDismiss()
}
