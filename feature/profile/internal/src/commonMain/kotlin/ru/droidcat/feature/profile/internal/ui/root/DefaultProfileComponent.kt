package ru.droidcat.feature.profile.internal.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import ru.droidcat.core.mvi.BaseComponent
import ru.droidcat.feature.profile.api.ui.root.ProfileComponent
import ru.droidcat.feature.profile.api.ui.root.model.ProfileRootSlot
import ru.droidcat.feature.profile.api.ui.root.model.ProfileRootSlot.GeoEditSlot
import ru.droidcat.feature.profile.api.ui.root.model.ProfileRootSlot.PreferenceEditSlot
import ru.droidcat.feature.profile.api.ui.root.model.ProfileRootSlot.ProfileEditSlot
import ru.droidcat.feature.profile.api.ui.showcase.ProfileShowCaseComponent
import ru.droidcat.feature.profile.internal.ui.geoedit.createGeoEditComponent
import ru.droidcat.feature.profile.internal.ui.preferenceedit.createPreferenceEditComponent
import ru.droidcat.feature.profile.internal.ui.profileedit.createProfileEditComponent
import ru.droidcat.feature.profile.internal.ui.root.model.ProfileSlotConfig
import ru.droidcat.feature.profile.internal.ui.root.model.ProfileSlotConfig.EditGeoConfig
import ru.droidcat.feature.profile.internal.ui.root.model.ProfileSlotConfig.EditPreferenceConfig
import ru.droidcat.feature.profile.internal.ui.root.model.ProfileSlotConfig.EditProfileConfig
import ru.droidcat.feature.profile.internal.ui.showcase.createShowCaseComponent

internal class DefaultProfileComponent(
    componentContext: ComponentContext,
    onBack: () -> Unit,
) : ProfileComponent, BaseComponent(
    componentContext = componentContext,
) {

    private val navigation = SlotNavigation<ProfileSlotConfig>()

    override val childSlot: Value<ChildSlot<*, ProfileRootSlot>> = childSlot(
        source = navigation,
        serializer = ProfileSlotConfig.serializer(),
    ) { config, context ->
        when (config) {
            is EditProfileConfig -> ProfileEditSlot(
                component = createProfileEditComponent(
                    componentContext = context,
                    onBack = ::dismiss,
                )
            )

            EditGeoConfig -> GeoEditSlot(
                component = createGeoEditComponent(
                    componentContext = context,
                    onBack = ::dismiss,
                )
            )
            EditPreferenceConfig -> PreferenceEditSlot(
                component = createPreferenceEditComponent(
                    componentContext = context,
                    onBack = ::dismiss,
                )
            )
        }
    }
    override val showCaseComponent: ProfileShowCaseComponent = createShowCaseComponent(
        componentContext = childContext(PROFILE_SHOWCASE_KEY),
        onEditProfile = ::navigateEditProfile,
        onEditPreference = ::navigateEditPreference,
        onEditGeo = ::navigateEditGeo,
        onBack = onBack,
    )

    private fun dismiss() {
        navigation.dismiss()
    }

    private fun navigateEditProfile() {
        navigation.activate(EditProfileConfig)
    }

    private fun navigateEditPreference() {
        navigation.activate(EditPreferenceConfig)
    }

    private fun navigateEditGeo() {
        navigation.activate(EditGeoConfig)
    }
}

fun createProfileComponent(
    componentContext: ComponentContext,
    onBack: () -> Unit = {},
): ProfileComponent = DefaultProfileComponent(
    componentContext = componentContext,
    onBack = onBack,
)

private val PROFILE_SHOWCASE_KEY = "ProfileShowCaseComponent"
