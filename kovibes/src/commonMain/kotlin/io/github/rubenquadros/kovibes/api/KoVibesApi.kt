package io.github.rubenquadros.kovibes.api

import io.github.rubenquadros.kovibes.api.artist.ArtistApiImpl
import io.github.rubenquadros.kovibes.api.browse.BrowseApiImpl
import io.github.rubenquadros.kovibes.api.playlist.PlaylistApiImpl
import io.github.rubenquadros.kovibes.api.recommendations.RecommendationsApiImpl
import io.github.rubenquadros.kovibes.api.search.SearchApiImpl

/**
 * KoVibesApi is the entry class for your application.
 */
object KoVibesApi {

    private val authStorage: AuthStorage by lazy { AuthStorage() }

    private val ktorService: KtorService by lazy {
        KtorService(
            authStorage = authStorage,
            ktorEngine = { getKtorEngine() },
            ktorLogger = { getKtorLogger() }
        )
    }

    private val spotifyService: SpotifyService by lazy {
        SpotifyServiceImpl(
            playlistApi = PlaylistApiImpl(ktorService),
            browseApi = BrowseApiImpl(ktorService),
            searchApi = SearchApiImpl(ktorService),
            recommendationsApi = RecommendationsApiImpl(ktorService),
            artistApi = ArtistApiImpl(ktorService)
        )
    }

    /**
     * Returns a [SpotifyService] which can be used to call Spotify APIs.
     *
     * See the [Doc to generate Client ID and Client Secret](https://developer.spotify.com/documentation/web-api/tutorials/getting-started#:~:text=of%20your%20choice.-,Set%20Up%20Your%20Account,-Login%20to%20the).
     *
     * @param clientId
     * @param clientSecret
     * @return [SpotifyService]
     */
    fun createSpotifyService(clientId: String, clientSecret: String): SpotifyService {
        validateClientId(clientId)
        validateClientSecret(clientSecret)

        authStorage.init(clientId, clientSecret)
        return spotifyService
    }

    private fun validateClientId(clientId: String) {
        require(clientId.isNotEmpty()) {
            "Client Id cannot be empty."
        }
    }

    private fun validateClientSecret(clientSecret: String) {
        require(clientSecret.isNotEmpty()) {
            "Client secret cannot be empty."
        }
    }
}