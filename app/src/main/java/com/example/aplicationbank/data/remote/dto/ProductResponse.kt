package com.example.aplicationbank.data.remote.dto

data class ProductResponse(
    val products: List<ProductDto>
)

data class ProductDto(
    val id: String,
    val name: String,
    val type: String,
    val balance: Double,
    val currency: String,
    val accountNumber: String?,
    val cci: String
)