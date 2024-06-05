package ru.droidcat.feature.profile.api.ui.root

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.droidcat.feature.profile.api.ui.root.model.ProfileRootSlot
import ru.droidcat.feature.profile.api.ui.showcase.ProfileShowCaseComponent

interface ProfileComponent : BackHandlerOwner {

    val childSlot: Value<ChildSlot<*, ProfileRootSlot>>

    val showCaseComponent: ProfileShowCaseComponent

    fun onBackRequest()
}
