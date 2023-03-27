package com.milad.dall_e.network

import com.milad.dall_e.BuildKonfig
import com.milad.dall_e.network.model.GeneratedImage
import com.milad.dall_e.network.model.RequestBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

interface DallEApi {
    suspend fun generateImage(body: RequestBody): GeneratedImage
}

class DallEApiImpl : DallEApi {
    override suspend fun generateImage(body: RequestBody): GeneratedImage {
        return client.get {
            headers {
                openAIEndPoint("images/generations")
            }
            setBody(body)
        }.body()
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
    }

    private fun HttpRequestBuilder.openAIEndPoint(path: String) {
        url {
            takeFrom("https://api.openai.com/v1/")
            encodedPath = path
            headers {
                append(
                    HttpHeaders.Authorization,
                    BuildKonfig.OPEN_AI_KEY
                )
            }
        }

    }
}