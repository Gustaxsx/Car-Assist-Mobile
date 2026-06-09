package com.example.car_assist_mobile.screens.adicionarcarro

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.screens.adquirircarro.AcquireCarScreenViewModel
import com.example.car_assist_mobile.screens.adquirircarro.AcquireCarState

// Definição da cor principal do app
val AppPrimaryColor = Color(0xFFA61616)

@Composable
fun AcquireCarScreen(
    navController: NavController,
    idUsuarioLogado: Int,
    viewModel: AcquireCarScreenViewModel = viewModel()
) {
    var codigoTransferencia by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Observador de Estado (Lida com o sucesso ou erro da API)
    LaunchedEffect(uiState) {
        when (uiState) {
            is AcquireCarState.Success -> {
                Toast.makeText(context, "Carro transferido com sucesso!", Toast.LENGTH_SHORT).show()
                viewModel.resetState()
                navController.navigate("garagem/$idUsuarioLogado") {
                    popUpTo("garagem/$idUsuarioLogado") { inclusive = true }
                }
            }
            is AcquireCarState.Error -> {
                val errorMessage = (uiState as AcquireCarState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

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
                .padding(horizontal = 24.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, CircleShape)
                        .size(45.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = "ADQUIRIR CARRO",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Digite o código gerado pelo\nantigo dono para confirmar a\ntransferencia",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TextField(
                    value = codigoTransferencia,
                    onValueChange = { codigoTransferencia = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFE0E0E0),
                        unfocusedContainerColor = Color(0xFFE0E0E0),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = AppPrimaryColor
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Gray // Texto cinza
                        ),
                        enabled = uiState !is AcquireCarState.Loading
                    ) {
                        Text(text = "Cancelar", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            viewModel.aceitarTransferencia(codigoTransferencia, idUsuarioLogado)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppPrimaryColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = uiState !is AcquireCarState.Loading
                    ) {
                        if (uiState is AcquireCarState.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(text = "Confirmar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}