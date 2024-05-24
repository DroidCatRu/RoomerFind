package ru.droidcat.feature.finders.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ru.droidcat.core.ui.AsyncImage
import ru.droidcat.feature.finders.api.ui.profile.FinderProfileComponent
import ru.droidcat.feature.finders.api.ui.profile.model.FinderProfileIntent
import ru.droidcat.feature.finders.api.ui.profile.model.FinderProfileIntent.OnBackClick
import ru.droidcat.feature.finders.api.ui.profile.model.ProfileState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    component: FinderProfileComponent,
    modifier: Modifier = Modifier,
) {
    val viewState by component.viewState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(TopAppBarDefaults.windowInsets.asPaddingValues())
                    .height(64.dp),
            ) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .shadow(16.dp, CircleShape)
                        .background(MaterialTheme.colorScheme.background, CircleShape),
                    onClick = { component.accept(OnBackClick) },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
        floatingActionButton = {
            when (val state = viewState) {
                is ProfileState.Loaded -> if (state.contacts == null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        LikeButton { component.accept(FinderProfileIntent.OnLike) }
                        DislikeButton { component.accept(FinderProfileIntent.OnDislike) }
                    }
                }
                else -> {}
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { scaffoldPaddings ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (val state = viewState) {
                is ProfileState.Loaded -> Profile(
                    state = state,
                    paddings = scaffoldPaddings,
                )

                is ProfileState.Loading -> Loading()
            }
        }
    }
}

@Composable
private fun BoxScope.Profile(
    state: ProfileState.Loaded,
    paddings: PaddingValues,
) {
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .aspectRatio(0.8f),
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
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = paddings.calculateBottomPadding(),
        ),
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.9f),
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        ),
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
            ) {
                Text(
                    text = state.title,
                    style = MaterialTheme.typography.displaySmall,
                )
            }
        }

        state.priceRange?.let { price ->
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(
                            top = 4.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    text = price,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }

        state.description?.let { desc ->
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(
                            top = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    text = desc,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        state.contacts?.let { contacts ->
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                        ),
                    text = contacts,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
private fun BoxScope.Loading() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
    )
}
