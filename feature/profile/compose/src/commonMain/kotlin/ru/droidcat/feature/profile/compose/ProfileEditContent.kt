package ru.droidcat.feature.profile.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.droidcat.feature.profile.api.ui.profileedit.ProfileEditComponent
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnAgeChange
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnConfirm
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnContactsChange
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnDescChange
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnDismiss
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnNameChange
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState.Loaded

@Composable
fun ProfileEditContent(
    component: ProfileEditComponent,
) {
    val viewState by component.viewState.collectAsState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextButton(
                onClick = { component.accept(OnDismiss) }
            ) {
                Text("Отменить")
            }
            TextButton(
                onClick = { component.accept(OnConfirm) },
                enabled = viewState is Loaded,
            ) {
                Text("Сохранить")
            }
        }
        HorizontalDivider()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                top = 8.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp,
            )
        ) {
            (viewState as? Loaded)?.profile?.let { state ->
                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.name,
                        onValueChange = { component.accept(OnNameChange(it)) },
                        label = {
                            Text("Имя")
                        },
                    )
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.age.toString(),
                        onValueChange = { component.accept(OnAgeChange(it)) },
                        label = {
                            Text("Возраст")
                        },
                    )
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.contactInfo.orEmpty(),
                        onValueChange = { component.accept(OnContactsChange(it)) },
                        label = {
                            Text("Контакты")
                        },
                    )
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        value = state.description,
                        onValueChange = { component.accept(OnDescChange(it)) },
                        label = {
                            Text("О себе")
                        },
                    )
                }
            }
        }
    }
}
