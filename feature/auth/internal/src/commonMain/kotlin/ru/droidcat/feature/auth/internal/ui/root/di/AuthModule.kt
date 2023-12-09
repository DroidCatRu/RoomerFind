package ru.droidcat.feature.auth.internal.ui.root.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.droidcat.feature.auth.api.data.AuthApi
import ru.droidcat.feature.auth.api.data.AuthDB
import ru.droidcat.feature.auth.api.usecase.GetUserSessionUseCase
import ru.droidcat.feature.auth.api.usecase.LogOutUseCase
import ru.droidcat.feature.auth.api.usecase.LoginUseCase
import ru.droidcat.feature.auth.api.usecase.RegisterUseCase
import ru.droidcat.feature.auth.internal.data.DefaultAuthApi
import ru.droidcat.feature.auth.internal.data.DefaultAuthDB
import ru.droidcat.feature.auth.internal.ui.login.di.loginModule
import ru.droidcat.feature.auth.internal.ui.register.di.registerModule

val authModule = module {
    includes(
        loginModule,
        registerModule,
    )

    singleOf<AuthApi>(::DefaultAuthApi)
    singleOf<AuthDB>(::DefaultAuthDB)

    factoryOf(::GetUserSessionUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::LogOutUseCase)
    factoryOf(::RegisterUseCase)
}
