package com.example.aplicationbank.domain.model

data class Product(
    val id: String,
    val name: String,
    val type: ProductType,
    val balance: Double,
    val currency: Currency,
    val accountNumber: String,
    val cci: String
)

enum class ProductType {
    SAVINGS_ACCOUNT
}

enum class Currency {
    PEN, USD
}