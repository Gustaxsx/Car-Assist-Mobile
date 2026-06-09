package com.example.car_assist_mobile.screens.gastos

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins

// >>> IMPORT DA SUA COR CENTRALIZADA AQUI <<<
import com.example.car_assist_mobile.ui.theme.RedDesign

@Composable
fun GastosScreen(navController: NavController) {
    var isSemanalSelected by remember { mutableStateOf(true) }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .border(0.5.dp, Color.LightGray, CircleShape)
                        .size(45.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
                Text(
                    text = "GASTOS",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TabButtonDesign("Semanal", isSemanalSelected, Modifier.weight(1f)) { isSemanalSelected = true }
                TabButtonDesign("Mensal", !isSemanalSelected, Modifier.weight(1f)) { isSemanalSelected = false }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)
                ) {
                    GastoItemDesign("Combustível", "R$ 230,00") {}
                    HorizontalDivider(color = Color(0xFFF0F0F0))

                    GastoItemDesign("Limpeza", "R$ 230,00") {}
                    HorizontalDivider(color = Color(0xFFF0F0F0))

                    GastoItemDesign("Pedágio", "R$ 230,00") {}
                    HorizontalDivider(color = Color(0xFFF0F0F0))

                    GastoItemDesign("Estacionamento", "R$ 230,00") {}
                    HorizontalDivider(color = Color(0xFFF0F0F0))

                    GastoItemDesign("Manutenção", "R$ 230,00") {}
                    HorizontalDivider(color = Color(0xFFF0F0F0))

                    GastoItemDesign("Multas", "R$ 230,00") {}

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total",
                            color = RedDesign, // Usando a cor do app
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins
                        )
                        Text(
                            text = "R$ 1380,00",
                            color = RedDesign, // Usando a cor do app
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // >>> BOTÃO PRINCIPAL ATUALIZADO <<<
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth() // Padronizado com as outras telas
                    .height(55.dp), // Altura padronizada
                colors = ButtonDefaults.buttonColors(containerColor = RedDesign), // Cor do app
                shape = RoundedCornerShape(24.dp) // Borda mais arredondada
            ) {
                Text(
                    text = "Inserir Novo Valor",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = Poppins,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun TabButtonDesign(text: String, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier
            .height(45.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        // Fundo vermelho se selecionado, cinza bem clarinho se não
        color = if (isSelected) RedDesign else Color(0xFFF5F5F5)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                // Texto branco se selecionado, cinza escuro se não
                color = if (isSelected) Color.White else Color.Gray,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun GastoItemDesign(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            color = Color(0xFF424242),
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontFamily = Poppins
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Ver detalhes",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}