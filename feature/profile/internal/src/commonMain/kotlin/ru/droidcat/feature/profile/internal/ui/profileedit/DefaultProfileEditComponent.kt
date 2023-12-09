package ru.droidcat.feature.profile.internal.ui.profileedit

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.profile.api.ui.profileedit.ProfileEditComponent
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnAgeChange
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnConfirm
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnDescChange
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnEmailChange
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnNameChange
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnPhoneChange
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Label
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Label.ProfileSaved

internal class DefaultProfileEditComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : ProfileEditComponent, BaseComponentWithStore<Intent, ProfileEditState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultProfileEditStore>() },
) {

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is ProfileSaved -> onBack()
        }
    }

    override fun onNameChange(name: String) {
        accept(OnNameChange(name))
    }

    override fun onAgeChange(age: String) {
        accept(OnAgeChange(age))
    }

    override fun onDescriptionChange(description: String) {
        accept(OnDescChange(description))
    }

    override fun onEmailChange(email: String) {
        accept(OnEmailChange(email))
    }

    override fun onPhoneChange(phone: String) {
        accept(OnPhoneChange(phone))
    }

    override fun onConfirm() {
        accept(OnConfirm)
    }

    override fun onDismiss() {
        onBack()
    }
}

fun createProfileEditComponent(
    componentContext: ComponentContext,
    onBack: () -> Unit = {},
): ProfileEditComponent = DefaultProfileEditComponent(
    componentContext = componentContext,
    onBack = onBack,
)
