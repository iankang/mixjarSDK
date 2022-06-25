package com.mixsteroids.tools.sdk.component

import com.mixsteroids.tools.sdk.model.response.*
import com.mixsteroids.tools.sdk.util.extension.endpointHearthis
import com.mixsteroids.tools.sdk.util.extension.parameters
import io.ktor.client.*
import io.ktor.client.request.*
import java.util.Date

open class HearthisComponent(private val client: HttpClient) {

    /**
     * Get feed
     * @param page page to show
     * @param count entries per page (max: 20)
     * @param duration duration (+/- 5 minutes)
     * @param type empty / popular / new
     * @param category empty / house / drumandbass / etc. - see genre API
     * @param showFeedStart Start Date
     * @param showFeedEnd End Date
     * @return HearthisTrackList
     * @see HearthisTrackList
     */
    suspend fun getFeed(
        page: Int? = 1,
        count: Int? = 5,
        duration: Int? = null,
        type: String? = null,
        category: String? = null,
        showFeedStart: Date? = null,
        showFeedEnd: Date? = null,
    ): HearthisTrackList = client.get {
        endpointHearthis("feed")
        parameters(
            mapOf(
                "page" to page.toString(),
                "count" to count.toString(),
                "duration" to duration.toString(),
                "type" to type.toString(),
                "category" to category.toString(),
                //"show-feed-start" to showFeedStart?
                //"show-feed-end" to showFeedEnd?
            )
        )
    }

    private suspend inline fun <reified T> search(
        t: String,
        type: String,
        page: Int? = 1,
        count: Int? = 10
    ): T = client.get {
        endpointHearthis("search")
        parameters(
            mapOf(
                "t" to t,
                "page" to page.toString(),
                "count" to count.toString(),
                "type" to type,
            )
        )
    }

    /**
     * Search for a track
     * @param t search string
     * @param page page
     * @param count number of items
     * @return HearthisTrackList
     * @see HearthisTrackList
     */
    suspend fun searchTrack(
        t: String,
        page: Int? = 1,
        count: Int? = 10,
    ): HearthisTrackList = search(t, "tracks", page, count)

    /**
     * Search for a playlist
     * @param t search string
     * @param page page
     * @param count number of items
     * @return HearthisPlaylistList
     * @see HearthisPlaylistList
     */
    suspend fun searchPlaylist(
        t: String,
        page: Int? = 1,
        count: Int? = 10,
    ): HearthisPlaylistList? = search(t, "playlists", page, count)

    /**
     * Search for a user
     * @param t search string
     * @param page page
     * @param count number of items
     * @return HearthisUserList
     * @see HearthisUserList
     */
    suspend fun searchUser(
        t: String,
        page: Int? = 1,
        count: Int? = 10,
    ): HearthisUserList? = search(t, "user", page, count)

    /**
     * gets all available genres/categories
     * @return HearthisCategoryList
     * @see HearthisCategoryList
     */
    suspend fun getCategories(): HearthisCategoryList? = client.get {
        endpointHearthis("categories")
    }

    /**
     * gets all genre tracks
     * @param category category name
     * @param page page
     * @param count number of items
     * @return HearthisTrackList
     * @see HearthisTrackList
     */
    suspend fun getCategoryTracks(category: String, page: Int? = 1, count: Int? = 10): HearthisTrackList? = client.get {
        endpointHearthis("categories", category)
        parameters(
            mapOf(
                "page" to page.toString(),
                "count" to count.toString(),
            )
        )
    }

    /**
     * gets a single artist/user
     * @param username name of the artist
     * @return HearthisUserDTO
     * @see HearthisUserDTO
     */
    suspend fun getUser(username: String): HearthisUserDTO? = client.get {
        endpointHearthis(username)
    }

    /**
     * gets a single track information
     * @param username name of artist
     * @param track_name name of track
     * @return HearthisTrackDTO
     * @see HearthisTrackDTO
     */
    suspend fun getTrack(username: String, track_name: String): HearthisTrackDTO? = client.get {
        endpointHearthis(username, track_name)
    }
}
