package com.homework.nasibullin.dataclasses
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