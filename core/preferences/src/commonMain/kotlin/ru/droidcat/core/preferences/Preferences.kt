package ru.droidcat.core.preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer
import kotlin.jvm.JvmInline

/**
 * Interface for work with key-value data-base
 *
 * Include methods from [com.russhwolf.settings.coroutines.FlowSettings]
 *
 * Used custom interface for isolate dependency only in impl
 */
interface Preferences {

    suspend fun remove(key: AnyKey)

    suspend fun putInt(key: IntKey, value: Int)

    fun getIntFlow(key: IntKey, defaultValue: Int = 0): Flow<Int>

    fun getIntOrNullFlow(key: IntKey): Flow<Int?>

    suspend fun putLong(key: LongKey, value: Long)

    fun getLongFlow(key: LongKey, defaultValue: Long = 0): Flow<Long>

    fun getLongOrNullFlow(key: LongKey): Flow<Long?>

    suspend fun putString(key: StringKey, value: String)

    fun getStringFlow(key: StringKey, defaultValue: String = ""): Flow<String>

    fun getStringOrNullFlow(key: StringKey): Flow<String?>

    suspend fun putFloat(key: FloatKey, value: Float)

    fun getFloatFlow(key: FloatKey, defaultValue: Float = 0f): Flow<Float>

    fun getFloatOrNullFlow(key: FloatKey): Flow<Float?>

    suspend fun putDouble(key: DoubleKey, value: Double)

    fun getDoubleFlow(key: DoubleKey, defaultValue: Double = 0.0): Flow<Double>

    fun getDoubleOrNullFlow(key: DoubleKey): Flow<Double?>

    suspend fun putBoolean(key: BooleanKey, value: Boolean)

    fun getBooleanFlow(key: BooleanKey, defaultValue: Boolean = false): Flow<Boolean>

    fun getBooleanOrNullFlow(key: BooleanKey): Flow<Boolean?>

    suspend fun <T> putTyped(key: TypedKey<T>, value: T, serializer: SerializationStrategy<T>)

    fun <T> getTypedFlow(key: TypedKey<T>, defaultValue: T, deserializer: DeserializationStrategy<T>): Flow<T>

    fun <T> getTypedOrNullFlow(key: TypedKey<T>, deserializer: DeserializationStrategy<T>): Flow<T?>

    sealed interface AnyKey {
        val value: String
    }

    @JvmInline
    value class IntKey(override val value: String) : AnyKey

    @JvmInline
    value class LongKey(override val value: String) : AnyKey

    @JvmInline
    value class StringKey(override val value: String) : AnyKey

    @JvmInline
    value class FloatKey(override val value: String) : AnyKey

    @JvmInline
    value class DoubleKey(override val value: String) : AnyKey

    @JvmInline
    value class BooleanKey(override val value: String) : AnyKey

    @JvmInline
    value class TypedKey<T>(override val value: String) : AnyKey {
        fun toStringKey() = StringKey(value)
    }
}

suspend inline fun <reified T> Preferences.putTyped(
    key: Preferences.TypedKey<T>,
    value: T,
) {
    putTyped(key, value, serializer())
}

inline fun <reified T> Preferences.getTypedFlow(
    key: Preferences.TypedKey<T>,
    defaultValue: T,
): Flow<T> = getTypedFlow(key, defaultValue, serializer())

inline fun <reified T> Preferences.getTypedOrNullFlow(
    key: Preferences.TypedKey<T>,
): Flow<T?> = getTypedOrNullFlow(key, serializer())
