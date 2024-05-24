package ru.droidcat.feature.auth.api.model

data object InvalidCredentialsOrEmailAlreadyRegistered : Throwable(
    message = "InvalidCredentialsOrEmailAlreadyRegistered",
)
