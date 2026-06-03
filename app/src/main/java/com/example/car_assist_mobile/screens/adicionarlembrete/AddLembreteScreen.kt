package com.example.car_assist_mobile.screens.lembrete

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.car_assist_mobile.screens.adicionarlembrete.AddLembreteViewModel
import com.example.car_assist_mobile.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLembreteScreen(
    navController: NavController,
    veiculoId: Int, // Podemos ignorar isso agora, já que ele vai escolher na tela
    idUsuarioLogado: Int,
    viewModel: AddLembreteViewModel = viewModel()
) {
    val context = LocalContext.current

    // 💡 Carrega a lista de veículos assim que a tela abre
    LaunchedEffect(Unit) {
        viewModel.carregarVeiculosDoUsuario(idUsuarioLogado)
    }

    // Exibe Toast
    LaunchedEffect(viewModel.mensagemSucesso, viewModel.mensagemErro) {
        if (viewModel.mensagemSucesso.isNotBlank()) {
            Toast.makeText(context, viewModel.mensagemSucesso, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
        if (viewModel.mensagemErro.isNotBlank()) {
            Toast.makeText(context, viewModel.mensagemErro, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(containerColor = Color.White) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Header
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.border(0.5.dp, Color.LightGray, CircleShape).size(45.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
                Text(
                    text = "NOVO LEMBRETE",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 💡 NOVO: Dropdown de Seleção de Veículo
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    // Mostra o nome do carro selecionado ou um texto padrão
                    value = viewModel.veiculoSelecionado?.let { "${it.modelo} (${it.placa})" } ?: "Selecione um veículo",
                    onValueChange = {},
                    readOnly = true, // Não permite digitar
                    label = { Text("Veículo Vinculado", fontFamily = Poppins) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF910D0D))
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    // Lista os carros que vieram do banco
                    viewModel.listaVeiculos.forEach { veiculo ->
                        DropdownMenuItem(
                            text = { Text("${veiculo.modelo} - ${veiculo.placa}", fontFamily = Poppins) },
                            onClick = {
                                viewModel.veiculoSelecionado = veiculo
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Título
            OutlinedTextField(
                value = viewModel.titulo,
                onValueChange = { viewModel.titulo = it },
                label = { Text("Título (Ex: Troca de Óleo)", fontFamily = Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF910D0D))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Descrição
            OutlinedTextField(
                value = viewModel.descricao,
                onValueChange = { viewModel.descricao = it },
                label = { Text("Descrição detalhada", fontFamily = Poppins) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF910D0D))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 💡 Campo Data (Agora pede o formato brasileiro)
            OutlinedTextField(
                value = viewModel.dataVencimento,
                onValueChange = {
                    // Limita a digitação para 10 caracteres (DD/MM/AAAA)
                    if (it.length <= 10) viewModel.dataVencimento = it
                },
                label = { Text("Data de Vencimento (DD/MM/AAAA)", fontFamily = Poppins) },
                placeholder = { Text("Ex: 15/12/2026") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF910D0D))
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botão Salvar
            Button(
                onClick = { viewModel.salvarLembrete(idUsuarioLogado) {} },
                modifier = Modifier.height(48.dp).fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF910D0D)),
                shape = RoundedCornerShape(24.dp),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("SALVAR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}