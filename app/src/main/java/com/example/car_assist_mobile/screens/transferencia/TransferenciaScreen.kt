package com.example.car_assist_mobile.screens.transferencia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.car_assist_mobile.ui.theme.Poppins

val MarromDesign = Color(0xFF73261D)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferenciaScreen(
    onNavigateBack: () -> Unit,
    onTransferirClick: (String, String, PermissaoTransferencia) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var permissaoSelecionada by remember { mutableStateOf(PermissaoTransferencia.TRANSFERIR_PROPRIEDADE) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TRANSFERÊNCIA",
                        fontFamily = Poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

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
                value = email,
                onValueChange = { email = it },
                isPassword = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransferField(
                label = "Sua Senha de Confirmação",
                value = senha,
                onValueChange = { senha = it },
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
                    selecionado = permissaoSelecionada == PermissaoTransferencia.LEITURA,
                    onClick = { permissaoSelecionada = PermissaoTransferencia.LEITURA }
                )
                PermissaoOption(
                    texto = "Acesso editável",
                    selecionado = permissaoSelecionada == PermissaoTransferencia.EDICAO,
                    onClick = { permissaoSelecionada = PermissaoTransferencia.EDICAO }
                )
                PermissaoOption(
                    texto = "Transferir propriedade definitiva",
                    selecionado = permissaoSelecionada == PermissaoTransferencia.TRANSFERIR_PROPRIEDADE,
                    onClick = { permissaoSelecionada = PermissaoTransferencia.TRANSFERIR_PROPRIEDADE }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onTransferirClick(email, senha, permissaoSelecionada) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MarromDesign),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Avançar",
                    fontFamily = Poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

enum class PermissaoTransferencia {
    LEITURA, EDICAO, TRANSFERIR_PROPRIEDADE
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
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = TextStyle(fontFamily = Poppins, fontSize = 14.sp, color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MarromDesign,
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
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = selecionado,
            onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(checkedColor = MarromDesign)
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
        StepCircle(number = "1", title = "Dados", isActive = passoAtual >= 1)
        HorizontalDivider(
            modifier = Modifier.width(50.dp).padding(horizontal = 8.dp),
            color = if (passoAtual >= 2) MarromDesign else Color(0xFFE0E0E0),
            thickness = 2.dp
        )
        StepCircle(number = "2", title = "Confirmar", isActive = passoAtual >= 2)
        HorizontalDivider(
            modifier = Modifier.width(50.dp).padding(horizontal = 8.dp),
            color = if (passoAtual >= 3) MarromDesign else Color(0xFFE0E0E0),
            thickness = 2.dp
        )
        StepCircle(number = "3", title = "Concluído", isActive = passoAtual >= 3)
    }
}

@Composable
fun StepCircle(number: String, title: String, isActive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(if (isActive) MarromDesign else Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                fontFamily = Poppins,
                color = Color.White,
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