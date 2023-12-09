package ru.droidcat.feature.profile.internal.ui.preferenceedit

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.profile.api.ui.preferencesedit.PreferenceEditComponent
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent.OnConfirm
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent.OnMaxAgeChange
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent.OnMaxValueChange
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent.OnMinAgeChange
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent.OnMinValueChange
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Label
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Label.PreferenceSaved

internal class DefaultPreferenceEditComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : PreferenceEditComponent, BaseComponentWithStore<Intent, PreferenceState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultPreferenceEditStore>() },
) {

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is PreferenceSaved -> onBack()
        }
    }

    override fun onMinValueChange(minValue: String) {
        accept(OnMinValueChange(minValue))
    }

    override fun onMaxValueChange(maxValue: String) {
        accept(OnMaxValueChange(maxValue))
    }

    override fun onMinAgeChange(minAge: String) {
        accept(OnMinAgeChange(minAge))
    }

    override fun onMaxAgeChange(maxAge: String) {
        accept(OnMaxAgeChange(maxAge))
    }

    override fun onConfirm() {
        accept(OnConfirm)
    }

    override fun onDismiss() {
        onBack()
    }
}

fun createPreferenceEditComponent(
    componentContext: ComponentContext,
    onBack: () -> Unit = {},
): PreferenceEditComponent = DefaultPreferenceEditComponent(
    componentContext = componentContext,
    onBack = onBack,
)
