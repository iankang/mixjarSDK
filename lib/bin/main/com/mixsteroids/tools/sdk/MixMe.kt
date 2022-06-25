package com.mixsteroids.tools.sdk

import com.mixsteroids.tools.sdk.component.Hearthis
import com.mixsteroids.tools.sdk.component.Mixcloud
import com.mixsteroids.tools.sdk.remote.HttpClientFactory
import com.mixsteroids.tools.sdk.remote.HttpLogLevel
import io.ktor.client.*


class MixMe(
    mixcloud_access_token: String? = null,
    log_level: HttpLogLevel = HttpLogLevel.NONE,
    connection_timeout_ms: Long = 60_000,
) {
    private val client: HttpClient = HttpClientFactory(log_level, connection_timeout_ms).build()

    val mixcloud = Mixcloud(client, mixcloud_access_token)
    val hearthis = Hearthis(client)
}
