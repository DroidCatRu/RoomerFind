package ru.droidcat.feature.profile.internal.ui.showcase

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.profile.api.ui.showcase.ProfileShowCaseComponent
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState
import ru.droidcat.feature.profile.internal.ui.showcase.model.Intent
import ru.droidcat.feature.profile.internal.ui.showcase.model.Intent.OnLogOut
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label

internal class DefaultShowCaseComponent(
    componentContext: ComponentContext,
    private val onEditProfile: () -> Unit,
    private val onEditPreference: () -> Unit,
    private val onGeoEdit: () -> Unit,
    private val onBack: () -> Unit,
) : ProfileShowCaseComponent, BaseComponentWithStore<Intent, ProfileShowCaseState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultShowCaseStore>() },
) {

    override fun onEditProfileRequest() {
        onEditProfile()
    }

    override fun onEditPreferenceRequest() {
        onEditPreference()
    }

    override fun onGeoEditRequest() {
        onGeoEdit()
    }

    override fun onBackRequest() {
        onBack()
    }

    override fun onLogOutRequest() {
        accept(OnLogOut)
    }
}

fun createShowCaseComponent(
    componentContext: ComponentContext,
    onEditProfile: () -> Unit = {},
    onEditPreference: () -> Unit = {},
    onGeoEdit: () -> Unit = {},
    onBack: () -> Unit = {},
): ProfileShowCaseComponent = DefaultShowCaseComponent(
    componentContext = componentContext,
    onEditProfile = onEditProfile,
    onEditPreference = onEditPreference,
    onGeoEdit = onGeoEdit,
    onBack = onBack,
)
