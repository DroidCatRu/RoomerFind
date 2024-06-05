package ru.droidcat.feature.root.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.droidcat.feature.auth.compose.root.AuthContent
import ru.droidcat.feature.main.compose.MainContent
import ru.droidcat.feature.root.api.RootComponent
import ru.droidcat.feature.root.api.model.RootChild.AuthChild
import ru.droidcat.feature.root.api.model.RootChild.MainChild
import ru.droidcat.feature.root.api.model.RootChild.SplashChild

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
    ) {
        when (val child = it.instance) {
            is SplashChild -> SplashContent(
                modifier = Modifier.fillMaxSize(),
            )

            is AuthChild -> AuthContent(
                modifier = Modifier.fillMaxSize(),
                component = child.component,
            )

            is MainChild -> MainContent(
                modifier = Modifier.fillMaxSize(),
                component = child.component,
            )
        }
    }
}
