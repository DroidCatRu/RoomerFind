package ru.droidcat.feature.finders.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.WindowInsetsSides.Companion
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import ru.droidcat.feature.finders.api.ui.root.FindersComponent
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.MatchesChild
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.ProfileChild
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.SearchChild

@Composable
fun FindersContent(
    component: FindersComponent,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.ime,
        modifier = modifier,
        bottomBar = {
            Surface {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            WindowInsets.safeContent.only(
                                WindowInsetsSides.Bottom + Companion.Horizontal
                            ).asPaddingValues()
                        ),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = component::selectMatches,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                        )
                    }

                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = component::selectSearch,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.List,
                            contentDescription = null,
                        )
                    }
                }
            }
        },
    ) { scaffoldPaddings ->
        Children(
            stack = component.childStack,
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddings),
        ) {
            when (val child = it.instance) {
                is MatchesChild -> MatchesContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize(),
                )

                is SearchChild -> SearchContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize(),
                )

                is ProfileChild -> ProfileContent(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
