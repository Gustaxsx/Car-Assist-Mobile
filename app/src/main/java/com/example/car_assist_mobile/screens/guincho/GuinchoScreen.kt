//package com.example.car_assist_mobile.screens.guincho
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.car_assist_mobile.components.CustomBottomBar
//
//val FundoRosadoGeral = Color(0xFFFDF7F7)
//val CinzaTextoSuave = Color(0xFFBDBDBD)
//
//@Composable
//fun GuinchoScreen(navController: NavController) {
//    Scaffold(
//        containerColor = FundoRosadoGeral,
//        bottomBar = {
//            CustomBottomBar(
//                navController = navController,
//                selectedItem = "servicos"
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(horizontal = 24.dp)
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 20.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Surface(
//                    modifier = Modifier
//                        .align(Alignment.CenterStart)
//                        .size(45.dp)
//                        .clickable { navController.popBackStack() },
//                    shape = CircleShape,
//                    color = Color.White
//                ) {
//                    Box(contentAlignment = Alignment.Center) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "Voltar",
//                            tint = Color.Black,
//                            modifier = Modifier.size(20.dp)
//                        )
//                    }
//                }
//
//                Text(
//                    text = "GUINCHO",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    letterSpacing = 1.sp
//                )
//            }
//
//            Spacer(modifier = Modifier.height(30.dp))
//
//            LazyColumn(
//                modifier = Modifier.fillMaxWidth(),
//                verticalArrangement = Arrangement.spacedBy(20.dp),
//                contentPadding = PaddingValues(bottom = 100.dp)
//            ) {
//                item {
//                    GuinchoCard(
//                        nome = "Guincho 24 Horas",
//                        distancia = "2.2km",
//                        endereco = "Atendimento em toda a Região Metropolitana"
//                    )
//                }
//                item {
//                    GuinchoCard(
//                        nome = "SOS Auto Resgate",
//                        distancia = "4.7km",
//                        endereco = "Avenida Central, 1500 - Distrito Industrial"
//                    )
//                }
//                item {
//                    GuinchoCard(
//                        nome = "Reboque Confiança",
//                        distancia = "6.1km",
//                        endereco = "Rua das Acácias, 400 - Jardim Glória"
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun GuinchoCard(nome: String, distancia: String, endereco: String) {
//    Surface(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(24.dp),
//        color = Color.White,
//        shadowElevation = 1.dp,
//        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEFEFEF))
//    ) {
//        Column(
//            modifier = Modifier.padding(vertical = 20.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier.weight(1f),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Estabelecimento",
//                        fontSize = 11.sp,
//                        color = CinzaTextoSuave,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        text = nome,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Black,
//                        color = Color.Black
//                    )
//                }
//
//                Box(
//                    modifier = Modifier
//                        .width(1.dp)
//                        .height(40.dp)
//                        .background(Color(0xFFE0E0E0))
//                )
//
//                Column(
//                    modifier = Modifier.weight(1f),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Distância",
//                        fontSize = 11.sp,
//                        color = CinzaTextoSuave,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        text = distancia,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Black,
//                        color = Color.Black
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 24.dp)
//                    .height(1.dp)
//                    .background(Color(0xFFF0F0F0))
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = "Base / Área de Atendimento",
//                    fontSize = 11.sp,
//                    color = CinzaTextoSuave,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = endereco,
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.Black,
//                    color = Color.Black,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(horizontal = 16.dp)
//                )
//            }
//        }
//    }
//}