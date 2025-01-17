package com.example.aplicationbank.data.remote.dto

data class ErrorResponse(
    val error: ErrorData
)

data class ErrorData(
    val code: Int,
    val userMessage: UserMessage
)

data class UserMessage(
    val original: String,
    val es: String,
    val ja: String
)