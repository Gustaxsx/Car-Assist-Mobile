package com.example.car_assist_mobile.screens.transferencia

import com.example.car_assist_mobile.ui.theme.RedDesign
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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

@Composable
fun TransferenciaConfirmarScreen(
    navController: NavController,
    idUsuarioLogado: Int,
    viewModel: TransferenciaScreenViewModel
) {
    val uiState = viewModel.uiState
    val scrollState = rememberScrollState()

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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 30.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Text(
                    text = "CONFIRMAR TRANSFERÊNCIA",
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

            Spacer(modifier = Modifier.height(32.dp))

            TransferenciaStepper(passoAtual = 2)

            Spacer(modifier = Modifier.height(48.dp))

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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            val (textoAlerta, corCardBack, corTextoAlerta) = when (uiState.nivelPermissao) {
                "Somente leitura" -> Triple(
                    "Isso dará acesso de Somente Leitura. O usuário poderá ver os dados, mas não alterá-los.",
                    Color(0xFFF1F1F1),
                    Color(0xFF333333)
                )
                "Acesso editável" -> Triple(
                    "Isso dará Acesso Editável. O usuário poderá alterar as informações do veículo.",
                    Color(0xFFF1F1F1),
                    Color(0xFF333333)
                )
                else -> Triple(
                    "ATENÇÃO: Você está transferindo a propriedade. Isso removerá todos os usuários atuais vinculados a este veículo.",
                    Color(0xFFFCE8E6),
                    RedDesign
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(corCardBack, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = textoAlerta,
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = corTextoAlerta,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFCCCCCC))
                ) {
                    Text(
                        text = "Não",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF555555)
                        )
                    )
                }

                Button(
                    onClick = {
                        viewModel.gerarCodigoTransferencia()
                        navController.navigate("transferencia_codigo")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedDesign)
                ) {
                    Text(
                        text = "Sim",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}