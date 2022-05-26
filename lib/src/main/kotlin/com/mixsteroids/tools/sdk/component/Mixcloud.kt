package com.mixsteroids.tools.sdk.component

import com.mixsteroids.tools.sdk.model.request.MixcloudPagingRequestDTO
import com.mixsteroids.tools.sdk.model.response.*
import com.mixsteroids.tools.sdk.util.extension.endpointMixcloud
import com.mixsteroids.tools.sdk.util.extension.json
import com.mixsteroids.tools.sdk.util.extension.parameterPagingMixcloud
import com.mixsteroids.tools.sdk.util.extension.parameters
import io.ktor.client.*
import io.ktor.client.request.*

class Mixcloud(private val client: HttpClient, private val access_token: String? = null) {
    private val accessToken: String? = access_token

    /**
     * Get user from provided access token
     * @return MixcloudUserDTO
     * @see MixcloudUserDTO
     */
    suspend fun me(): MixcloudUserDTO? = client.get {
        endpointMixcloud("me")
        parameters(
            mapOf("access_token" to accessToken.orEmpty(), "metadata" to 1)
        )
    }

    /**
     * Get single show
     * @param username username of the uploader
     * @param slug the slug name of the
     * @param paging paging object
     * @return MixcloudShowDTO
     * @see MixcloudShowDTO
     */
    suspend fun show(
        username: String,
        slug: String,
        paging: MixcloudPagingRequestDTO? = null
    ): MixcloudShowDTO? = client.get {
        endpointMixcloud(username, slug)
        parameters(
            mapOf("access_token" to accessToken.orEmpty(), "metadata" to 1)
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * Get similar shows
     * @param username username of the uploader
     * @param slug the slug name of the
     * @param paging paging object
     * @return MixcloudSearchListDTO.Shows
     * @see MixcloudSearchListDTO.Shows
     */
    suspend fun similarShow(
        username: String,
        slug: String,
        paging: MixcloudPagingRequestDTO? = null
    ): MixcloudSearchListDTO.Shows? = client.get {
        endpointMixcloud(username, slug, "similar")
        parameters(
            mapOf("access_token" to accessToken.orEmpty(), "metadata" to 1)
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * Search for a user
     * @param q query string
     * @param paging paging object
     * @return MixcloudSearchListDTO.Users
     * @see MixcloudSearchListDTO.Users
     */
    suspend fun searchUser(
        q: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Users? = client.get {
        endpointMixcloud("search")
        parameters(
            mapOf(
                "q" to q,
                "type" to "user",
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * Search for a show
     * @param q query string
     * @param paging paging object
     * @return MixcloudSearchListDTO.Shows
     * @see MixcloudSearchListDTO.Shows
     */
    suspend fun searchShow(
        q: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Shows? = client.get {
        endpointMixcloud("search")
        parameters(
            mapOf(
                "q" to q,
                "type" to "cloudcast",
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * Search for a tag
     * @param q query string
     * @param paging paging object
     * @return MixcloudSearchListDTO.Tags
     * @see MixcloudSearchListDTO.Tags
     */
    suspend fun searchTags(
        q: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Tags? = client.get {
        endpointMixcloud("search")
        parameters(
            mapOf(
                "q" to q,
                "type" to "tag",
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * Discover
     * @param tag query tag
     * @param city city to search tag in
     * @return MixcloudTagDTO
     * @see MixcloudTagDTO
     */
    suspend fun discover(tag: String?, city: String? = ""): MixcloudTagDTO? = client.get {
        val separator = if (!tag.isNullOrEmpty() && !city.isNullOrEmpty()) "+" else ""
        endpointMixcloud(
            "discover",
            tag.orEmpty().plus(if (city.isNullOrEmpty()) "" else "${separator}city:$city").trim()
        )
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        json()
    }

    /**
     * Discover Latest or Popular
     * @param tag query tag
     * @param city city to search tag in
     * @param isPopular discover popular tags
     * @param paging paging object
     * @return MixcloudSearchListDTO.Shows?
     * @see MixcloudSearchListDTO.Shows?
     */
    suspend fun discoverPopularOrLatest(
        tag: String?,
        city: String? = "",
        isPopular: Boolean = true,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Shows? = client.get {
        val separator = if (!tag.isNullOrEmpty() && !city.isNullOrEmpty()) "+" else ""
        endpointMixcloud(
            "discover",
            tag.orEmpty().plus(if (city.isNullOrEmpty()) "" else "${separator}city:$city").trim(),
            if (isPopular) "popular" else "latest"
        )
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * Get user by username
     * @return MixcloudUserDTO
     * @see MixcloudUserDTO
     */
    suspend fun user(username: String): MixcloudUserDTO? = client.get {
        endpointMixcloud(username)
        parameters(
            mapOf("access_token" to accessToken.orEmpty(), "metadata" to 1)
        )
    }

    /**
     * User cloudcasts
     * @param username username of user
     * @param paging paging object
     * @return MixcloudSearchListDTO.Shows
     * @see MixcloudSearchListDTO.Shows
     */
    suspend fun userCloudcasts(
        username: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Shows? = client.get {
        endpointMixcloud(username, "cloudcasts")
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * User comments
     * @param username username of user
     * @param paging paging object
     * @return MixcloudSearchListDTO.Comments
     * @see MixcloudSearchListDTO.Comments
     */
    suspend fun userComments(
        username: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Comments? = client.get {
        endpointMixcloud(username, "comments")
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * User favorites
     * @param username username of user
     * @param paging paging object
     * @return MixcloudSearchListDTO.Shows
     * @see MixcloudSearchListDTO.Shows
     */
    suspend fun userFavorites(
        username: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Shows? = client.get {
        endpointMixcloud(username, "favorites")
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * User feed
     * @param username username of user
     * @param paging paging object
     * @return MixcloudSearchListDTO.Feed
     * @see MixcloudSearchListDTO.Feed
     */
    suspend fun userFeed(
        username: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Feed? = client.get {
        endpointMixcloud(username, "feed")
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * User followers
     * @param username username of user
     * @param paging paging object
     * @return MixcloudSearchListDTO.Followers
     * @see MixcloudSearchListDTO.Followers
     */
    suspend fun userFollowers(
        username: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Followers? = client.get {
        endpointMixcloud(username, "followers")
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * User following
     * @param username username of user
     * @param paging paging object
     * @return MixcloudSearchListDTO.Following
     * @see MixcloudSearchListDTO.Following
     */
    suspend fun userFollowing(
        username: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Followers? = client.get {
        endpointMixcloud(username, "following")
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * User listens
     * @param username username of user
     * @param paging paging object
     * @return MixcloudSearchListDTO.Shows
     * @see MixcloudSearchListDTO.Shows
     */
    suspend fun userListens(
        username: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Shows? = client.get {
        endpointMixcloud(username, "listens")
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * User playlists
     * @param username username of user
     * @param paging paging object
     * @return MixcloudSearchListDTO.Playlists
     * @see MixcloudSearchListDTO.Playlists
     */
    suspend fun userPlaylists(
        username: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Playlists? = client.get {
        endpointMixcloud(username, "playlists")
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }

    /**
     * User playlist cloudcasts
     * @param username username of user
     * @param paging paging object
     * @return MixcloudSearchListDTO.Shows
     * @see MixcloudSearchListDTO.Shows
     */
    suspend fun userPlaylistCloudcasts(
        username: String,
        playlist_slug: String,
        paging: MixcloudPagingRequestDTO? = null,
    ): MixcloudSearchListDTO.Shows? = client.get {
        endpointMixcloud(username, "playlists", playlist_slug, "cloudcasts")
        parameters(
            mapOf(
                "access_token" to accessToken.orEmpty(),
                "metadata" to 1,
            )
        )
        parameterPagingMixcloud(paging)
        json()
    }
}
