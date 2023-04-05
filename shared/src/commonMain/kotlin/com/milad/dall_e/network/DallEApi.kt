package com.milad.dall_e.network

import com.milad.dall_e.BuildKonfig
import com.milad.dall_e.network.model.Data
import com.milad.dall_e.network.model.GeneratedImage
import com.milad.dall_e.network.model.RequestBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

interface DallEApi {
    suspend fun generateImage(body: RequestBody): Flow<Result<List<Data>>>
}

class DallEApiImpl : DallEApi {
    override suspend fun generateImage(body: RequestBody): Flow<Result<List<Data>>> {
        return safeApiCall {
            val res = client.post {
                headers {
                    openAIEndPoint("images/generations")
                }
                contentType(ContentType.Application.Json)
                setBody(body)
            }.body<GeneratedImage>()

            res.data
        }
    }

    private val client = HttpClient {
        expectSuccess = true
        install(HttpTimeout) {
            val timeout = 30000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
        install(ContentNegotiation) {
            json()
        }
        install(Logging){
            logger = CustomHttpLogger()
            level = LogLevel.ALL
        }
    }

    class CustomHttpLogger : Logger {
        override fun log(message: String) {
            print(message) // Or whatever logging system you want here
        }
    }

    private fun HttpRequestBuilder.openAIEndPoint(path: String) {
        url {
            takeFrom("https://api.openai.com/v1/images/generations")
//            encodedPath = path
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer "+BuildKonfig.OPEN_AI_KEY
                )
            }
        }
    }
}

suspend fun <T : Any?> safeApiCall(apiCall: suspend () -> T): Flow<Result<T>> =
    channelFlow {
        try {
            send(Result.success(apiCall.invoke()))
        } catch (e: RedirectResponseException) {
            send(Result.failure(exception = Throwable(e.message)))
        }
    }