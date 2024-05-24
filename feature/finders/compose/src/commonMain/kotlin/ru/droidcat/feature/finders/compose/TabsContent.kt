package ru.droidcat.feature.finders.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.droidcat.feature.finders.api.ui.tabs.FindersTabsComponent
import ru.droidcat.feature.finders.api.ui.tabs.model.FindersTabsChild.MatchesChild
import ru.droidcat.feature.finders.api.ui.tabs.model.FindersTabsChild.SearchChild

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TabsContent(
    component: FindersTabsComponent,
    modifier: Modifier = Modifier,
) {
    val child by component.childStack.subscribeAsState()
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = when (child.active.instance) {
                            is SearchChild -> "Предложения"
                            is MatchesChild -> "Совпадения"
                        },
                    )
                },
                actions = {
                    IconButton(
                        onClick = { component.onProfile() },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
            ) {
                NavigationBarItem(
                    selected = child.active.instance is SearchChild,
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null,
                        )
                    },
                    onClick = { component.navigateToSearch() },
                )
                NavigationBarItem(
                    selected = child.active.instance is MatchesChild,
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                        )
                    },
                    onClick = { component.navigateToMatches() },
                )
            }
        }
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
            }
        }
    }
}
