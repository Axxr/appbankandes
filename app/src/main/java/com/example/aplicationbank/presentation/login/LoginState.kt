package com.example.aplicationbank.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = "",
    val error: String? = null,
    val isSuccess: Boolean = false,
    val isPasswordValid: Boolean = false,
)