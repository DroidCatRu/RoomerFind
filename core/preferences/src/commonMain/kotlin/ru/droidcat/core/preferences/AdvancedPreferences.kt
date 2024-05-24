package ru.droidcat.core.preferences

interface AdvancedPreferences : Preferences {

    suspend fun clear()

    suspend fun keys(): Set<String>

    suspend fun size(): Int

    suspend fun hasKey(key: Preferences.AnyKey): Boolean

    suspend fun getInt(key: Preferences.IntKey, defaultValue: Int): Int

    suspend fun getIntOrNull(key: Preferences.IntKey): Int?

    suspend fun getLong(key: Preferences.LongKey, defaultValue: Long): Long

    suspend fun getLongOrNull(key: Preferences.LongKey): Long?

    suspend fun getString(key: Preferences.StringKey, defaultValue: String): String

    suspend fun getStringOrNull(key: Preferences.StringKey): String?

    suspend fun getFloat(key: Preferences.FloatKey, defaultValue: Float): Float

    suspend fun getFloatOrNull(key: Preferences.FloatKey): Float?

    suspend fun getDouble(key: Preferences.DoubleKey, defaultValue: Double): Double

    suspend fun getDoubleOrNull(key: Preferences.DoubleKey): Double?

    suspend fun getBoolean(key: Preferences.BooleanKey, defaultValue: Boolean): Boolean

    suspend fun getBooleanOrNull(key: Preferences.BooleanKey): Boolean?
}
