package com.example.car_assist_mobile.screens.garagem

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar

@Composable
fun GarageScreen(navController: NavController) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomBottomBar(
                    navController = navController,
                    selectedItem = "garagem"
                )
            }
        },
        containerColor = Color(0xFFF7F7F7)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = WindowInsets.statusBars
                            .asPaddingValues()
                            .calculateTopPadding() + 8.dp,
                        start = 24.dp,
                        end = 24.dp
                    )
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.perfil1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Olá Beatriz!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                        Text(
                            text = "contatobeatriz@email.com",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    HeaderIcon(
                        iconRes = R.drawable.icone_chat,
                        onClick = { navController.navigate("chatbot") }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    HeaderIcon(
                        iconRes = R.drawable.icone_email,
                        onClick = {navController.navigate("Lembrete")}
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(16.dp))
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "91.6",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1A1A1A)
                        )
                        Text(
                            text = "SCORE DA GARAGEM",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1A1A1A)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        CarCard(
                            name = "Fastback Abarth",
                            plate = "RFT5S34",
                            score = "97",
                            imageRes = R.drawable.icone_carro_moderno,
                            onClick = { navController.navigate("DetailsCar") }
                        )
                    }
                    item {
                        CarCard(
                            name = "T-Cross",
                            plate = "QXM7D19",
                            score = "92",
                            imageRes = R.drawable.icone_carro_moderno,
                            onClick = {}
                        )
                    }
                    item {
                        CarCard(
                            name = "Onix",
                            plate = "RZT5B67",
                            score = "86",
                            imageRes = R.drawable.icone_carro_moderno,
                            onClick = {}
                        )
                    }
                }
            }

            Button(
                onClick = { navController.navigate("AddCar") },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
                    .height(48.dp)
                    .width(180.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.seta_cima),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ADICIONAR CARRO",
                    color = Color.Black,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HeaderIcon(iconRes: Int, onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .size(42.dp)
            .clickable { onClick() },
        shape = CircleShape,
        color = Color(0xFF1A1A1A)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
fun CarCard(
    name: String,
    plate: String,
    score: String,
    imageRes: Int,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEFEFEF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1.2f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Text(
                        text = plate,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(width = 55.dp, height = 32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = score,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit
            )
        }
    }
}