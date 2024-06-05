package ru.droidcat.feature.profile.internal.ui.preferenceedit

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.profile.api.ui.preferencesedit.PreferenceEditComponent
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Label
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Label.DismissRequested

internal class DefaultPreferenceEditComponent(
    componentContext: ComponentContext,
    private val onDismiss: () -> Unit,
) : PreferenceEditComponent, BaseComponentWithStore<PreferenceEditIntent, PreferenceState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultPreferenceEditStore>() },
) {

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is DismissRequested -> onDismiss()
        }
    }
}

fun createPreferenceEditComponent(
    componentContext: ComponentContext,
    onDismiss: () -> Unit = {},
): PreferenceEditComponent = DefaultPreferenceEditComponent(
    componentContext = componentContext,
    onDismiss = onDismiss,
)
