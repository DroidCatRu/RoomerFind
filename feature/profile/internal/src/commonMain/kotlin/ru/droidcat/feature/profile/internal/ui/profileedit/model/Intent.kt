package ru.droidcat.feature.profile.internal.ui.profileedit.model

internal sealed interface Intent {

    data class OnNameChange(val name: String) : Intent

    data class OnAgeChange(val age: String) : Intent

    data class OnDescChange(val desc: String) : Intent

    data class OnEmailChange(val email: String) : Intent

    data class OnPhoneChange(val phone: String) : Intent

    data object OnConfirm : Intent
}
