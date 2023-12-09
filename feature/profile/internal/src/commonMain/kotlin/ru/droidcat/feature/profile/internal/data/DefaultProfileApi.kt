package ru.droidcat.feature.profile.internal.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel.ALL
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.droidcat.core.mvi.ioDispatcher
import ru.droidcat.feature.auth.api.model.UserSession.Defined
import ru.droidcat.feature.profile.api.data.ProfileApi
import ru.droidcat.feature.profile.api.model.Contacts
import ru.droidcat.feature.profile.api.model.Geo
import ru.droidcat.feature.profile.api.model.UserPreference
import ru.droidcat.feature.profile.api.model.UserProfile

private const val BASE_URL = "http://31.129.42.155:8080"

private const val GET_PROFILE_ENDPOINT = "user"
private const val SAVE_PROFILE_ENDPOINT = "user-edit"

private const val GET_PREFERENCE_ENDPOINT = "user-preferences"
private const val SAVE_PREFERENCE_ENDPOINT = "user-preferences-edit"

private const val GET_GEO_ENDPOINT = "user-geopos"
private const val SAVE_GEO_ENDPOINT = "user-geopos-add"
private const val DELETE_GEO_ENDPOINT = "user-geopos-clear"

private const val GET_CONTACTS_ENDPOINT = "user-contacts"
private const val SAVE_CONTACTS_ENDPOINT = "user-contacts-edit"

class DefaultProfileApi : ProfileApi {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val httpClient = HttpClient(
        engineFactory = platformEngineFactory,
    ) {
        expectSuccess = true

        install(ContentNegotiation) {
            json(json)
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    override suspend fun getProfile(session: Defined): UserProfile.Defined {
        return withContext(ioDispatcher) {
            val profile = httpClient.get {
                bearerAuth(session.sessionId)
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(GET_PROFILE_ENDPOINT)
                }
            }.body<ProfileDTO>()

            UserProfile.Defined(
                name = profile.name,
                age = profile.age?.toString().orEmpty(),
                description = profile.description,
            )
        }
    }

    override suspend fun saveProfile(session: Defined, profile: UserProfile.Defined) {
        withContext(ioDispatcher) {
            httpClient.post {
                bearerAuth(session.sessionId)
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(SAVE_PROFILE_ENDPOINT)
                }
                contentType(ContentType.Application.Json)
                setBody(
                    ProfileDTO(
                        name = profile.name,
                        age = profile.age.takeIf { it.isNotBlank() }?.toInt(),
                        description = profile.description,
                    )
                )
            }
        }
    }

    override suspend fun getPreference(session: Defined): UserPreference.Defined {
        return withContext(ioDispatcher) {
            val preference = httpClient.get {
                bearerAuth(session.sessionId)
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(GET_PREFERENCE_ENDPOINT)
                }
            }.body<PreferenceDTO>()

            UserPreference.Defined(
                minValue = preference.minValue?.toString().orEmpty(),
                maxValue = preference.maxValue?.toString().orEmpty(),
                minAge = preference.minAge?.toString().orEmpty(),
                maxAge = preference.maxAge?.toString().orEmpty(),
            )
        }
    }

    override suspend fun savePreference(session: Defined, preference: UserPreference.Defined) {
        withContext(ioDispatcher) {
            httpClient.post {
                bearerAuth(session.sessionId)
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(SAVE_PREFERENCE_ENDPOINT)
                }
                contentType(ContentType.Application.Json)
                setBody(
                    PreferenceDTO(
                        minValue = preference.minValue.takeIf { it.isNotBlank() }?.toInt(),
                        maxValue = preference.maxValue.takeIf { it.isNotBlank() }?.toInt(),
                        minAge = preference.minAge.takeIf { it.isNotBlank() }?.toInt(),
                        maxAge = preference.maxAge.takeIf { it.isNotBlank() }?.toInt(),
                    )
                )
            }
        }
    }

    override suspend fun getGeo(session: Defined): Geo.Defined {
        return withContext(ioDispatcher) {
            val geo = httpClient.get {
                bearerAuth(session.sessionId)
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(GET_GEO_ENDPOINT)
                }
            }.body<GeoDTO>()

            Geo.Defined(
                lat = geo.lat,
                lon = geo.lon,
                radius = geo.radius,
            )
        }
    }

    override suspend fun saveGeo(session: Defined, geo: Geo.Defined) {
        withContext(ioDispatcher) {
            httpClient.post {
                bearerAuth(session.sessionId)
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(SAVE_GEO_ENDPOINT)
                }
                contentType(ContentType.Application.Json)
                setBody(
                    GeoDTO(
                        lat = geo.lat,
                        lon = geo.lon,
                        radius = geo.radius,
                    )
                )
            }
        }
    }

    override suspend fun clearGeo(session: Defined) {
        withContext(ioDispatcher) {
            httpClient.post {
                bearerAuth(session.sessionId)
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(DELETE_GEO_ENDPOINT)
                }
            }
        }
    }

    override suspend fun getContacts(session: Defined): Contacts.Defined {
        return withContext(ioDispatcher) {
            val contacts = httpClient.get {
                bearerAuth(session.sessionId)
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(GET_CONTACTS_ENDPOINT)
                }
            }.body<ContactsDTO>()

            Contacts.Defined(
                email = contacts.email.orEmpty(),
                phone = contacts.phone.orEmpty(),
            )
        }
    }

    override suspend fun saveContacts(session: Defined, contacts: Contacts.Defined) {
        withContext(ioDispatcher) {
            httpClient.post {
                bearerAuth(session.sessionId)
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(SAVE_CONTACTS_ENDPOINT)
                }
                contentType(ContentType.Application.Json)
                setBody(
                    ContactsDTO(
                        email = contacts.email.takeIf { it.isNotBlank() },
                        phone = contacts.phone.takeIf { it.isNotBlank() },
                    )
                )
            }
        }
    }
}

@Serializable
data class ProfileDTO(
    val name: String = String(),
    val age: Int? = null,
    val description: String = String(),
    val gender: Int = 0,
)

@Serializable
data class ContactsDTO(
    val email: String? = null,
    val phone: String? = null,
    val priority: Int = 0,
)

@Serializable
data class PreferenceDTO(
    @SerialName("min_value") val minValue: Int? = null,
    @SerialName("max_value") val maxValue: Int? = null,
    @SerialName("min_age") val minAge: Int? = null,
    @SerialName("max_age") val maxAge: Int? = null,
)

@Serializable
data class GeoDTO(
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("rad") val radius: Double,
)
