package rs.raf.rafeisen.serialization.json


import kotlinx.serialization.json.Json
import timber.log.Timber

val AppJson =
    Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

inline fun <reified T> Json.decodeFromStringOrNull(string: String?): T? {
    if (string.isNullOrEmpty()) return null

    return try {
        decodeFromString(string)
    } catch (error: IllegalArgumentException) {
        Timber.w(error)
        null
    }
}
