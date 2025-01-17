package com.example.aplicationbank.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.aplicationbank.BuildConfig
import com.example.aplicationbank.data.local.TokenManager
import com.example.aplicationbank.data.remote.api.AuthApi
import com.example.aplicationbank.data.remote.dto.AppRequest
import com.example.aplicationbank.data.remote.dto.DeviceRequest
import com.example.aplicationbank.data.remote.dto.ErrorResponse
import com.example.aplicationbank.data.remote.dto.LoginRequest
import com.example.aplicationbank.data.remote.dto.ProfileRequest
import com.example.aplicationbank.data.remote.dto.UserRequest
import com.example.aplicationbank.domain.model.AuthResult
import com.example.aplicationbank.domain.model.AuthTokens
import com.example.aplicationbank.domain.model.User
import com.example.aplicationbank.domain.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException


class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenManager: TokenManager,
    private val gson: Gson
) : AuthRepository {

    override suspend fun login(username: String, password: String): AuthResult {
        return try {
            val loginRequest = createLoginRequest(username, password)
            val response = api.login(BuildConfig.AUTH_HEADER, loginRequest)

            if (response.isSuccessful) {
                response.body()?.data?.let { loginData ->
                    tokenManager.saveTokens(
                        loginData.accessToken,
                        loginData.refreshToken,
                        loginData.expiresIn
                    )

                    AuthResult.Success(
                        AuthTokens(
                            accessToken = loginData.accessToken,
                            refreshToken = loginData.refreshToken,
                            expiresIn = loginData.expiresIn,
                            tokenType = loginData.tokenType
                        ),
                        User(
                            id = loginData.user._id,
                            username = username,
                            role = loginData.user.rbac.role,
                            language = loginData.user.profile.language
                        )
                    )
                } ?: AuthResult.Error("Invalid response from server")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                when (errorResponse.error.code) {
                    40110 -> AuthResult.Error("No existe la version de la aplicación")
                    else -> AuthResult.Error("Usuario y/o contraseña incorrectos")
                }
            }
        } catch (e: HttpException) {
            AuthResult.Error("Error de conexión")
        } catch (e: Exception) {
            AuthResult.Error("Sucedió un error inesperado")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun isSessionValid(): Flow<Boolean> = tokenManager.isSessionValid()

    private fun createLoginRequest(username: String, password: String): LoginRequest {
        return LoginRequest(
            user = UserRequest(
                usr_code = username,
                pass = password,
                profile = ProfileRequest()
            ),
            device = DeviceRequest(
                deviceId = "66261162-16d5-4ad3-9b9b-8c05a373ad60",
                name = "Galaxy S25 Ultra",
                version = "Android 14.0",
                width = 1080,
                height = 2244,
                model = "SM-S925",
                osVersion = "Android 14.0",
                manufacturer = "Samsung",
                screenDensity = "xxhdpi",
                language = "en-US",
                lastUpdated = "2025-01-10T12:00:00Z",
                processor = "Qualcomm Snapdragon 8 Elite",
                ram = 16,
                storage = 512,
                batteryCapacity = 5000,
                batteryType = "Silicon"
            ),
            app = AppRequest(version = "1.0.0")
        )
    }
}