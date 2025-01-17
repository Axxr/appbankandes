package com.example.aplicationbank.domain.model

import java.time.LocalDateTime

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val date: LocalDateTime,
    val description: String
)

enum class TransactionType {
    TRANSFER, PLIN, YAPE
}