package com.example.aplicationbank.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicationbank.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
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

    fun shareAccountInfo() {
        viewModelScope.launch {
            state.value.product?.let { product ->
                // Implementar lógica de compartir
            }
        }
    }
}

