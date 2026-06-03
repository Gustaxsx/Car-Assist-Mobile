package com.example.car_assist_mobile.screens.transferencia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins
import com.example.car_assist_mobile.ui.theme.RedDesign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferenciaConfirmarScreen(
    navController: NavController,
    viewModel: TransferenciaViewModel
) {
    val uiState = viewModel.uiState

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            CustomBottomBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFF7F7F7), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black
                    )
                }

                Text(
                    text = "TRANSFERÊNCIA",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepCircle(number = "1", label = "Dados", isCompleted = true, isActive = false)

                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.width(40.dp).height(1.dp).background(Color(0xFFE0E0E0)))
                Spacer(modifier = Modifier.width(8.dp))

                StepCircle(number = "2", label = "Confirmar", isCompleted = false, isActive = true)

                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.width(40.dp).height(1.dp).background(Color(0xFFE0E0E0)))
                Spacer(modifier = Modifier.width(8.dp))

                StepCircle(number = "3", label = "Concluído", isCompleted = false, isActive = false)
            }

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = buildAnnotatedString {
                    append("Você tem certeza que deseja gerar um código de transferência para o carro ")
                    withStyle(style = SpanStyle(color = RedDesign, fontWeight = FontWeight.Bold)) {
                        append(uiState.nomeVeiculo)
                    }
                    append(" com a placa ")
                    withStyle(style = SpanStyle(color = RedDesign, fontWeight = FontWeight.Bold)) {
                        append(uiState.placaVeiculo)
                    }
                    append("?")
                },
                style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {
                    viewModel.gerarCodigoTransferencia()
                    navController.navigate("transferencia_codigo")
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedDesign
                )
            ) {
                Text(
                    text = "Sim",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Cancelar",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                ),
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .padding(8.dp)
            )
        }
    }
}