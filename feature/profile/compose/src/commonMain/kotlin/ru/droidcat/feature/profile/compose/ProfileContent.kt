package ru.droidcat.feature.profile.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import ru.droidcat.feature.profile.api.ui.root.ProfileComponent
import ru.droidcat.feature.profile.api.ui.root.model.ProfileChild.GeoEditChild
import ru.droidcat.feature.profile.api.ui.root.model.ProfileChild.PreferenceEditChild
import ru.droidcat.feature.profile.api.ui.root.model.ProfileChild.ProfileEditChild
import ru.droidcat.feature.profile.api.ui.root.model.ProfileChild.ProfileShowCaseChild

@Composable
fun ProfileContent(
    component: ProfileComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
    ) {
        when (val child = it.instance) {
            is ProfileShowCaseChild -> ProfileShowCaseContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )

            is ProfileEditChild -> ProfileEditContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )

            is GeoEditChild -> GeoEditContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )

            is PreferenceEditChild -> PreferenceEditContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
