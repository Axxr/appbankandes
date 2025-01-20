package com.example.aplicationbank.domain.usecase

import com.example.aplicationbank.domain.model.AuthResult
import com.example.aplicationbank.domain.repository.AuthRepository
import android.content.Context

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(context: Context, username: String, password: String, isPasswordValid: Boolean): AuthResult {
        if (username.isBlank() || password.isBlank()) {
            return AuthResult.Error("Usuario y contraseña son requeridos")
        }
        return repository.login(context, username, password, isPasswordValid)
    }
}