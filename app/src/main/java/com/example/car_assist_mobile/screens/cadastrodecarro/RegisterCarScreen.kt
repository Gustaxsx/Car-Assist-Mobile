package com.example.car_assist_mobile.screens.cadastrodecarro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar

@Composable
fun RegisterCarScreen(
    navController: NavController,
    idUsuarioLogado: Int,
    viewModel: RegisterCarScreenViewModel = viewModel()
) {
    var showSuccessDialog by remember { mutableStateOf(false) }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Sucesso!", fontWeight = FontWeight.Bold) },
            text = { Text("Veículo cadastrado perfeitamente!") },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate("garagem/$idUsuarioLogado") {
                            popUpTo("garagem/{idUsuario}") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("OK", color = Color.White)
                }
            },
            containerColor = Color.White
        )
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
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(
                    onClick = { if (!viewModel.isLoading) navController.popBackStack() },
                    modifier = Modifier
                        .border(0.5.dp, Color.LightGray, CircleShape)
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
                    text = "CADASTRAR CARRO",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icone_foto),
                    contentDescription = null,
                    modifier = Modifier.size(90.dp),
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (viewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = viewModel.errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            CarInput(label = "Modelo", value = viewModel.modelo, onValueChange = { viewModel.modelo = it })
            CarInput(label = "Marca", value = viewModel.marca, onValueChange = { viewModel.marca = it })
            CarInput(label = "Placa", value = viewModel.placa, onValueChange = { viewModel.placa = it.uppercase() })
            CarInput(label = "Ano", value = viewModel.ano, onValueChange = { viewModel.ano = it }, isDropdown = true)
            CarInput(label = "Cor", value = viewModel.cor, onValueChange = { viewModel.cor = it }, isDropdown = true)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.cadastrarVeiculo(idUsuario = idUsuarioLogado) {
                        showSuccessDialog = true
                    }
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                shape = RoundedCornerShape(12.dp),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text(
                        "CADASTRAR",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CarInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isDropdown: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        Text(
            text = label,
            color = Color(0xFFBCBCBC),
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE8E8E8),
                focusedBorderColor = Color.Gray,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            trailingIcon = {
                if (isDropdown) {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(26.dp)
                            .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        )
    }
}