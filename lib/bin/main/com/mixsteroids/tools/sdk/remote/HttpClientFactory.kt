package com.mixsteroids.tools.sdk.remote

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.http.*

class HttpClientFactory(
    log_level: HttpLogLevel = HttpLogLevel.NONE,
    timeout_ms: Long = 60_000,
) {
    private val timeoutMs = timeout_ms

    private val logLevel: LogLevel = when (log_level) {
        HttpLogLevel.ALL -> LogLevel.ALL
        HttpLogLevel.HEADERS -> LogLevel.HEADERS
        HttpLogLevel.BODY -> LogLevel.BODY
        HttpLogLevel.INFO -> LogLevel.INFO
        HttpLogLevel.NONE -> LogLevel.NONE
    }

    fun build(): HttpClient = HttpClient(OkHttp) {
        expectSuccess = true

        install(Logging) {
            logger = Logger.SIMPLE
            level = logLevel
        }

        install(JsonFeature) {
            serializer = GsonSerializer {
                setLenient()
                setPrettyPrinting()
                serializeNulls()
                disableHtmlEscaping()
            }
            acceptContentTypes = acceptContentTypes + ContentType.Any
        }

        install(HttpTimeout) {
            requestTimeoutMillis = timeoutMs
            connectTimeoutMillis = timeoutMs
            socketTimeoutMillis = timeoutMs
        }

    }
}
