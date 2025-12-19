package com.example.produtos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.produtos.data.model.Product
import com.example.produtos.ui.state.UiState
import com.example.produtos.ui.viewmodel.ProductViewModel
import com.example.produtos.ui.viewmodel.SettingsViewModel



@Composable
fun ProductListScreen(
    onItemClick: (Int) -> Unit,
    onSettingsClick: () -> Unit,
    settingsViewModel: SettingsViewModel
) {
    val productViewModel: ProductViewModel = viewModel()

    val state by productViewModel.uiState.collectAsState()
    val productLimit by settingsViewModel.productLimit.collectAsState()

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0D47A1))
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "CATÁLOGO", color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Lista de produtos disponíveis",
                color = Color.White.copy(alpha = 0.85f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clickable { onSettingsClick() }
                .padding(16.dp)
        ) {
            Text(text = "Configurações", color = Color(0xFF0D47A1))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(text = "Pesquisar produto")
            Spacer(modifier = Modifier.height(6.dp))
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Digite o nome do produto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        Text(
            text = "Produtos",
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
            color = Color.DarkGray
        )

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
                val allProducts =
                    (state as UiState.Success<List<Product>>).data

                val filteredProducts = allProducts
                    .filter { it.title.contains(searchText, ignoreCase = true) }
                    .take(productLimit)

                LazyColumn {
                    items(filteredProducts) { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable { onItemClick(product.id) }
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color(0xFF0D47A1), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = product.title.first().toString(),
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = product.title, maxLines = 1)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "R$ ${product.price}",
                                    color = Color.Gray
                                )
                            }
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
