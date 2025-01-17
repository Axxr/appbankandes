package com.example.aplicationbank.data.remote.api

import com.example.aplicationbank.data.remote.dto.ProductResponse
import com.example.aplicationbank.data.remote.dto.TransactionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): Response<ProductResponse>

    @GET("products/{productId}/transactions")
    suspend fun getTransactions(
        @Path("productId") productId: String
    ): Response<TransactionResponse>
}