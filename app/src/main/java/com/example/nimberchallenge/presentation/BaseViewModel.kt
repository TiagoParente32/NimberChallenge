package com.example.nimberchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {
    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<String>()
    val navigationEvents = _navigationEvents.asSharedFlow() // Read-only access

    protected abstract fun initialState(): T

    protected fun updateUiState(update: T.() -> T) {
        _uiState.value = _uiState.value.update()
    }

    fun navigateTo(route: String) {
        viewModelScope.launch {
            _navigationEvents.emit(route)
        }
    }

    fun popBackStack() {
        viewModelScope.launch {
            _navigationEvents.emit("pop_back_stack")
        }
    }
}
