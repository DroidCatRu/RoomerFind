package ru.droidcat.feature.profile.api.data

import ru.droidcat.feature.auth.api.model.UserSession
import ru.droidcat.feature.profile.api.model.Contacts
import ru.droidcat.feature.profile.api.model.Geo
import ru.droidcat.feature.profile.api.model.UserPreference
import ru.droidcat.feature.profile.api.model.UserProfile

interface ProfileApi {

    suspend fun getProfile(
        session: UserSession.Defined,
    ): UserProfile.Defined

    suspend fun saveProfile(
        session: UserSession.Defined,
        profile: UserProfile.Defined,
    )

    suspend fun getPreference(
        session: UserSession.Defined,
    ): UserPreference.Defined

    suspend fun savePreference(
        session: UserSession.Defined,
        preference: UserPreference.Defined,
    )

    suspend fun getGeo(
        session: UserSession.Defined,
    ): Geo.Defined

    suspend fun saveGeo(
        session: UserSession.Defined,
        geo: Geo.Defined,
    )

    suspend fun clearGeo(
        session: UserSession.Defined,
    )

    suspend fun getContacts(
        session: UserSession.Defined,
    ): Contacts.Defined

    suspend fun saveContacts(
        session: UserSession.Defined,
        contacts: Contacts.Defined,
    )
}
