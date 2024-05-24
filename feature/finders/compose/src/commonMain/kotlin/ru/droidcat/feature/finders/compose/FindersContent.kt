package ru.droidcat.feature.finders.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import io.github.alexzhirkevich.cupertino.decompose.NativeChildren
import ru.droidcat.feature.finders.api.ui.root.FindersComponent
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.FindersTabsChild
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.ProfileChild

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun FindersContent(
    component: FindersComponent,
    modifier: Modifier = Modifier,
) {
    NativeChildren(
        stack = component.childStack,
        onBack = component::onBackRequest,
        modifier = modifier,
        animation = predictiveBackAnimation(
            backHandler = component.backHandler,
            fallbackAnimation = stackAnimation(fade() + scale()),
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
