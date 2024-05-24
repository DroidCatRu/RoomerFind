package ru.droidcat.core.preferences

import com.russhwolf.settings.coroutines.FlowSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

@Suppress("OPT_IN_USAGE")
internal class PreferencesImpl(private val flowSettings: FlowSettings) : AdvancedPreferences {

    override suspend fun clear() =
        flowSettings.clear()

    override suspend fun remove(key: Preferences.AnyKey) =
        flowSettings.remove(key.value)

    override suspend fun keys(): Set<String> =
        flowSettings.keys()

    override suspend fun size(): Int =
        flowSettings.size()

    override suspend fun hasKey(key: Preferences.AnyKey): Boolean =
        flowSettings.hasKey(key.value)

    override suspend fun putInt(key: Preferences.IntKey, value: Int) =
        flowSettings.putInt(key.value, value)

    override fun getIntFlow(key: Preferences.IntKey, defaultValue: Int): Flow<Int> =
        flowSettings.getIntFlow(key.value, defaultValue)

    override suspend fun getInt(key: Preferences.IntKey, defaultValue: Int): Int =
        flowSettings.getInt(key.value, defaultValue)

    override fun getIntOrNullFlow(key: Preferences.IntKey): Flow<Int?> =
        flowSettings.getIntOrNullFlow(key.value)

    override suspend fun getIntOrNull(key: Preferences.IntKey): Int? =
        flowSettings.getIntOrNull(key.value)

    override suspend fun putLong(key: Preferences.LongKey, value: Long) =
        flowSettings.putLong(key.value, value)

    override fun getLongFlow(key: Preferences.LongKey, defaultValue: Long): Flow<Long> =
        flowSettings.getLongFlow(key.value, defaultValue)

    override suspend fun getLong(key: Preferences.LongKey, defaultValue: Long): Long =
        flowSettings.getLong(key.value, defaultValue)

    override fun getLongOrNullFlow(key: Preferences.LongKey): Flow<Long?> =
        flowSettings.getLongOrNullFlow(key.value)

    override suspend fun getLongOrNull(key: Preferences.LongKey): Long? =
        flowSettings.getLongOrNull(key.value)

    override suspend fun putString(key: Preferences.StringKey, value: String) =
        flowSettings.putString(key.value, value)

    override fun getStringFlow(key: Preferences.StringKey, defaultValue: String): Flow<String> =
        flowSettings.getStringFlow(key.value, defaultValue)

    override suspend fun getString(key: Preferences.StringKey, defaultValue: String): String =
        flowSettings.getString(key.value, defaultValue)

    override fun getStringOrNullFlow(key: Preferences.StringKey): Flow<String?> =
        flowSettings.getStringOrNullFlow(key.value)

    override suspend fun getStringOrNull(key: Preferences.StringKey): String? =
        flowSettings.getStringOrNull(key.value)

    override suspend fun putFloat(key: Preferences.FloatKey, value: Float) =
        flowSettings.putFloat(key.value, value)

    override fun getFloatFlow(key: Preferences.FloatKey, defaultValue: Float): Flow<Float> =
        flowSettings.getFloatFlow(key.value, defaultValue)

    override suspend fun getFloat(key: Preferences.FloatKey, defaultValue: Float): Float =
        flowSettings.getFloat(key.value, defaultValue)

    override fun getFloatOrNullFlow(key: Preferences.FloatKey): Flow<Float?> =
        flowSettings.getFloatOrNullFlow(key.value)

    override suspend fun getFloatOrNull(key: Preferences.FloatKey): Float? =
        flowSettings.getFloatOrNull(key.value)

    override suspend fun putDouble(key: Preferences.DoubleKey, value: Double) =
        flowSettings.putDouble(key.value, value)

    override fun getDoubleFlow(key: Preferences.DoubleKey, defaultValue: Double): Flow<Double> =
        flowSettings.getDoubleFlow(key.value, defaultValue)

    override suspend fun getDouble(key: Preferences.DoubleKey, defaultValue: Double): Double =
        flowSettings.getDouble(key.value, defaultValue)

    override fun getDoubleOrNullFlow(key: Preferences.DoubleKey): Flow<Double?> =
        flowSettings.getDoubleOrNullFlow(key.value)

    override suspend fun getDoubleOrNull(key: Preferences.DoubleKey): Double? =
        flowSettings.getDoubleOrNull(key.value)

    override suspend fun putBoolean(key: Preferences.BooleanKey, value: Boolean) =
        flowSettings.putBoolean(key.value, value)

    override fun getBooleanFlow(key: Preferences.BooleanKey, defaultValue: Boolean): Flow<Boolean> =
        flowSettings.getBooleanFlow(key.value, defaultValue)

    override suspend fun getBoolean(key: Preferences.BooleanKey, defaultValue: Boolean): Boolean =
        flowSettings.getBoolean(key.value, defaultValue)

    override fun getBooleanOrNullFlow(key: Preferences.BooleanKey): Flow<Boolean?> =
        flowSettings.getBooleanOrNullFlow(key.value)

    override suspend fun getBooleanOrNull(key: Preferences.BooleanKey): Boolean? =
        flowSettings.getBooleanOrNull(key.value)

    override suspend fun <T> putTyped(
        key: Preferences.TypedKey<T>,
        value: T,
        serializer: SerializationStrategy<T>,
    ) {
        val encoded = jsonConfig.encodeToString(serializer, value)
        putString(key.toStringKey(), encoded)
    }

    override fun <T> getTypedFlow(
        key: Preferences.TypedKey<T>,
        defaultValue: T,
        deserializer: DeserializationStrategy<T>
    ): Flow<T> = getTypedOrNullFlow(key, deserializer).map { it ?: defaultValue }

    override fun <T> getTypedOrNullFlow(
        key: Preferences.TypedKey<T>,
        deserializer: DeserializationStrategy<T>
    ): Flow<T?> = getStringOrNullFlow(key.toStringKey()).map { stringValue ->
        try {
            stringValue?.let { jsonConfig.decodeFromString(deserializer, it) }
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}

private val jsonConfig = Json {
    ignoreUnknownKeys = true
    allowSpecialFloatingPointValues = true
    encodeDefaults = true
}
