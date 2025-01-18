package com.example.aplicationbank.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicationbank.domain.usecase.GetProductsUseCase
import com.example.aplicationbank.domain.usecase.RefreshProductsUseCase
import com.example.aplicationbank.domain.usecase.ValidateSessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val validateSessionUseCase: ValidateSessionUseCase,
    private val refreshProductsUseCase: RefreshProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _navigateToLogin = MutableStateFlow(false)
    val navigateToLogin = _navigateToLogin.asStateFlow()

    init {
        validateSessionAndLoadProducts()
    }

    private fun validateSessionAndLoadProducts() {
        viewModelScope.launch {
            try {
                val isSessionValid = validateSessionUseCase().first()
                if (isSessionValid) {
                    loadProducts()
                } else {
                    _navigateToLogin.update { true }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = e.message ?: "Error desconocido al validar sesión")
                }
            }
        }
    }


    fun loadProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val products = getProductsUseCase()
                _state.value = _state.value.copy(
                    products = products,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido al cargar productos"
                )
            }
        }
    }

    fun refreshProducts() {
        viewModelScope.launch {
            try {
                val products = refreshProductsUseCase()
                _state.value = _state.value.copy(
                    products = products,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Error al actualizar"
                )
            }
        }
    }
}