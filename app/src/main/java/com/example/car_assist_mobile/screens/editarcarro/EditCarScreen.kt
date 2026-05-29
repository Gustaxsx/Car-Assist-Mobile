package com.example.car_assist_mobile.screens.editarcarro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins

@Composable
fun EditCarScreen(
    navController: NavController,
    veiculoId: Int,
    viewModel: EditCarViewModel = viewModel()
) {
    LaunchedEffect(veiculoId) {
        viewModel.carregarDadosDoVeiculo(veiculoId)
    }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
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
                .padding(horizontal = 30.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Surface(
                    modifier = Modifier.size(52.dp),
                    shape = CircleShape,
                    color = Color.Transparent,
                    border = BorderStroke(1.dp, Color(0xFFEFEFEF))
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.Black, modifier = Modifier.size(22.dp))
                    }
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

            Spacer(modifier = Modifier.height(30.dp))


            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = viewModel.marca,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = viewModel.modelo.replace("_", " "),
                        fontSize = 32.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = Poppins,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Surface(
                    modifier = Modifier
                        .width(120.dp)
                        .height(80.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF9F9F9),
                    border = BorderStroke(1.dp, Color(0xFFEFEFEF))
                ) {
                    if (!viewModel.fotoUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = viewModel.fotoUrl,
                            contentDescription = "Foto do veículo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.icone_carro_moderno),
                            placeholder = painterResource(id = R.drawable.icone_carro_moderno)
                        )
                    } else {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(
                                painter = painterResource(id = R.drawable.icone_carro_moderno),
                                contentDescription = null,
                                tint = Color.LightGray,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    EditInfoItem(label = "Marca", value = viewModel.marca, onValueChange = { viewModel.marca = it }, modifier = Modifier.weight(1f))
                    EditInfoItem(label = "Placa", value = viewModel.placa, onValueChange = { viewModel.placa = it }, modifier = Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    EditInfoItem(label = "Ano", value = viewModel.ano, onValueChange = { viewModel.ano = it }, modifier = Modifier.weight(1f))
                    EditInfoItem(label = "Cor", value = viewModel.cor, onValueChange = { viewModel.cor = it }, modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterHorizontally).width(150.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("SALVAR", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun EditInfoItem(label: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(85.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFEFEFEF)),
        color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(label, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontFamily = Poppins
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}