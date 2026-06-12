package com.example.car_assist_mobile.screens.service

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins

val CorVermelhaPremium = Color(0xFF910D0D)
val TomRosaSuave = Color(0xFFF5E9E9)
val CorTextoEscuro = Color(0xFF171B20)

@Composable
fun ServicesScreen(
    navController: NavController,
    idUsuarioLogado: Int
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp)
                    .padding(horizontal = 30.dp)
            ) {
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
                        text = "SERVIÇOS",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        color = Color.Black
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CustomBottomBar(
                    navController = navController,
                    selectedItem = "servicos",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            val dashPathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 15f), 0f)
                            drawRoundRect(
                                color = Color(0xFFD1D5DB),
                                style = Stroke(width = 4f, pathEffect = dashPathEffect),
                                cornerRadius = CornerRadius(80f, 80f)
                            )
                        }
                        .padding(horizontal = 24.dp, vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.size(100.dp),
                            shape = CircleShape,
                            color = TomRosaSuave
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icone_servicos),
                                    contentDescription = "Ferramenta",
                                    tint = CorVermelhaPremium,
                                    modifier = Modifier.size(45.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "Página em Construção",
                            fontFamily = Poppins,
                            fontSize = 24.sp,
                            lineHeight = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = CorTextoEscuro,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = buildAnnotatedString {
                                append("Estamos trabalhando duro para trazer os melhores ")
                                withStyle(style = SpanStyle(color = CorVermelhaPremium, fontWeight = FontWeight.Bold)) {
                                    append("serviços")
                                }
                                append(" para o seu veículo em breve!")
                            },
                            fontFamily = Poppins,
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = Color(0xFF6B7280),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1.5f))
            }
        }
    }
}