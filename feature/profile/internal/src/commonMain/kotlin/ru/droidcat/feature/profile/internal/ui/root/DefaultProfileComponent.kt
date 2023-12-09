package ru.droidcat.feature.profile.internal.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.push
import ru.droidcat.core.mvi.BaseComponent
import ru.droidcat.feature.profile.api.ui.root.ProfileComponent
import ru.droidcat.feature.profile.api.ui.root.model.ProfileChild.GeoEditChild
import ru.droidcat.feature.profile.api.ui.root.model.ProfileChild.PreferenceEditChild
import ru.droidcat.feature.profile.api.ui.root.model.ProfileChild.ProfileEditChild
import ru.droidcat.feature.profile.api.ui.root.model.ProfileChild.ProfileShowCaseChild
import ru.droidcat.feature.profile.internal.ui.geoedit.createGeoEditComponent
import ru.droidcat.feature.profile.internal.ui.preferenceedit.createPreferenceEditComponent
import ru.droidcat.feature.profile.internal.ui.profileedit.createProfileEditComponent
import ru.droidcat.feature.profile.internal.ui.root.model.ProfileStackConfig
import ru.droidcat.feature.profile.internal.ui.root.model.ProfileStackConfig.EditGeoConfig
import ru.droidcat.feature.profile.internal.ui.root.model.ProfileStackConfig.EditPreferenceConfig
import ru.droidcat.feature.profile.internal.ui.root.model.ProfileStackConfig.EditProfileConfig
import ru.droidcat.feature.profile.internal.ui.root.model.ProfileStackConfig.ShowCaseConfig
import ru.droidcat.feature.profile.internal.ui.showcase.createShowCaseComponent

internal class DefaultProfileComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : ProfileComponent, BaseComponent(
    componentContext = componentContext,
) {

    private val navigation = StackNavigation<ProfileStackConfig>()

    override val childStack = childStack(
        source = navigation,
        initialConfiguration = ShowCaseConfig,
        handleBackButton = true,
        childFactory = { config, context ->
            when (config) {
                is ShowCaseConfig -> ProfileShowCaseChild(
                    component = createShowCaseComponent(
                        componentContext = context,
                        onEditProfile = ::navigateEditProfile,
                        onEditPreference = ::navigateEditPreference,
                        onGeoEdit = ::navigateEditGeo,
                        onBack = onBack,
                    )
                )

                is EditProfileConfig -> ProfileEditChild(
                    component = createProfileEditComponent(
                        componentContext = context,
                        onBack = ::popToShowCase,
                    )
                )

                is EditPreferenceConfig -> PreferenceEditChild(
                    component = createPreferenceEditComponent(
                        componentContext = context,
                        onBack = ::popToShowCase,
                    )
                )

                is EditGeoConfig -> GeoEditChild(
                    component = createGeoEditComponent(
                        componentContext = context,
                        onBack = ::popToShowCase,
                    )
                )
            }
        }
    )

    private fun popToShowCase() {
        navigation.popWhile { it != ShowCaseConfig }
    }

    private fun navigateEditProfile() {
        navigation.push(EditProfileConfig)
    }

    private fun navigateEditPreference() {
        navigation.push(EditPreferenceConfig)
    }

    private fun navigateEditGeo() {
        navigation.push(EditGeoConfig)
    }
}

fun createProfileComponent(
    componentContext: ComponentContext,
    onBack: () -> Unit = {},
): ProfileComponent = DefaultProfileComponent(
    componentContext = componentContext,
    onBack = onBack,
)
