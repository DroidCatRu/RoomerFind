package ru.droidcat.feature.profile.api.model

sealed interface Contacts {

    data class Defined(
        val email: String = "Без почты",
        val phone: String = "Без телефона",
    ) : Contacts

    data object Empty : Contacts
}
