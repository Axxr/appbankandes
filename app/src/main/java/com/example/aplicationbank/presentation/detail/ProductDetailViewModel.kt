package com.example.aplicationbank.presentation.detail

import GetTransactionsUseCase
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicationbank.domain.model.Currency
import com.example.aplicationbank.domain.model.Product
import com.example.aplicationbank.domain.usecase.GetProductsUseCase
import com.example.aplicationbank.domain.usecase.ShareProductInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val shareProductInfoUseCase: ShareProductInfoUseCase,
    private val productId: String
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()

    init {
        loadProductDetail()
    }

    private fun loadProductDetail() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val products = getProductsUseCase()
                val product = products.find { it.id == productId }
                val transactions = getTransactionsUseCase(productId)

                _state.value = _state.value.copy(
                    product = product,
                    transactions = transactions,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun getShareMessage(): String? {
        val product = state.value.product
        return product?.let { shareProductInfoUseCase.execute(it) }
    }

    fun clearShareMessage() {
        _state.value = _state.value.copy(shareMessage = null)
    }

}

