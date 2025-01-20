package com.example.aplicationbank.presentation.home

import com.example.aplicationbank.domain.model.Product

data class HomeState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null,
)