package ru.droidcat.feature.profile.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.droidcat.core.ui.AsyncImage
import ru.droidcat.feature.profile.api.ui.showcase.ProfileShowCaseComponent
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent.OnBack
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent.OnEditGeo
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent.OnEditPreferences
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent.OnEditProfile
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent.OnLogOut
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState.Loaded
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState.Loading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileShowCaseContent(
    component: ProfileShowCaseComponent,
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
                    onClick = { component.accept(OnBack) },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { component.accept(OnLogOut) }
            ) {
                Text("Выйти")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { scaffoldPaddings ->
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            when (val state = viewState) {
                is Loaded -> ProfileView(
                    state = state,
                    component = component,
                    paddings = scaffoldPaddings,
                )

                is Loading -> Loading()
            }
        }
    }
}

@Composable
private fun BoxScope.ProfileView(
    state: Loaded,
    component: ProfileShowCaseComponent,
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
            TitledField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        ),
                    )
                    .padding(16.dp),
                title = "Основная информация",
                onClick = { component.accept(OnEditProfile) },
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Имя: ${state.name}",
                    style = MaterialTheme.typography.displaySmall,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Возраст: ${state.age}",
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "О себе: ${state.description}",
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Контакты: ${state.contacts}",
                )
            }
        }

        item {
            TitledField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                title = "Предпочтения",
                onClick = { component.accept(OnEditPreferences) },
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Цена: от ${state.minPrice} до ${state.maxPrice}",
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Возраст: от ${state.minAge} до ${state.maxAge}",
                )
            }
        }

        item {
            TitledField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                onClick = { component.accept(OnEditGeo) },
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Настроить зону поиска",
                )
            }
        }
    }
}

@Composable
private fun TitledField(
    title: String? = null,
    modifier: Modifier = Modifier
        .padding(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
    spacedBy: Dp = 0.dp,
    titleAlpha: Float = 0.4F,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable(onClick != null) { onClick?.invoke() },
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1F),
                verticalArrangement = Arrangement.spacedBy(
                    space = spacedBy,
                    alignment = Alignment.CenterVertically,
                ),
            ) {
                title?.let {
                    Text(
                        modifier = Modifier.graphicsLayer { alpha = titleAlpha },
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                content()
            }
            onClick?.let {
                Icon(
                    modifier = Modifier.graphicsLayer { alpha = titleAlpha },
                    imageVector = Icons.Rounded.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
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
