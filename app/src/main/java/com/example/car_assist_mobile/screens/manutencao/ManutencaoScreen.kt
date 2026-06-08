package com.example.car_assist_mobile.screens.manutencao

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.car_assist_mobile.data.model.ManutencaoItemResponse

@Composable
fun ManutencaoScreen(
    navController: NavController,
    idUsuarioLogado: Int,
    idVeiculoAtual: Int,
    viewModel: ManutencaoScreenViewModel = viewModel()
) {
    LaunchedEffect(idVeiculoAtual) {
        viewModel.carregarManutencoes(idVeiculoAtual)
    }

    Scaffold(
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.Center) {
                CustomBottomBar(navController = navController, selectedItem = "garagem")
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.border(0.5.dp, Color.LightGray, CircleShape).size(45.dp)) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
                Text("MANUTENÇÕES", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 18.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (viewModel.isLoading) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF910D0D))
                }
            } else if (!viewModel.errorMessage.isNullOrEmpty()) {
                Box(modifier = Modifier.weight(1f).padding(20.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = viewModel.errorMessage!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else if (viewModel.listaManutencoes.isEmpty()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("Nenhuma manutenção registrada.", color = Color.Gray, fontSize = 14.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(viewModel.listaManutencoes) { item ->
                        CardManutencao(
                            manutencao = item,
                            viewModel = viewModel,
                            onClick = {
                                // Abre a edição sem limite de tempo
                                navController.currentBackStackEntry?.savedStateHandle?.apply {
                                    set("edit_id", item.id)
                                    set("edit_data", item.data_manutencao)
                                    set("edit_custo", item.custo)
                                    set("edit_km", item.quilometragem?.toString())
                                    set("edit_oficina", item.oficina)
                                    set("edit_pecas", item.pecas)
                                    set("edit_obs", item.observacoes)
                                    set("edit_tipo_id", item.tipo_manutencao?.id)
                                    set("edit_tipo_nome", item.tipo_manutencao?.nome)

                                    val listaUrls = item.evidencia ?: emptyList()
                                    set("edit_evidencias", ArrayList(listaUrls))
                                }
                                navController.navigate("AddManutencao/$idUsuarioLogado/$idVeiculoAtual")
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.apply {
                        remove<Int>("edit_id")
                        remove<String>("edit_data")
                        remove<String>("edit_custo")
                        remove<String>("edit_km")
                        remove<String>("edit_oficina")
                        remove<String>("edit_pecas")
                        remove<String>("edit_obs")
                        remove<Int>("edit_tipo_id")
                        remove<String>("edit_tipo_nome")
                        remove<ArrayList<String>>("edit_evidencias")
                    }
                    navController.navigate("AddManutencao/$idUsuarioLogado/$idVeiculoAtual")
                },
                modifier = Modifier.padding(bottom = 16.dp).height(48.dp).width(200.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("NOVA MANUTENÇÃO", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CardManutencao(manutencao: ManutencaoItemResponse, viewModel: ManutencaoScreenViewModel, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Manutenção", fontSize = 11.sp, color = Color.LightGray)
            Text(manutencao.tipo_manutencao?.nome ?: "Serviço Geral", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Data", fontSize = 11.sp, color = Color.LightGray)
                    Text(viewModel.formatarDataBR(manutencao.data_manutencao ?: ""), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Box(modifier = Modifier.width(1.dp).height(30.dp).background(Color(0xFFF0F0F0)))
                Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                    Text("Valor", fontSize = 11.sp, color = Color.LightGray)
                    Text(viewModel.formatarMoedaBR(manutencao.custo ?: "0.00"), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}