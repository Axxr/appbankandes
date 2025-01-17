package com.example.aplicationbank.domain.repository

import com.example.aplicationbank.domain.model.Product
import com.example.aplicationbank.domain.model.Transaction

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun refreshProducts(): List<Product>
    suspend fun getTransactions(productId: String): List<Transaction>
}