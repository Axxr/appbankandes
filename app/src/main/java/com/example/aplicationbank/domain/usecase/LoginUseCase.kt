package com.example.aplicationbank.domain.usecase

import com.example.aplicationbank.domain.model.AuthResult
import com.example.aplicationbank.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): AuthResult {
        if (username.isBlank() || password.isBlank()) {
            return AuthResult.Error("Usuario y contraseña son requeridos")
        }
        return repository.login(username, password)
    }
}