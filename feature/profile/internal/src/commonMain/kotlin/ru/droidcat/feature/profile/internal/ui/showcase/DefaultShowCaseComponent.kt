package ru.droidcat.feature.profile.internal.ui.showcase

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.profile.api.ui.showcase.ProfileShowCaseComponent
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label.BackRequested
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label.EditGeoRequested
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label.EditPreferencesRequested
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label.EditProfileRequested

internal class DefaultShowCaseComponent(
    componentContext: ComponentContext,
    private val onEditProfile: () -> Unit,
    private val onEditPreference: () -> Unit,
    private val onEditGeo: () -> Unit,
    private val onBack: () -> Unit,
) : ProfileShowCaseComponent, BaseComponentWithStore<ProfileShowCaseIntent, ProfileShowCaseState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultShowCaseStore>() },
) {

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is EditProfileRequested -> onEditProfile()
            is EditPreferencesRequested -> onEditPreference()
            is EditGeoRequested -> onEditGeo()
            is BackRequested -> onBack()
        }
    }
}

fun createShowCaseComponent(
    componentContext: ComponentContext,
    onEditProfile: () -> Unit = {},
    onEditPreference: () -> Unit = {},
    onEditGeo: () -> Unit = {},
    onBack: () -> Unit = {},
): ProfileShowCaseComponent = DefaultShowCaseComponent(
    componentContext = componentContext,
    onEditProfile = onEditProfile,
    onEditPreference = onEditPreference,
    onEditGeo = onEditGeo,
    onBack = onBack,
)
