package com.example.car_assist_mobile.screens.carrodetalhes

import android.widget.Toast // 💡 Importado para exibir o aviso
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.ui.platform.LocalContext // 💡 Importado para obter o contexto do Android
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins

@Composable
fun DetailsCarScreen(
    navController: NavController,
    idUsuarioLogado: Int,
    veiculoId: Int,
    viewModel: DetailsCarViewModel = viewModel(),
) {
    val context = LocalContext.current // 💡 Contexto necessário para o Toast

    LaunchedEffect(veiculoId, idUsuarioLogado) {
        viewModel.carregarDadosDoVeiculo(veiculoId, idUsuarioLogado)
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Image(
                painter = painterResource(id = R.drawable.icone_carro_branco),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 150.dp, y = 20.dp)
                    .fillMaxHeight(0.85f)
                    .width(400.dp)
                    .rotate(90f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
                    .zIndex(1f)
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
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(22.dp)
                            )
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

                Spacer(modifier = Modifier.height(50.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth(0.65f)
                ) {
                    Text(
                        text = viewModel.marca.ifBlank { "A carregar..." },
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins
                    )

                    Text(
                        text = viewModel.modelo.replace("_", " ").ifBlank { "A carregar..." },
                        fontSize = 32.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = Poppins,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(45.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxWidth(0.60f)
                ) {
                    // Botões livres para todos os acessos
                    DetailsActionRow(
                        text = "Editar Dados",
                        iconRes = R.drawable.icone_pincel
                    ) {
                        navController.navigate("EditCar/$veiculoId")
                    }

                    DetailsActionRow(
                        text = "Manutenções",
                        iconRes = R.drawable.icone_engrenagem
                    ) {
                        navController.navigate("Manutencao/$idUsuarioLogado/$veiculoId")
                    }

                    DetailsActionRow(
                        text = "Gastos",
                        iconRes = R.drawable.icone_gasto
                    ) {
                        navController.navigate("Gastos/$idUsuarioLogado/$veiculoId")
                    }

                    // 💡 O botão de Histórico agora fica visível, mas valida o clique internamente
                    DetailsActionRow(
                        text = "Histórico de Donos",
                        iconRes = R.drawable.icone_pessoas
                    ) {
                        if (viewModel.papelUsuario == "Proprietário") {
                            navController.navigate("OwnerHistory/$idUsuarioLogado/$veiculoId")
                        } else {
                            Toast.makeText(
                                context,
                                "Acesso Negado: Apenas o Proprietário pode ver o histórico de donos.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // 💡 O botão de Transferência também fica visível e valida o clique internamente
                    DetailsActionRow(
                        text = "Transferir Veículo",
                        iconRes = R.drawable.icone_transfer
                    ) {
                        if (viewModel.papelUsuario == "Proprietário") {
                            val nomeSeguro = viewModel.modelo.replace("/", "-").ifBlank { "Veiculo" }
                            val placaSegura = viewModel.placa.ifBlank { "Sem_Placa" }
                            navController.navigate("transferencia/$veiculoId/$nomeSeguro/$placaSegura")
                        } else {
                            Toast.makeText(
                                context,
                                "Acesso Negado: Apenas o Proprietário pode transferir este veículo.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsActionRow(
    text: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = Color(0xFFF5E9E9)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            color = Color.Black
        )
    }
}