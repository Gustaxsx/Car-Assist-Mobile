package com.example.car_assist_mobile.screens.garagem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar

val CorPrimariaVermelha = Color(0xFF910D0D)
val FundoGeral = Color(0xFFFFFFFF)
val BadgeRosadaCard = Color(0xFFF5E9E9)

@Composable
fun GarageScreen(navController: NavController) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            CustomBottomBar(
                navController = navController,
                selectedItem = "garagem"
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
                        painter = painterResource(id = R.drawable.perfil1),
                        contentDescription = null,
                        modifier = Modifier.size(52.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                        Text("Olá Beatriz!", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("contatobeatriz@email.com", fontSize = 12.sp, color = Color.Gray)
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
                            Text("91.6", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Black)
                            Spacer(modifier = Modifier.width(16.dp))
                            Box(modifier = Modifier.width(1.dp).height(30.dp).background(Color(0xFFE0E0E0)))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("SCORE DA GARAGEM", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(painterResource(id = R.drawable.icone_grafico), null, tint = CorPrimariaVermelha, modifier = Modifier.size(22.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 120.dp)
                ) {
                    item { CardCarroDesign("Fastback Abarth", "RFT5S34", "97", R.drawable.icone_carro_moderno) { navController.navigate("DetailsCar") } }
                    item { CardCarroDesign("T-Cross", "QXM7D19", "92", R.drawable.icone_carro_moderno) { } }
                    item { CardCarroDesign("Onix", "RZT5B67", "86", R.drawable.icone_carro_moderno) { } }
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
                    .clickable { navController.navigate("AddCar") },
                color = BadgeRosadaCard,
                shape = RoundedCornerShape(25.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painterResource(id = R.drawable.icone_mais), null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ADICIONAR CARRO", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
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
                    Text(nome, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Text(placa, fontSize = 13.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(18.dp))
                    Surface(color = BadgeRosadaCard, shape = RoundedCornerShape(12.dp)) {
                        Text(score, modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp), fontWeight = FontWeight.Black, color = Color.Black)
                    }
                }
                Image(painterResource(id = imageRes), null, modifier = Modifier.weight(1f).fillMaxHeight(), contentScale = ContentScale.Fit)
            }
        }
    }
}