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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.ui.theme.Poppins

@Composable
fun LoginScreen(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

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
                painter = painterResource(id = R.drawable.bg_carro_topo),
                contentDescription = "Carro Assist",
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .aspectRatio(1.1f)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bem-vindo",
                fontFamily = Poppins,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Faça login para continuar",
                fontFamily = Poppins,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomInputField(
                label = "E-mail",
                placeholder = "seu@email.com",
                value = email,
                onValueChange = { email = it },
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomInputField(
                label = "Senha",
                placeholder = "••••••••",
                value = senha,
                onValueChange = { senha = it }
            )

            Text(
                text = "Esqueceu a senha?",
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable {},
                fontSize = 12.sp,
                color = Color.Black
            )

            Button(
                onClick = {navController.navigate("garage")},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D3239)),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "ENTRAR",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Não tem conta?",
                    fontFamily = Poppins,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "Criar conta",
                    fontFamily = Poppins,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate("register") }
                )
            }
        }
    }
}

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
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
            color = Color.Black
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Light
                )
            },
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2D3239),
                unfocusedBorderColor = Color.LightGray
            )
        )
    }
}