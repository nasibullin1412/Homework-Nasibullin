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
    val poster_path: String,
    val release_date: String
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

@Serializable
data class UserRequest(
    @SerialName("request_token")
    val requestToken: String
)

@Serializable
data class SessionIdResponse(
    val success: Boolean,
    @SerialName("session_id")
    val session_id: String
)

@Serializable
data class AvatarPathDataResponse(
    @SerialName("avatar_path")
    val avatarPath: String?
)

@Serializable
data class AvatarDataResponse(
    @SerialName("tmdb")
    val avatarPathDataResponse: AvatarPathDataResponse
)

@Serializable
data class AccountDetailResponse(
    val avatar: AvatarDataResponse,
    val id: Long,
    val name: String,
    val username: String
)