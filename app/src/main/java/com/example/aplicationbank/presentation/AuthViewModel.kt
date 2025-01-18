package com.example.aplicationbank.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicationbank.domain.usecase.ValidateSessionUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AuthViewModel(private val validateSessionUseCase: ValidateSessionUseCase) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    val isSessionValid: StateFlow<Boolean> = validateSessionUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Lazily, true)
}