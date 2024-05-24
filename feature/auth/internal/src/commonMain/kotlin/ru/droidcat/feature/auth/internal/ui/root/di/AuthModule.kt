package ru.droidcat.feature.auth.internal.ui.root.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.droidcat.core.preferences.preferencesModule
import ru.droidcat.feature.auth.api.data.AuthLocalApi
import ru.droidcat.feature.auth.api.usecase.LogOutUseCase
import ru.droidcat.feature.auth.api.usecase.LoginByEmailUseCase
import ru.droidcat.feature.auth.api.usecase.RegisterByEmailUseCase
import ru.droidcat.feature.auth.api.usecase.TokenUseCase
import ru.droidcat.feature.auth.internal.data.AuthLocalImpl
import ru.droidcat.feature.auth.internal.data.RemoteAuthRepository
import ru.droidcat.feature.auth.internal.ui.login.di.loginModule
import ru.droidcat.feature.auth.internal.ui.register.di.registerModule
import ru.droidcat.feature.auth.internal.usecase.LogOutUseCaseImpl
import ru.droidcat.feature.auth.internal.usecase.LoginByEmailUseCaseImpl
import ru.droidcat.feature.auth.internal.usecase.RegisterByEmailUseCaseImpl

val authModule = module {
    includes(
        loginModule,
        registerModule,
        preferencesModule,
    )

    singleOf(::AuthLocalImpl).bind<AuthLocalApi>()
    singleOf(::RemoteAuthRepository)
    singleOf(::TokenUseCase)
    singleOf(::LoginByEmailUseCaseImpl).bind<LoginByEmailUseCase>()
    singleOf(::RegisterByEmailUseCaseImpl).bind<RegisterByEmailUseCase>()
    singleOf(::LogOutUseCaseImpl).bind<LogOutUseCase>()
}
