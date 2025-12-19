package com.example.produtos.data.api

import com.example.produtos.data.model.Product
import retrofit2.http.GET

interface ProductApi {

    @GET("products")
    suspend fun getProducts(): List<Product>
}
