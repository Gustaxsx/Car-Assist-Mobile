package com.example.car_assist_mobile.screens.garagem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins

val CorPrimariaVermelha = Color(0xFF910D0D)
val FundoGeral = Color(0xFFFFFFFF)
val BadgeRosadaCard = Color(0xFFF5E9E9)

@Composable
fun GaragemScreen(
    navController: NavController,
    idUsuarioLogado: Int = 0,
    viewModel: GaragemScreenViewModel = viewModel()
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    LaunchedEffect(idUsuarioLogado) {
        if (idUsuarioLogado == 0) {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    LaunchedEffect(currentRoute) {
        if (currentRoute?.startsWith("garagem") == true && idUsuarioLogado != 0) {
            if (viewModel.listaVeiculos.isEmpty()) {
                viewModel.buscarVeiculosDaGaragem(idUsuarioLogado)
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            CustomBottomBar(
                navController = navController,
                selectedItem = "garagem",
                idUsuarioLogado = idUsuarioLogado
            )
        },
        containerColor = FundoGeral
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            Box(
                modifier = Modifier
                    .size(350.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 100.dp, y = (-100).dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(CorPrimariaVermelha.copy(alpha = 0.05f), Color.Transparent)
                        ),
                        shape = CircleShape
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icone_perfil1),
                        contentDescription = null,
                        modifier = Modifier.size(52.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                        Text(
                            "Olá Nikolas!",
                            fontFamily = Poppins,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            "id_usuario: $idUsuarioLogado",
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    IconeRedondoHeader(iconRes = R.drawable.icone_chat, onClick = { navController.navigate("chatbot") })
                    Spacer(modifier = Modifier.width(12.dp))
                    IconeRedondoHeader(iconRes = R.drawable.icone_envelope, onClick = { navController.navigate("Lembrete") }, temNotificacao = true)
                }

                Spacer(modifier = Modifier.height(30.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth().height(85.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                if (viewModel.listaVeiculos.isEmpty()) "--" else "91.6",
                                fontFamily = Poppins,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Box(modifier = Modifier.width(1.dp).height(30.dp).background(Color(0xFFE0E0E0)))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "SCORE DA GARAGEM",
                                fontFamily = Poppins,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(painterResource(id = R.drawable.icone_grafico), null, tint = CorPrimariaVermelha, modifier = Modifier.size(22.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (viewModel.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = CorPrimariaVermelha)
                    }
                } else if (viewModel.listaVeiculos.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icone_carro_moderno),
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Sua garagem está vazia.\nCadastre um veículo para começar!",
                            fontFamily = Poppins,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 120.dp)
                    ) {
                        items(viewModel.listaVeiculos) { veiculo ->
                            CardCarroDesign(
                                nome = veiculo.modelo,
                                placa = veiculo.placa,
                                score = veiculo.score ?: "100.0",
                                imageRes = R.drawable.icone_carro_moderno
                            ) {
                                navController.navigate("DetailsCar")
                            }
                        }
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
                    .clickable { navController.navigate("AddCar/$idUsuarioLogado") },
                color = BadgeRosadaCard,
                shape = RoundedCornerShape(25.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painterResource(id = R.drawable.icone_mais), null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "ADICIONAR CARRO",
                        fontFamily = Poppins,
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Composable
fun IconeRedondoHeader(iconRes: Int, onClick: () -> Unit, temNotificacao: Boolean = false) {
    Box {
        Surface(
            modifier = Modifier.size(46.dp).clickable { onClick() },
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFFF9F9F9)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(painterResource(id = iconRes), null, modifier = Modifier.size(20.dp), tint = Color.Black)
            }
        }
        if (temNotificacao) {
            Box(modifier = Modifier.size(8.dp).background(CorPrimariaVermelha, CircleShape).align(Alignment.TopEnd).offset(x = (-4).dp, y = 4.dp))
        }
    }
}

@Composable
fun CardCarroDesign(nome: String, placa: String, score: String, imageRes: Int, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(145.dp).clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxHeight().width(5.dp).background(CorPrimariaVermelha))
            Row(modifier = Modifier.fillMaxSize().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1.2f)) {
                    Text(nome, fontFamily = Poppins, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Text(placa, fontFamily = Poppins, fontSize = 13.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(18.dp))
                    Surface(color = BadgeRosadaCard, shape = RoundedCornerShape(12.dp)) {
                        Text(score, fontFamily = Poppins, modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp), fontWeight = FontWeight.Black, color = Color.Black)
                    }
                }
                Image(painterResource(id = imageRes), null, modifier = Modifier.weight(1f).fillMaxHeight(), contentScale = ContentScale.Fit)
            }
        }
    }
}