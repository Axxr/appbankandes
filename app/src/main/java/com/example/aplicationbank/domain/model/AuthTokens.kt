package com.example.aplicationbank.domain.model

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: String,
    val tokenType: String
)
