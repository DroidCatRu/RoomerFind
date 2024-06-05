package ru.droidcat.feature.profile.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter,
    ) {
        ProfileShowCaseContent(
            component = component.showCaseComponent,
            modifier = Modifier.fillMaxSize(),
        )

        val childSlot by component.childSlot.subscribeAsState()

        AnimatedVisibility(
            visible = childSlot.child != null,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(MutableInteractionSource(), null) { component.onBackRequest() }
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
            )
        }

        AnimatedVisibility(
            visible = childSlot.child != null,
            enter = slideInVertically { it },
            exit = slideOutVertically { it },
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                )
            ) {
                when (val child = childSlot.child?.instance) {
                    is ProfileEditSlot -> ProfileEditContent(
                        component = child.component,
                    )

                    is GeoEditSlot -> GeoEditContent(
                        component = child.component,
                    )

                    is PreferenceEditSlot -> PreferenceEditContent(
                        component = child.component,
                    )

                    else -> {}
                }
            }
        }
    }
}
