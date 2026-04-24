package com.example.car_assist_mobile.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.navigation.Screen

@Composable
fun LoginScreen(navController: NavController) {

    // Pegando as dimensões da tela
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    // Estados para os campos de texto funcionarem
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Topo escuro com o carro
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.35f)
                .clip(
                    RoundedCornerShape(
                        bottomStart = screenWidth * 0.4f,
                        bottomEnd = screenWidth * 0.4f
                    )
                )
                .background(Color(0xFF2D3239)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.carro_topo),
                contentDescription = "Carro Assist",
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )
        }

        // Area do formulario
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bem-vindo",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Faça login para continuar",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo de E-mail
            CustomInputField(
                label = "E-mail",
                placeholder = "seu@email.com",
                value = email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Senha
            CustomInputField(
                label = "Senha",
                placeholder = "••••••••",
                value = senha,
                onValueChange = { senha = it }
            )

            Text(
                text = "Esqueceu a senha?",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable {},
                fontSize = 12.sp,
                color = Color.Gray
            )

            // Botão Entrar
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D3239)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "ENTRAR",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Rodapé
            Row(modifier = Modifier.padding(bottom = 40.dp)) {
                Text(text = "Não tem conta? ", color = Color.Gray)
                Text(
                    text = "Criar conta",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate(Screen.Register.route) }
                )
            }
        }
    }
}

// Componente de Input Reutilizável
@Composable
fun CustomInputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Black
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text(placeholder) },
            singleLine = true,

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2D3239),
                unfocusedBorderColor = Color.LightGray
            )
        )
    }
}