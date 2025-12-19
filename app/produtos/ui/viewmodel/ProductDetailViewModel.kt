package com.example.produtos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.produtos.data.model.Product
import com.example.produtos.data.repository.ProductRepository
import com.example.produtos.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<Product>>(UiState.Loading)

    val uiState: StateFlow<UiState<Product>> = _uiState

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            try {
                val product = repository.getProducts().first { it.id == id }
                _uiState.value = UiState.Success(product)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Erro ao carregar produto")
            }
        }
    }
}
