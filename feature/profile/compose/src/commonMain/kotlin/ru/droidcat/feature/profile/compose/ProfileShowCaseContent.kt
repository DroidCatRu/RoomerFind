package ru.droidcat.feature.profile.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.droidcat.feature.profile.api.ui.showcase.ProfileShowCaseComponent
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileShowCaseContent(
    component: ProfileShowCaseComponent,
    modifier: Modifier = Modifier,
) {
    val viewState by component.viewState.subscribeAsState()

    (viewState as? ProfileShowCaseState.Loaded)?.let { state ->
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Профиль",
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = component::onBackRequest,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = component::onLogOutRequest,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ExitToApp,
                                contentDescription = null,
                            )
                        }
                    }
                )
            }
        ) { scaffoldPaddings ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPaddings),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    TitledField(
                        title = "Имя",
                        onClick = component::onEditProfileRequest,
                    ) {
                        Text(
                            text = state.name,
                        )
                    }
                }

                item {
                    TitledField(
                        title = "Возраст",
                        onClick = component::onEditProfileRequest,
                    ) {
                        Text(
                            text = state.age,
                        )
                    }
                }

                item {
                    TitledField(
                        title = "Описание",
                        onClick = component::onEditProfileRequest,
                    ) {
                        Text(
                            text = state.description,
                        )
                    }
                }

                item {
                    TitledField(
                        title = "Почта",
                        onClick = component::onEditProfileRequest,
                    ) {
                        Text(
                            text = state.email,
                        )
                    }
                }

                item {
                    TitledField(
                        title = "Телефон",
                        onClick = component::onEditProfileRequest,
                    ) {
                        Text(
                            text = state.phone,
                        )
                    }
                }

                item {
                    TitledField(
                        onClick = component::onEditPreferenceRequest,
                    ) {
                        Text(
                            text = "Настроить предпочтения",
                        )
                    }
                }

                item {
                    TitledField(
                        onClick = component::onGeoEditRequest,
                    ) {
                        Text(
                            text = "Настроить зону поиска",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TitledField(
    title: String = String(),
    modifier: Modifier = Modifier
        .padding(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
    spacedBy: Dp = 0.dp,
    titleAlpha: Float = 0.4F,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
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
                content()
                Text(
                    modifier = Modifier.graphicsLayer { alpha = titleAlpha },
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
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
