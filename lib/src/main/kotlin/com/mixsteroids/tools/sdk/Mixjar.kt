package com.mixsteroids.tools.sdk

import com.mixsteroids.tools.sdk.component.HearthisComponent
import com.mixsteroids.tools.sdk.component.MixcloudComponent
import com.mixsteroids.tools.sdk.remote.HttpClientFactory
import com.mixsteroids.tools.sdk.remote.HttpLogLevel
import io.ktor.client.*


sealed class Mixjar {

    class Mixcloud(
        access_token: String? = null,
        log_level: HttpLogLevel = HttpLogLevel.NONE,
        connection_timeout_ms: Long = 60_000,
    ) : MixcloudComponent(HttpClientFactory(log_level, connection_timeout_ms).build(), access_token)

    class Hearthis(
        log_level: HttpLogLevel = HttpLogLevel.NONE,
        connection_timeout_ms: Long = 60_000,
    ) : HearthisComponent(HttpClientFactory(log_level, connection_timeout_ms).build())
}
