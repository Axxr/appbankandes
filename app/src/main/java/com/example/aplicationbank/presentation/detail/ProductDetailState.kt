package com.example.aplicationbank.presentation.detail

import com.example.aplicationbank.domain.model.Product
import com.example.aplicationbank.domain.model.Transaction

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val transactions: List<Transaction> = emptyList(),
    val error: String? = null,
    val shareMessage: String? = null
)