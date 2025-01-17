package com.example.aplicationbank.domain.model

sealed class AuthResult {
    data class Success(val tokens: AuthTokens, val user: User) : AuthResult()
    data class Error(val message: String) : AuthResult()
}