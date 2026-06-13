package com.example.car_assist_mobile.screens.gastos

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.car_assist_mobile.ui.theme.Poppins
import com.example.car_assist_mobile.ui.theme.RedDesign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGastoScreen(
    navController: NavController,
    idVeiculo: Int,
    viewModel: AddGastoViewModel = viewModel()
) {
    val context = LocalContext.current
    var expandirDropdown by remember { mutableStateOf(false) }

    // Escuta os eventos da ViewModel (Sucesso ou Erro)
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { evento ->
            if (evento == "Sucesso") {
                Toast.makeText(context, "Gasto cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                navController.popBackStack() // Volta para a lista de gastos
            } else {
                Toast.makeText(context, evento, Toast.LENGTH_LONG).show()
            }
        }
    }

    Scaffold(
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Cabeçalho
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
                    text = "ADICIONAR GASTO",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Campo de Valor
            OutlinedTextField(
                value = viewModel.valor,
                onValueChange = { viewModel.valor = it },
                label = { Text("Valor (Ex: 150.50)", fontFamily = Poppins) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RedDesign,
                    unfocusedBorderColor = Color(0xFFEFEFEF)
                ),
                textStyle = TextStyle(fontFamily = Poppins, fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Data
            OutlinedTextField(
                value = viewModel.dataGasto,
                onValueChange = { viewModel.dataGasto = it },
                label = { Text("Data (DD/MM/AAAA)", fontFamily = Poppins) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RedDesign,
                    unfocusedBorderColor = Color(0xFFEFEFEF)
                ),
                textStyle = TextStyle(fontFamily = Poppins, fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown de Categoria
            ExposedDropdownMenuBox(
                expanded = expandirDropdown,
                onExpandedChange = { expandirDropdown = !expandirDropdown }
            ) {
                OutlinedTextField(
                    value = viewModel.categoriasDisponiveis[viewModel.idCategoriaSelecionada] ?: "Selecione...",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria", fontFamily = Poppins) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirDropdown) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RedDesign,
                        unfocusedBorderColor = Color(0xFFEFEFEF)
                    ),
                    textStyle = TextStyle(fontFamily = Poppins, fontSize = 16.sp)
                )

                ExposedDropdownMenu(
                    expanded = expandirDropdown,
                    onDismissRequest = { expandirDropdown = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    viewModel.categoriasDisponiveis.forEach { (id, nome) ->
                        DropdownMenuItem(
                            text = { Text(nome, fontFamily = Poppins) },
                            onClick = {
                                viewModel.idCategoriaSelecionada = id
                                expandirDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botão Salvar
            Button(
                onClick = { viewModel.cadastrarNovoGasto(idVeiculo) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RedDesign),
                shape = RoundedCornerShape(24.dp),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = "Salvar Gasto",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = Poppins,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}