package com.example.aplicationbank.data.remote.dto

data class LoginRequest(
    val user: UserRequest,
    val device: DeviceRequest,
    val app: AppRequest
)

data class UserRequest(
    val usr_code: String,
    val pass: String,
    val profile: ProfileRequest
)

data class ProfileRequest(
    val language: String = "es"
)

data class DeviceRequest(
    val deviceId: String,
    val name: String,
    val version: String,
    val width: Int,
    val height: Int,
    val model: String,
    val platform: String = "android",
    val osVersion: String,
    val manufacturer: String,
    val screenDensity: String,
    val language: String,
    val lastUpdated: String,
    val processor: String,
    val ram: Int,
    val storage: Int,
    val batteryCapacity: Int,
    val batteryType: String
)

data class AppRequest(
    val version: String
)