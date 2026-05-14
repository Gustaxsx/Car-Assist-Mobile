package com.example.car_assist_mobile.screens.cardetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins

@Composable
fun DetailsCarScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomBottomBar(navController = navController, selectedItem = "garagem")
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Imagem do carro posicionada à direita e cortada, como no design
            Image(
                painter = painterResource(id = R.drawable.carro_branco),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 80.dp) // Ajuste para cortar o carro
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth(0.7f),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                // Header
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.border(0.5.dp, Color.LightGray, CircleShape).size(45.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }

                    Text(
                        text = "DADOS DO CARRO",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Marca e Nome do Carro (Alinhados à esquerda)
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Fiat",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Fastback\nAbarth",
                        fontSize = 36.sp,
                        lineHeight = 42.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = Poppins,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Lista de Ações (Botões Verticais com ícones)
                Column(
                    modifier = Modifier.fillMaxWidth(0.6f), // Limita a largura para não bater no carro
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionRow(
                        text = "Editar Dados",
                        iconRes = R.drawable.pincel // Substitua pelos seus IDs de ícone
                    ) { navController.navigate("EditCar") }

                    ActionRow(
                        text = "Manutenções",
                        iconRes = R.drawable.engrenagem
                    ) { navController.navigate("Manutencao") }

                    ActionRow(
                        text = "Gastos",
                        iconRes = R.drawable.gasto
                    ) { navController.navigate("Gastos") }

                    ActionRow(
                        text = "Histórico de Donos",
                        iconRes = R.drawable.pessoas
                    ) { /* Ação */ }

                    Spacer(modifier = Modifier.height(30.dp))

                    ActionRow(
                        text = "Transferir Veículo",
                        iconRes = R.drawable.transfer
                    ) { /* Ação de transferir */ }
                }
            }
        }
    }
}

@Composable
fun ActionRow(text: String, iconRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            color = Color(0xFFF5E9E9) // Tom rosado/cinza claro do design
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            color = Color.Black
        )
    }
}