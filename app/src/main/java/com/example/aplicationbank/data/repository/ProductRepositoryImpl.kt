package com.example.aplicationbank.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.aplicationbank.data.remote.api.ProductApi
import com.example.aplicationbank.data.remote.dto.toDomain
import com.example.aplicationbank.domain.model.Currency
import com.example.aplicationbank.domain.model.Product
import com.example.aplicationbank.domain.model.ProductType
import com.example.aplicationbank.domain.model.Transaction
import com.example.aplicationbank.domain.model.TransactionType
import com.example.aplicationbank.domain.repository.ProductRepository
import com.google.gson.Gson
import java.time.LocalDateTime

class ProductRepositoryImpl(
    private val api: ProductApi,
    private val gson: Gson
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return try {
            val response = api.getProducts()
            if (response.isSuccessful) {
                response.body()?.products?.map { it.toDomain() } ?: getMockProducts()
            } else {
                throw Exception("Error al obtener productos")
            }
        } catch (e: Exception) {
            // En modo desarrollo, retornamos datos mock
            getMockProducts()
        }
    }

    override suspend fun refreshProducts(): List<Product> {
        return try {
            val response = api.getProducts()
            if (response.isSuccessful) {
                response.body()?.products?.map { it.toDomain() }
                    ?: (getMockProducts() + getMockAdditionalProducts())
            } else {
                getMockProducts() + getMockAdditionalProducts()
            }
        } catch (e: Exception) {
            getMockProducts() + getMockAdditionalProducts()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTransactions(productId: String): List<Transaction> {
        return try {
            val response = api.getTransactions(productId)
            if (response.isSuccessful) {
                response.body()?.transactions?.map { it.toDomain() } ?: emptyList()
            } else {
                throw Exception("Error al obtener movimientos")
            }
        } catch (e: Exception) {
            // En modo desarrollo, retornamos datos mock
            getMockTransactions()
        }
    }

    private fun getMockProducts(): List<Product> = listOf(
        Product(
            id = "1",
            name = "Cuenta Soles",
            type = ProductType.SAVINGS_ACCOUNT,
            balance = 1000.80,
            currency = Currency.PEN,
            accountNumber = "109756633258",
            cci = "100975663325158"
        ),
        Product(
            id = "2",
            name = "Cuenta Dólares",
            type = ProductType.SAVINGS_ACCOUNT,
            balance = 1600.20,
            currency = Currency.USD,
            accountNumber = "209756633259",
            cci = "200975663325159"
        )
    )

    private fun getMockAdditionalProducts(): List<Product> = listOf(
        Product(
            id = "3",
            name = "Cuenta Soles",
            type = ProductType.SAVINGS_ACCOUNT,
            balance = 0.0,
            currency = Currency.PEN,
            accountNumber = "309756633260",
            cci = "300975663325160"
        ),
        Product(
            id = "4",
            name = "Cuenta Dólares",
            type = ProductType.SAVINGS_ACCOUNT,
            balance = 1200.00,
            currency = Currency.USD,
            accountNumber = "409756644260",
            cci = "400975774425160"
        )
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMockTransactions(): List<Transaction> = listOf(
        Transaction(
            id = "1",
            type = TransactionType.TRANSFER,
            amount = -25.0,
            date = LocalDateTime.of(2025, 1, 10, 18, 50),
            description = "Transferencia"
        ),
        Transaction(
            id = "2",
            type = TransactionType.PLIN,
            amount = 124.0,
            date = LocalDateTime.of(2025, 1, 10, 19, 20),
            description = "Plin"
        ),
        Transaction(
            id = "3",
            type = TransactionType.TRANSFER,
            amount = -120.0,
            date = LocalDateTime.of(2024, 12, 31, 21, 50),
            description = "Transferencia"
        ),
        Transaction(
            id = "4",
            type = TransactionType.YAPE,
            amount = 250.0,
            date = LocalDateTime.of(2024, 12, 24, 20, 20),
            description = "Yape"
        )
    )
}