package ru.droidcat.feature.profile.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.droidcat.feature.profile.api.ui.root.ProfileComponent
import ru.droidcat.feature.profile.api.ui.root.model.ProfileRootSlot.GeoEditSlot
import ru.droidcat.feature.profile.api.ui.root.model.ProfileRootSlot.PreferenceEditSlot
import ru.droidcat.feature.profile.api.ui.root.model.ProfileRootSlot.ProfileEditSlot

@Composable
fun ProfileContent(
    component: ProfileComponent,
    modifier: Modifier = Modifier,
) {
    ProfileShowCaseContent(
        component = component.showCaseComponent,
        modifier = modifier,
    )

    val childSlot by component.childSlot.subscribeAsState()
    when (val child = childSlot.child?.instance) {
        is ProfileEditSlot -> ProfileEditContent(
            component = child.component,
            modifier = Modifier.fillMaxSize(),
        )

        is GeoEditSlot -> GeoEditContent(
            component = child.component,
            modifier = Modifier.fillMaxSize(),
        )

        is PreferenceEditSlot -> PreferenceEditContent(
            component = child.component,
            modifier = Modifier.fillMaxSize(),
        )

        else -> {}
    }
}
