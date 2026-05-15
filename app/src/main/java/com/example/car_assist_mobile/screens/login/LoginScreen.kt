package com.example.car_assist_mobile.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.ui.theme.Poppins

val MarromDesign = Color(0xFF73261D)

@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .size(850.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-620).dp)
                .clip(CircleShape)
                .background(MarromDesign)
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentAlignment = Alignment.TopCenter
            ) {

                Image(
                    painter = painterResource(id = R.drawable.icone_carro_branco),
                    contentDescription = "Carro Assist",
                    modifier = Modifier
                        .size(330.dp)
                        .offset(y = (-40).dp)
                        .rotate(-90f),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Bem-vindo",
                    fontFamily = Poppins,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "Faça login para continuar",
                    fontFamily = Poppins,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(36.dp))

                CustomInputField(
                    label = "E-mail",
                    value = email,
                    onValueChange = { email = it }
                )

                Spacer(modifier = Modifier.height(18.dp))

                CustomInputField(
                    label = "Senha",
                    value = senha,
                    onValueChange = { senha = it }
                )

                Text(
                    text = "Esqueceu a senha?",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 14.dp, bottom = 20.dp)
                        .clickable {}
                )

                Button(
                    onClick = { navController.navigate("garage") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MarromDesign
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Text(
                        text = "ENTRAR",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {

                Text(
                    text = "Não tem conta?",
                    fontFamily = Poppins,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Text(
                    text = "Criar conta",
                    fontFamily = Poppins,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        navController.navigate("register")
                    }
                )
            }
        }
    }
}

@Composable
fun CustomInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = label,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp),
            color = Color.Black
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(18.dp),
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )
    }
}