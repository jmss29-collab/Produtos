package com.example.produtos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import com.example.produtos.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel
) {
    val darkMode by viewModel.darkModeEnabled.collectAsState()
    val productLimit by viewModel.productLimit.collectAsState()

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
                text = "Configurações",
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(text = "Modo escuro")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = if (darkMode) "Ativado" else "Desativado")
                    Switch(
                        checked = darkMode,
                        onCheckedChange = { viewModel.toggleDarkMode() }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(text = "Quantidade de produtos exibidos")
                Spacer(modifier = Modifier.height(12.dp))

                listOf(3, 6, 10).forEach { limit ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.setProductLimit(limit) }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = productLimit == limit,
                            onClick = { viewModel.setProductLimit(limit) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$limit produtos")
                    }
                }
            }
        }
    }
}
