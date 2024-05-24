package ru.droidcat.feature.profile.api.model

sealed interface UserProfile {

    data class Defined(
        val name: String = "Без имени",
        val age: String = "0",
        val description: String = "Нет описания",
        val gender: Int = 0,
    ) : UserProfile

    data object Empty : UserProfile
}

data object NoProfile : Throwable()

data object TimeOutException : Throwable()
