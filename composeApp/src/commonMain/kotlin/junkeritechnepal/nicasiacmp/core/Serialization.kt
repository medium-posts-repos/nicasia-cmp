import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val jsonFormatter = Json {
    prettyPrint = false
    ignoreUnknownKeys = true
}

inline fun <reified T> String.toObject(): T? {
    return try {
        jsonFormatter.decodeFromString<T>(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

inline fun <reified T> T.toJsonString(): String {
    return try {
        jsonFormatter.encodeToString(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}