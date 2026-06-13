package com.example.car_assist_mobile.screens.transferencia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins

val RedDesignCodigo = Color(0xFFA61616)

@Composable
fun TransferenciaCodigoScreen(
    navController: NavController,
    idUsuarioLogado: Int,
    viewModel: TransferenciaScreenViewModel
) {
    val scrollState = rememberScrollState()
    val codigoTransferencia = viewModel.uiState.codigoGerado

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
                        modifier = Modifier.size(44.dp),
                        shape = CircleShape,
                        color = Color.Transparent,
                        border = BorderStroke(1.dp, Color(0xFFEFEFEF))
                    ) {
                        IconButton(onClick = {
                            viewModel.resetState()
                            navController.navigate("garagem/$idUsuarioLogado") {
                                popUpTo("garagem/$idUsuarioLogado") { inclusive = true }
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Text(
                        text = "CÓDIGO DE TRANSFERÊNCIA",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 52.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
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
                    selectedItem = "garagem",
                    idUsuarioLogado = idUsuarioLogado
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                TransferenciaStepper(passoAtual = 3)

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Código gerado com sucesso!",
                    fontFamily = Poppins,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Copie o código abaixo e envie para o novo usuário do veículo concluir o processo.",
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(40.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF9F9F9),
                    border = BorderStroke(1.dp, Color(0xFFEFEFEF))
                ) {
                    Box(
                        modifier = Modifier.padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = codigoTransferencia.ifBlank { "------" },
                            fontFamily = Poppins,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = RedDesignCodigo,
                            letterSpacing = 4.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = {
                        viewModel.resetState()
                        navController.navigate("garagem/$idUsuarioLogado") {
                            popUpTo("garagem/$idUsuarioLogado") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedDesignCodigo)
                ) {
                    Text(
                        text = "Voltar ao Início",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}