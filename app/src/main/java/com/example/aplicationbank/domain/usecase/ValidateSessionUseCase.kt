package com.example.aplicationbank.domain.usecase

import com.example.aplicationbank.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class ValidateSessionUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.isSessionValid()
}