import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

val jsonFormatter = Json {
    prettyPrint = false
    ignoreUnknownKeys = true
}

inline fun <reified T> String.toObject(): T? {
    return try {
        if(this.isBlank()) { return null }
        Json.decodeFromString<T>(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

inline fun <reified T> T.toJsonString(): String {
    return try {
        //serializer(): tells compiler explicitly to check the type during compile time rather than runtime ( reflection )
        // safer for ios counter parts
        Json.encodeToString(serializer(),this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

