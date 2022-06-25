package com.mixsteroids.tools.sdk.model.request

data class MixcloudPagingRequestDTO(
    var limit: Int?,
    var offset: Int?,
    var since: String? = null,
    var until: String? = null,
)
