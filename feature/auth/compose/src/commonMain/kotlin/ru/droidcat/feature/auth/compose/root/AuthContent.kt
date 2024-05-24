package ru.droidcat.feature.auth.compose.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.droidcat.feature.auth.api.ui.root.AuthComponent
import ru.droidcat.feature.auth.api.ui.root.model.AuthChild.LoginChild
import ru.droidcat.feature.auth.api.ui.root.model.AuthChild.RegisterChild
import ru.droidcat.feature.auth.compose.login.LoginContent
import ru.droidcat.feature.auth.compose.register.RegisterContent

@Composable
fun AuthContent(
    component: AuthComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
    ) {
        when (val child = it.instance) {
            is LoginChild -> LoginContent(
                component = child.component,
            )

            is RegisterChild -> RegisterContent(
                component = child.component,
            )
        }
    }
}
