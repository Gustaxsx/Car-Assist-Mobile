package com.example.car_assist_mobile.screens.transferencia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins

val RedDesign = Color(0xFFA61616)

@Composable
fun TransferenciaScreen(navController: NavController, viewModel: TransferenciaViewModel) {
    val uiState = viewModel.uiState
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            navController.navigate("transferencia_confirmar")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedDesign),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Transferir",
                            fontFamily = Poppins,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomBottomBar(navController = navController, selectedItem = "garagem")
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .padding(horizontal = 30.dp)
                // O segredo está aqui: adicionamos um espaço extra de 100.dp no fim da rolagem
                .verticalScroll(state = scrollState, reverseScrolling = false),
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
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Text(
                    text = "TRANSFERÊNCIA",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            TransferenciaStepper(passoAtual = 1)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Informe os dados do destinatário para a transferência do veículo.",
                fontFamily = Poppins,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            TransferField(
                label = "E-mail do Destinatário",
                value = uiState.emailDestinatario,
                onValueChange = { viewModel.onEmailChanged(it) },
                isPassword = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransferField(
                label = "Sua Senha de Confirmação",
                value = uiState.senhaConfirmacao,
                onValueChange = { viewModel.onSenhaChanged(it) },
                isPassword = true
            )

            Spacer(modifier = Modifier.height(28.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Nível de Permissão",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))

                PermissaoOption(
                    texto = "Somente leitura",
                    selecionado = uiState.nivelPermissao == "Somente leitura",
                    onClick = { viewModel.onPermissaoChanged("Somente leitura") }
                )
                PermissaoOption(
                    texto = "Acesso editável",
                    selecionado = uiState.nivelPermissao == "Acesso editável",
                    onClick = { viewModel.onPermissaoChanged("Acesso editável") }
                )
                PermissaoOption(
                    texto = "Transferir propriedade definitiva",
                    selecionado = uiState.nivelPermissao == "Transferir propriedade definitiva",
                    onClick = { viewModel.onPermissaoChanged("Transferir propriedade definitiva") }
                )
            }

            // Espaço invisível extra para empurrar o conteúdo acima do botão fixo durante o scroll
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun TransferField(label: String, value: String, onValueChange: (String) -> Unit, isPassword: Boolean) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontFamily = Poppins,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = TextStyle(fontFamily = Poppins, fontSize = 14.sp, color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RedDesign,
                unfocusedBorderColor = Color(0xFFEFEFEF),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0xFFF9F9F9)
            )
        )
    }
}

@Composable
fun PermissaoOption(texto: String, selecionado: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selecionado,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = RedDesign)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = texto,
            fontFamily = Poppins,
            fontSize = 14.sp,
            color = if (selecionado) Color.Black else Color.DarkGray,
            fontWeight = if (selecionado) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
fun TransferenciaStepper(passoAtual: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        StepCircle(
            number = "1",
            title = "Dados",
            isActive = passoAtual >= 1,
            isCompleted = passoAtual > 1,
        )
        HorizontalDivider(
            modifier = Modifier.width(50.dp).padding(horizontal = 8.dp),
            color = if (passoAtual >= 2) RedDesign else Color(0xFFE0E0E0),
            thickness = 2.dp
        )
        StepCircle(
            number = "2",
            title = "Confirmar",
            isActive = passoAtual >= 2,
            isCompleted = passoAtual > 2,
        )
        HorizontalDivider(
            modifier = Modifier.width(50.dp).padding(horizontal = 8.dp),
            color = if (passoAtual >= 3) RedDesign else Color(0xFFE0E0E0),
            thickness = 2.dp
        )
        StepCircle(
            number = "3",
            title = "Concluído",
            isActive = passoAtual >= 3,
            isCompleted = false,
        )
    }
}

@Composable
fun StepCircle(
    number: String,
    title: String,
    isActive: Boolean,
    isCompleted: Boolean
) {
    val backgroundColor = when {
        isActive -> RedDesign
        isCompleted -> RedDesign.copy(alpha = 0.15f)
        else -> Color(0xFFE0E0E0)
    }

    val textColor = when {
        isActive -> Color.White
        isCompleted -> RedDesign
        else -> Color(0xFF999999)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                fontFamily = Poppins,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontFamily = Poppins,
            fontSize = 12.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
            color = if (isActive) Color.Black else Color.Gray
        )
    }
}