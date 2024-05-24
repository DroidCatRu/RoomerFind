package ru.droidcat.feature.profile.api.ui.root.model

import ru.droidcat.feature.profile.api.ui.geoedit.GeoEditComponent
import ru.droidcat.feature.profile.api.ui.preferencesedit.PreferenceEditComponent
import ru.droidcat.feature.profile.api.ui.profileedit.ProfileEditComponent

sealed interface ProfileRootSlot {

    data class GeoEditSlot(val component: GeoEditComponent) : ProfileRootSlot

    data class PreferenceEditSlot(val component: PreferenceEditComponent) : ProfileRootSlot

    data class ProfileEditSlot(val component: ProfileEditComponent) : ProfileRootSlot
}
