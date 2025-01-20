package com.example.aplicationbank.domain.repository
import android.content.Context
import com.example.aplicationbank.domain.model.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(context: Context, username: String, password: String, isPasswordValid: Boolean): AuthResult
    fun isSessionValid(): Flow<Boolean>
}