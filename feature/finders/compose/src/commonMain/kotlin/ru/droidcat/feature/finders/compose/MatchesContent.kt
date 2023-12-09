package ru.droidcat.feature.finders.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.droidcat.feature.finders.api.ui.matches.MatchesComponent
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState.Loaded

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesContent(
    component: MatchesComponent,
    modifier: Modifier = Modifier,
) {
    val viewState by component.viewState.subscribeAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Совпадения",
                    )
                },
                actions = {
                    IconButton(
                        onClick = component::onProfileRequest,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                        )
                    }
                },
            )
        }
    ) { scaffoldPadding ->
        (viewState as? Loaded)?.matches?.takeIf { it.isNotEmpty() }?.let { matches ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
            ) {
                items(matches) { match ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { component.onSelect() },
                    ) {
                        match.name.takeIf { it.isNotBlank() }?.let { name ->
                            Text(
                                text = "Имя: $name",
                            )
                        }
                        match.description.takeIf { it.isNotBlank() }?.let { desc ->
                            Text(
                                text = "Описание: $desc",
                            )
                        }
                        match.age.takeIf { it.isNotBlank() }?.let { age ->
                            Text(
                                text = "Возраст: $age",
                            )
                        }
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Нет совпадений",
            )
        }
    }
}
