package com.example.produtos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.produtos.data.model.Product
import com.example.produtos.data.repository.ProductRepository
import com.example.produtos.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<List<Product>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Product>>> = _uiState

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = repository.getProducts()
                _uiState.value = UiState.Success(products)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Erro ao carregar produtos")
            }
        }
    }
}
