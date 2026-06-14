package com.example.car_assist_mobile.screens.editarcarro

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
    idUsuarioLogado: Int,
    viewModel: EditCarViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val podeEditar = viewModel.papelUsuario != "Visualizador"

    LaunchedEffect(veiculoId, idUsuarioLogado) {
        viewModel.carregarDadosDoVeiculo(veiculoId, idUsuarioLogado)
    }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
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
                .padding(horizontal = 30.dp)
                .verticalScroll(scrollState)
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
                    text = if (podeEditar) "EDITAR DADOS" else "DADOS DO VEÍCULO",
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
                    if (!viewModel.fotoUrl.isNullOrBlank() || viewModel.fotoSelecionadaUri != null) {
                        AsyncImage(
                            model = viewModel.fotoSelecionadaUri ?: viewModel.fotoUrl,
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

            if (viewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = viewModel.errorMessage,
                    color = Color.Red,
                    fontFamily = Poppins,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    textAlign = TextAlign.Center
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    EditInfoItem(label = "Marca", value = viewModel.marca, enabled = podeEditar, onValueChange = { viewModel.marca = it }, modifier = Modifier.weight(1f))
                    EditInfoItem(label = "Placa", value = viewModel.placa, enabled = podeEditar, onValueChange = { viewModel.placa = it }, modifier = Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    EditInfoItem(label = "Ano", value = viewModel.ano, enabled = podeEditar, onValueChange = { viewModel.ano = it }, modifier = Modifier.weight(1f))
                    EditInfoItem(label = "Cor", value = viewModel.cor, enabled = podeEditar, onValueChange = { viewModel.cor = it }, modifier = Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    EditInfoItem(
                        label = "Quilometragem",
                        value = viewModel.quilometragem,
                        enabled = podeEditar,
                        onValueChange = { viewModel.quilometragem = it },
                        modifier = Modifier.weight(1f),
                        keyboardType = KeyboardType.Number
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.atualizarVeiculo(veiculoId, context) {
                        Toast.makeText(context, "Veículo updated com sucesso!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).width(150.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (podeEditar) Color(0xFF910D0D) else Color(0xFFCCCCCC)
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = podeEditar && !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text(
                        text = if (podeEditar) "SALVAR" else "BLOQUEADO",
                        color = if (podeEditar) Color.White else Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun EditInfoItem(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Surface(
        modifier = modifier.height(85.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFEFEFEF)),
        color = if (enabled) Color.White else Color(0xFFF2F2F2)
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
                enabled = enabled,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = if (enabled) Color.Black else Color.Gray,
                    fontFamily = Poppins
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}