package com.example.aplicationbank.data.remote.api


import com.example.aplicationbank.data.remote.dto.LoginRequest
import com.example.aplicationbank.data.remote.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/users/login/anonymous")
    suspend fun login(
        @Header("Authorization") authHeader: String,
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}