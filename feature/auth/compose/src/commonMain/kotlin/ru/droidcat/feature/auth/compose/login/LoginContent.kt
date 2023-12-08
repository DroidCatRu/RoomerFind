package ru.droidcat.feature.auth.compose.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.droidcat.feature.auth.api.login.LoginComponent

@Composable
fun LoginContent(
    component: LoginComponent,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                text = "Вход в аккаунт",
            )
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
