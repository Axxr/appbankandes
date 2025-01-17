package com.example.aplicationbank.domain.repository
import com.example.aplicationbank.domain.model.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(username: String, password: String): AuthResult
    fun isSessionValid(): Flow<Boolean>
}