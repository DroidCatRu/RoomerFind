package ru.droidcat.feature.profile.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.droidcat.feature.profile.api.ui.preferencesedit.PreferenceEditComponent
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnConfirm
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnDismiss
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnMaxAgeChange
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnMaxValueChange
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnMinAgeChange
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnMinValueChange
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState.Loaded

@Composable
fun PreferenceEditContent(
    component: PreferenceEditComponent,
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
            (viewState as? Loaded)?.profile?.preferences?.let { state ->
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Диапазон цены")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = state.minPrice.toString(),
                                onValueChange = { component.accept(OnMinValueChange(it)) },
                                label = {
                                    Text("Мин")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next,
                                ),
                            )
                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = state.maxPrice.toString(),
                                onValueChange = { component.accept(OnMaxValueChange(it)) },
                                label = {
                                    Text("Макс")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next,
                                ),
                            )
                        }
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Диапазон возраста")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = state.minAge.toString(),
                                onValueChange = { component.accept(OnMinAgeChange(it)) },
                                label = {
                                    Text("Мин")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next,
                                ),
                            )
                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                value = state.maxAge.toString(),
                                onValueChange = { component.accept(OnMaxAgeChange(it)) },
                                label = {
                                    Text("Макс")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done,
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { component.accept(OnConfirm) },
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}
