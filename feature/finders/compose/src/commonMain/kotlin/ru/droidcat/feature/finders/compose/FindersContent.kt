package ru.droidcat.feature.finders.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.droidcat.core.ui.backAnimation
import ru.droidcat.feature.finders.api.ui.root.FindersComponent
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.FindersTabsChild
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.ProfileChild

@Composable
fun FindersContent(
    component: FindersComponent,
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
            is FindersTabsChild -> TabsContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )

            is ProfileChild -> ProfileContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
