package com.example.produtos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.produtos.data.model.Product
import coil.compose.AsyncImage
import com.example.produtos.ui.state.UiState
import com.example.produtos.ui.viewmodel.ProductDetailViewModel


@Composable
fun ProductDetailScreen(
    productId: Int,
    navController: NavHostController,
    viewModel: ProductDetailViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0D47A1))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Voltar",
                color = Color.White,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Detalhes do Produto",
                color = Color.White
            )
        }

        when (state) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Carregando...")
                }
            }

            is UiState.Success -> {
                val product = (state as UiState.Success<Product>).data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    item {
                        AsyncImage(
                            model = product.image,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .background(Color.White, RoundedCornerShape(16.dp))
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(16.dp))
                                .padding(16.dp)
                        ) {
                            Text(text = "Nome")
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = product.title)
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(16.dp))
                                .padding(16.dp)
                        ) {
                            Text(text = "Preço")
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "R$ ${product.price}",
                                color = Color(0xFF0D47A1)
                            )
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(16.dp))
                                .padding(16.dp)
                        ) {
                            Text(text = "Descrição")
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = product.description)
                        }
                    }
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text((state as UiState.Error).message)
                }
            }
        }
    }
}
