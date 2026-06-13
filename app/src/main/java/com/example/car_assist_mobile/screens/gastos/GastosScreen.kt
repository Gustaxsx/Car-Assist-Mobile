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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins
import com.example.car_assist_mobile.ui.theme.RedDesign
import java.text.NumberFormat
import java.util.Locale

@Composable
fun GastosScreen(
    navController: NavController,
    idUsuarioLogado: Int,
    idVeiculo: Int, // 💡 ADICIONADO: ID para buscar os dados corretos
    viewModel: GastosViewModel = viewModel()
) {
    var isSemanalSelected by remember { mutableStateOf(true) }

    // Dispara a busca na API ao abrir a tela
    LaunchedEffect(idVeiculo) {
        viewModel.carregarGastosDoVeiculo(idVeiculo)
    }

    val formatadorMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomBottomBar(
                    navController = navController,
                    selectedItem = "garagem",
                    idUsuarioLogado = idUsuarioLogado
                )
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

            // CABEÇALHO
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

            // ABAS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TabButtonDesign("Semanal", isSemanalSelected, Modifier.weight(1f)) { isSemanalSelected = true }
                TabButtonDesign("Mensal", !isSemanalSelected, Modifier.weight(1f)) { isSemanalSelected = false }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // TABELA DE GASTOS DINÂMICA
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).padding(20.dp), color = RedDesign)
                    } else if (viewModel.errorMessage != null) {
                        Text(
                            text = viewModel.errorMessage!!,
                            color = Color.Gray,
                            modifier = Modifier.padding(20.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        // RENDERIZA A LISTA DO BACKEND
                        viewModel.gastosAgrupados.entries.forEachIndexed { index, entrada ->
                            GastoItemDesign(
                                label = entrada.key,
                                value = formatadorMoeda.format(entrada.value)
                            ) {}

                            // Adiciona o divisor se não for o último item
                            if (index < viewModel.gastosAgrupados.size - 1) {
                                HorizontalDivider(color = Color(0xFFF0F0F0))
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // TOTAL
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total",
                                color = RedDesign,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Poppins
                            )
                            Text(
                                text = formatadorMoeda.format(viewModel.totalGasto),
                                color = RedDesign,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Poppins
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate("AddGasto/$idVeiculo") }, // 💡 CORRIGIDO: idVeiculo com 'V' maiúsculo
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedDesign),
                shape = RoundedCornerShape(24.dp)
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
        color = if (isSelected) RedDesign else Color(0xFFF5F5F5)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
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