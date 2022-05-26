package com.mixsteroids.tools.sdk.util.extension

import com.mixsteroids.tools.sdk.model.request.MixcloudPagingRequestDTO
import com.mixsteroids.tools.sdk.util.Constants
import io.ktor.client.request.*
import io.ktor.http.*


fun HttpRequestBuilder.json() {
    contentType(ContentType.Application.Json)
}

fun HttpRequestBuilder.endpointMixcloud(vararg paths: String) {
    url {
        takeFrom(Constants.MIXCLOUD_API_URL)
        path(*paths)
    }
}

fun HttpRequestBuilder.endpointHearthis(vararg paths: String) {
    url {
        takeFrom(Constants.HEARTHIS_API_URL)
        path(*paths)
    }
}

fun HttpRequestBuilder.parameters(parameters: Map<String, Any?>) {
    parameters.entries.forEach {
        parameter(it.key, it.value)
    }
}

fun HttpRequestBuilder.parameterPagingMixcloud(paging: MixcloudPagingRequestDTO?) {

    val limit = paging?.limit ?: ""
    val offset = paging?.offset ?: ""
    val since = paging?.since ?: ""
    val until = paging?.until ?: ""

    parameters(
        mapOf(
            "limit" to limit,
            "offset" to offset,
            "since" to since,
            "until" to until,
        ),
    )
}
