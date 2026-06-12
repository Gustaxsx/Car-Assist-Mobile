package com.example.car_assist_mobile.screens.historico

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.data.model.DonoDetalhado
import com.example.car_assist_mobile.ui.theme.Poppins

val VermelhoApp = Color(0xFFA61616)

@Composable
fun HistoricoDonoScreen(
    navController: NavController,
    veiculoId: Int,
    idUsuarioLogado: Int,
    viewModel: HistoricoDonoViewModel = viewModel()
) {
    val context = LocalContext.current
    var usuarioParaRemover by remember { mutableStateOf<DonoDetalhado?>(null) }

    LaunchedEffect(veiculoId) {
        viewModel.buscarHistoricoDoVeiculo(veiculoId)
    }

    // 💡 DIÁLOGO DE CONFIRMAÇÃO DE EXCLUSÃO
    if (usuarioParaRemover != null) {
        AlertDialog(
            onDismissRequest = { usuarioParaRemover = null },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White,
            title = {
                Text(
                    text = "Revogar Acesso",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            text = {
                Text(
                    text = "Tem certeza que deseja remover o acesso de ${usuarioParaRemover?.nome} a este veículo?",
                    fontFamily = Poppins,
                    color = Color.DarkGray
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val user = usuarioParaRemover
                        usuarioParaRemover = null
                        if (user != null) {
                            viewModel.removerAcesso(user.id_usuario, veiculoId) {
                                Toast.makeText(context, "Acesso removido com sucesso!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VermelhoApp)
                ) {
                    Text("Remover", fontFamily = Poppins, color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { usuarioParaRemover = null }) {
                    Text("Cancelar", fontFamily = Poppins, color = Color.Gray)
                }
            }
        )
    }

    Scaffold(
        containerColor = Color.White,
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = "HISTÓRICO DE DONOS",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (viewModel.errorMessage.isNotEmpty() && !viewModel.isLoading) {
                Text(
                    text = viewModel.errorMessage,
                    color = Color.Red,
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            if (viewModel.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = VermelhoApp)
                }
            } else if (viewModel.listaHistorico.isEmpty()) {
                Text(
                    text = "Nenhum histórico disponível para este veículo.",
                    fontFamily = Poppins,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 40.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(viewModel.listaHistorico) { historico ->
                        val isAtual = historico.is_ativo == 1
                        OwnerHistoryCard(
                            historico = historico,
                            isAtual = isAtual,
                            onRemoveClick = { usuarioParaRemover = historico } // 💡 Dispara o modal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OwnerHistoryCard(
    historico: DonoDetalhado,
    isAtual: Boolean,
    onRemoveClick: () -> Unit // 💡 Callback de clique do botão
) {

    val corBorda = if (isAtual) VermelhoApp else Color(0xFFEFEFEF)
    val corFundo = if (isAtual) Color(0xFFFFF5F5) else Color.White
    val dataFim = historico.data_desvinculo?.take(10) ?: "Atual"
    val dataInicio = historico.data_vinculo?.take(10) ?: "Desconhecido"

    // 💡 REGRA DE NEGÓCIO: Só pode excluir se for ATUAL e NÃO for o Proprietário
    val podeRemover = isAtual && historico.papel_usuario != "Proprietário"

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = corFundo,
        border = BorderStroke(1.dp, corBorda)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = Color(0xFFF0F0F0)
            ) {
                if (!historico.foto_usuario.isNullOrBlank()) {
                    AsyncImage(
                        model = historico.foto_usuario,
                        contentDescription = "Foto de ${historico.nome}",
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.icone_user),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = historico.nome,
                    fontFamily = Poppins,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = historico.papel_usuario ?: "Vínculo",
                    fontFamily = Poppins,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isAtual) VermelhoApp else Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "De $dataInicio até $dataFim",
                    fontFamily = Poppins,
                    fontSize = 11.sp,
                    color = Color.DarkGray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                if (isAtual) {
                    Surface(
                        color = VermelhoApp,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "ATUAL",
                            fontFamily = Poppins,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                } else {
                    Surface(
                        color = Color(0xFFF0F0F0),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "ANTIGO",
                            fontFamily = Poppins,
                            color = Color.Gray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // 💡 BOTÃO DE REMOVER APARECE AQUI SE A REGRA PERMITIR
                if (podeRemover) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remover Acesso",
                        tint = VermelhoApp,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { onRemoveClick() }
                    )
                }
            }
        }
    }
}