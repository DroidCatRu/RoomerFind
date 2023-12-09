package ru.droidcat.feature.profile.api.ui.root.model

import ru.droidcat.feature.profile.api.ui.geoedit.GeoEditComponent
import ru.droidcat.feature.profile.api.ui.preferencesedit.PreferenceEditComponent
import ru.droidcat.feature.profile.api.ui.profileedit.ProfileEditComponent
import ru.droidcat.feature.profile.api.ui.showcase.ProfileShowCaseComponent

sealed interface ProfileChild {

    data class GeoEditChild(val component: GeoEditComponent) : ProfileChild

    data class PreferenceEditChild(val component: PreferenceEditComponent) : ProfileChild

    data class ProfileEditChild(val component: ProfileEditComponent) : ProfileChild

    data class ProfileShowCaseChild(val component: ProfileShowCaseComponent) : ProfileChild
}
