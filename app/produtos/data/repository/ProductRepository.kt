package com.example.produtos.data.repository

import com.example.produtos.data.api.RetrofitInstance
import com.example.produtos.data.model.Product

class ProductRepository {

    suspend fun getProducts(): List<Product> {
        return RetrofitInstance.api.getProducts()
    }
}