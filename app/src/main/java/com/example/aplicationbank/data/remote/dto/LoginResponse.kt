package com.example.aplicationbank.data.remote.dto

data class LoginResponse(
    val data: LoginData?
)

data class LoginData(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: String,
    val tokenType: String,
    val user: UserResponse
)

data class UserResponse(
    val _id: String,
    val rbac: RbacResponse,
    val profile: ProfileResponse
)

data class RbacResponse(
    val role: String,
    val template: String
)

data class ProfileResponse(
    val language: String
)