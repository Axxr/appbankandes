package com.example.aplicationbank.domain.usecase

import com.example.aplicationbank.domain.model.Product
import com.example.aplicationbank.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> = repository.getProducts()
}