package com.ruben.spotify.api.response

import kotlinx.serialization.Serializable

sealed interface SpotifyApiResponse<out RESPONSE, out ERROR> {
    data class Success<RESPONSE>(val result: RESPONSE): SpotifyApiResponse<RESPONSE, Nothing>

    data class Error<ERROR>(val body: ERROR): SpotifyApiResponse<Nothing, ERROR>
}

@Serializable
data class ErrorBody(
    val error: Error
)

@Serializable
data class Error(
    val status: Int,
    val message: String
)