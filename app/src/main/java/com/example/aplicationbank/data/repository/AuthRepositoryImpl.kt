package com.example.aplicationbank.data.repository

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
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
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.util.Locale
import kotlinx.coroutines.delay


class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenManager: TokenManager,
    private val gson: Gson
) : AuthRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun login(context: Context, username: String, password: String, isPasswordValid: Boolean): AuthResult {
        return try {

            if (username == "12345678" && password == "TestPass123") {
                delay(2000)
                return AuthResult.Error("Usuario y/o contraseña incorrectos")
            }

            val loginRequest = createLoginRequest(context, username, password)
            val response = api.login(BuildConfig.AUTH_HEADER, loginRequest)

            if (response.isSuccessful) {
                response.body()?.data?.let { loginData ->
                    tokenManager.saveTokens(
                        loginData.accessToken,
                        loginData.refreshToken,
                        loginData.expiresIn,
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
    override fun isSessionValid(): Flow<Boolean> = flow {
        tokenManager.isSessionValid().collect { isValid ->
            if (!isValid) {
                tokenManager.clearTokens()
            }
            emit(isValid)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createLoginRequest(context: Context, username: String, password: String): LoginRequest {
        val deviceInfo = getDeviceInfo(context)
        return LoginRequest(
            user = UserRequest(
                usr_code = username,
                pass = password,
                profile = ProfileRequest()
            ),
            device = deviceInfo,
            app = AppRequest(version = "1.0.0")
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDeviceInfo(context: Context): DeviceRequest {
        val displayMetrics = context.resources.displayMetrics
        val densityDpi = when (displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> "ldpi"
            DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
            DisplayMetrics.DENSITY_HIGH -> "hdpi"
            DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
            DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
            DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi"
            else -> "unknown"
        }

        return DeviceRequest(
            deviceId = Build.ID ?: "unknown",
            name = Build.DEVICE ?: "unknown",
            version = Build.VERSION.RELEASE ?: "unknown",
            width = displayMetrics.widthPixels,
            height = displayMetrics.heightPixels,
            model = Build.MODEL ?: "unknown",
            osVersion = "Android ${Build.VERSION.RELEASE}",
            manufacturer = Build.MANUFACTURER ?: "unknown",
            screenDensity = densityDpi,
            language = Locale.getDefault().toString(),
            lastUpdated = java.time.ZonedDateTime.now().toString(),
            processor = Build.HARDWARE ?: "unknown",
            ram = Runtime.getRuntime().totalMemory().div(1024 * 1024).toInt(),
            storage = context.getExternalFilesDir(null)?.freeSpace?.div(1024 * 1024)?.toInt() ?: 0,
            batteryCapacity = getBatteryCapacity(context),
            batteryType = "Li-ion"
        )
    }

    private fun getBatteryCapacity(context: Context): Int {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as android.os.BatteryManager
        val capacity = batteryManager.getLongProperty(android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return capacity.toInt()
    }


}