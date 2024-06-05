package ru.droidcat.feature.main.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.droidcat.core.ui.backAnimation
import ru.droidcat.feature.finders.compose.FindersContent
import ru.droidcat.feature.main.api.MainComponent
import ru.droidcat.feature.main.api.model.MainChild.FindersChild
import ru.droidcat.feature.main.api.model.MainChild.ProfileChild
import ru.droidcat.feature.profile.compose.ProfileContent

@Composable
fun MainContent(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = backAnimation(
            backHandler = component.backHandler,
            onBack = component::onBackRequest,
        ),
    ) {
        when (val child = it.instance) {
            is ProfileChild -> ProfileContent(
                modifier = Modifier.fillMaxSize(),
                component = child.component,
            )

            is FindersChild -> FindersContent(
                modifier = Modifier.fillMaxSize(),
                component = child.component,
            )
        }
    }
}
