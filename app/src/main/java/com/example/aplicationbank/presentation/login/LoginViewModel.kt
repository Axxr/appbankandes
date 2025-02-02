package com.example.aplicationbank.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicationbank.domain.model.AuthResult
import com.example.aplicationbank.domain.usecase.LoginUseCase
import com.example.aplicationbank.domain.usecase.ValidateSessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val validateSessionUseCase: ValidateSessionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    init {
        validateSession()
    }

    private fun validateSession() {
        viewModelScope.launch {
            validateSessionUseCase().collect { isValid ->
                if (isValid) {
                    _state.value = _state.value.copy(isSuccess = true)
                }
            }
        }
    }

    fun onUsernameChange(username: String) {
        val filteredUsername = username.filter { it.isDigit() }.take(8)
        _state.value = _state.value.copy(username = filteredUsername)
    }


    fun onPasswordChange(password: String) {
        val isPasswordValid = password.length >= 8

        _state.value = _state.value.copy(
            password = password,
            isPasswordValid = isPasswordValid
        )
    }



    fun onLoginClick(context: Context) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = loginUseCase(context, state.value.username, state.value.password, state.value.isPasswordValid )) {
                is AuthResult.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                }
                is AuthResult.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}