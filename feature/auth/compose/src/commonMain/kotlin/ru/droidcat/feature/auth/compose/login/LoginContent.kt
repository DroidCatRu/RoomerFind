package ru.droidcat.feature.auth.compose.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.droidcat.feature.auth.api.ui.login.LoginComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    component: LoginComponent,
    modifier: Modifier = Modifier,
) {
    val viewState by component.viewState.subscribeAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Вход в аккаунт",
                    )
                },
            )
        },
    ) { scaffoldPaddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    paddingValues = scaffoldPaddings,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = viewState.login,
                    onValueChange = component::onLoginChange,
                    label = {
                        Text(
                            text = "Логин",
                        )
                    },
                )
                OutlinedTextField(
                    value = viewState.password,
                    onValueChange = component::onPasswordChange,
                    label = {
                        Text(
                            text = "Пароль",
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                )
                Button(
                    onClick = component::onConfirm,
                ) {
                    Text(
                        text = "Войти в аккаунт",
                    )
                }
                Button(
                    onClick = component::onRegisterRequest,
                ) {
                    Text(
                        text = "Регистрация",
                    )
                }
            }
        }
    }
}
