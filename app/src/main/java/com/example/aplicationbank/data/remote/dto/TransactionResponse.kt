package com.example.aplicationbank.data.remote.dto

data class TransactionResponse(
    val transactions: List<TransactionDto>
)

data class TransactionDto(
    val id: String,
    val type: String,
    val amount: Double,
    val date: String,
    val description: String
)