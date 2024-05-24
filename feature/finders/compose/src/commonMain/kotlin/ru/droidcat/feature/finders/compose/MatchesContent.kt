package ru.droidcat.feature.finders.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.droidcat.feature.finders.api.ui.matches.MatchesComponent
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesIntent
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState.Loaded

@Composable
fun MatchesContent(
    component: MatchesComponent,
    modifier: Modifier = Modifier,
) {
    val viewState by component.viewState.collectAsState()

    (viewState as? Loaded)?.matches?.takeIf { it.isNotEmpty() }?.let { matches ->
        LazyColumn(
            modifier = modifier,
        ) {
            items(matches) { match ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { component.accept(MatchesIntent.OnSelect(match.id)) }
                        .padding(16.dp),
                ) {
                    Text(
                        text = match.title,
                    )
                    match.description.takeIf { !it.isNullOrBlank() }?.let { desc ->
                        Text(
                            text = desc,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    } ?: Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Нет совпадений",
        )
    }
}
