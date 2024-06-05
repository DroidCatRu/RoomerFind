package ru.droidcat.feature.profile.internal.ui.profileedit

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.profile.api.ui.profileedit.ProfileEditComponent
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Label
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Label.DismissRequested

internal class DefaultProfileEditComponent(
    componentContext: ComponentContext,
    private val onDismiss: () -> Unit,
) : ProfileEditComponent, BaseComponentWithStore<ProfileEditIntent, ProfileEditState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultProfileEditStore>() },
) {

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is DismissRequested -> onDismiss()
        }
    }
}

fun createProfileEditComponent(
    componentContext: ComponentContext,
    onDismiss: () -> Unit = {},
): ProfileEditComponent = DefaultProfileEditComponent(
    componentContext = componentContext,
    onDismiss = onDismiss,
)
