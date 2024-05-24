package ru.droidcat.feature.profile.api.ui.root

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.profile.api.ui.root.model.ProfileRootSlot
import ru.droidcat.feature.profile.api.ui.showcase.ProfileShowCaseComponent

interface ProfileComponent {

    val childSlot: Value<ChildSlot<*, ProfileRootSlot>>

    val showCaseComponent: ProfileShowCaseComponent
}
