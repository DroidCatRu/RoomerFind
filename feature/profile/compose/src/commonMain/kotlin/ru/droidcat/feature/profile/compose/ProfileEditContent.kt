package ru.droidcat.feature.profile.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.droidcat.feature.profile.api.ui.profileedit.ProfileEditComponent
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState.Loaded

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditContent(
    component: ProfileEditComponent,
    modifier: Modifier = Modifier,
) {
    val viewState by component.viewState.subscribeAsState()

    (viewState as? Loaded)?.let { state ->
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Редактирование профиля",
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = component::onDismiss,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = component::onConfirm,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Done,
                                contentDescription = null,
                            )
                        }
                    }
                )
            }
        ) { scaffoldPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.name,
                        onValueChange = component::onNameChange,
                        label = { Text("Имя") },
                    )
                }

                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.age,
                        onValueChange = component::onAgeChange,
                        label = { Text("Возраст") },
                    )
                }

                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.description,
                        onValueChange = component::onDescriptionChange,
                        label = { Text("Описание") },
                    )
                }

                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.email,
                        onValueChange = component::onEmailChange,
                        label = { Text("Почта") },
                    )
                }

                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.phone,
                        onValueChange = component::onPhoneChange,
                        label = { Text("Телефон") },
                    )
                }
            }
        }
    }
}
