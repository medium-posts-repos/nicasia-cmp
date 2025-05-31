package junkeritechnepal.nicasiacmp.infrastructure.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.logging.Logger
import kotlinx.serialization.json.Json
import kotlin.LazyThreadSafetyMode.SYNCHRONIZED

object KtorClientFactory {
    fun create(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            defaultRequest {
                header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS like Mac OS X)")
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}

class NetworkService {
    val httpClient = KtorClientFactory.create()

    companion object {
        val INSTANCE: NetworkService by lazy(SYNCHRONIZED) { NetworkService() }
    }

    suspend inline fun <reified T> getRequest(
        routeCode: String,
        headers: Map<String, String> = emptyMap()
    ): T {
        return try {
            val response = httpClient.get(NetworkConstants.routeFor(routeCode)) {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }
            println("GET Success: ${response.status}")
            response.body()
        } catch (e: Exception) {
            println("GET Error: ${e.message}")
            throw e // or return a default T or custom error object
        }
    }

    suspend inline fun <reified Req, reified Res> postRequest(
        routeCode: String,
        body: Req,
        headers: Map<String, String> = mapOf("Content-Type" to "application/json")
    ): Res {
        return try {
            val response = httpClient.post(NetworkConstants.routeFor(routeCode)) {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                setBody(body)
            }
            println("POST Success: ${response.status}")
            response.body()
        } catch (e: Exception) {
            println("POST Error: ${e.message}")
            throw e // or return a default Res or custom error object
        }
    }
}