package com.homework.nasibullin.dataclasses
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticateResponse(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
    )

@Serializable
data class UserLogin(
    val username: String,
    val password: String,
    val request_token: String
)

@Serializable
data class MovieResponse(
    val page: Int,
    val results: List<MovieDataResponse>
)

@Serializable
data class MovieDataResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val overview: String,
    val genre_ids: List<Int>,
    val id: Long,
    val title: String,
    val vote_average: Double,
    val poster_path: String
)

@Serializable
data class GenreResponse(
    val genres: List<GenreDataResponse>
)

@Serializable
data class GenreDataResponse(
    val id: Long,
    val name: String
)

@Serializable
data class CastResponse(
    val id: Long,
    val cast: List<CastDataResponse>
)

@Serializable
data class CastDataResponse(
    @SerialName("profile_path")
    val profilePath: String?,
    val id: Long,
    val name: String
)
