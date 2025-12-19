package com.example.produtos.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {

    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled

    private val _productLimit = MutableStateFlow(6)
    val productLimit: StateFlow<Int> = _productLimit

    fun toggleDarkMode() {
        _darkModeEnabled.value = !_darkModeEnabled.value
    }

    fun setProductLimit(limit: Int) {
        _productLimit.value = limit
    }
}
