package ru.droidcat.feature.finders.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.droidcat.core.ui.AsyncImage
import ru.droidcat.feature.finders.api.ui.search.SearchComponent
import ru.droidcat.feature.finders.api.ui.search.model.SearchIntent
import ru.droidcat.feature.finders.api.ui.search.model.SearchIntent.OnFinderProfileTap
import ru.droidcat.feature.finders.api.ui.search.model.SearchIntent.OnUpdate
import ru.droidcat.feature.finders.api.ui.search.model.SearchState.Loaded
import ru.droidcat.feature.finders.api.ui.search.model.SearchState.Loading
import ru.droidcat.feature.finders.api.ui.search.model.SearchState.NoProfile

@Composable
fun SearchContent(
    component: SearchComponent,
    modifier: Modifier = Modifier,
) {
    val viewState by component.viewState.collectAsState()

    when (val state = viewState) {
        is Loaded -> component.ProfileFound(
            state = state,
            modifier = modifier,
        )

        is Loading -> Loading(
            modifier = modifier,
        )

        is NoProfile -> component.NoProfile(
            modifier = modifier,
        )
    }
}

@Composable
private fun SearchComponent.ProfileFound(
    state: Loaded,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 32.dp,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        ProfileCard(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .aspectRatio(0.8f),
            state = state,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            LikeButton { accept(SearchIntent.OnLike) }
            DislikeButton { accept(SearchIntent.OnDislike) }
        }
    }
}

@Composable
private fun SearchComponent.ProfileCard(
    state: Loaded,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .shadow(18.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { accept(OnFinderProfileTap(state.id)) }
            .background(MaterialTheme.colorScheme.background),
    ) {
        state.avatar?.let { url ->
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = url,
            )
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f),
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        1f to Color.Black,
                    )
                )
                .padding(top = 16.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = state.title,
                color = Color.White,
                style = MaterialTheme.typography.displaySmall,
            )
            state.priceRange?.let { price ->
                Text(
                    text = price,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            state.description?.let { desc ->
                Text(
                    text = desc,
                    maxLines = 2,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SearchComponent.NoProfile(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Нет предложений",
            )
            Button(
                onClick = { accept(OnUpdate) },
            ) {
                Text(
                    text = "Обновить",
                )
            }
        }
    }
}
