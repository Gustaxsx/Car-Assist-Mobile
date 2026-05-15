package com.example.car_assist_mobile.screens.perfil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins

val MarromDesign = Color(0xFF73261D)

@Composable
fun EditProfileScreen(navController: NavController) {
    var nome by remember { mutableStateOf("Beatriz Fernanda") }
    var cpf by remember { mutableStateOf("444.444.444-44") }
    var dataNasc by remember { mutableStateOf("10/05/2006") }
    var email by remember { mutableStateOf("contatobeatriz@email.com") }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(24.dp))
                    Surface(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable { navController.popBackStack() },
                        shape = CircleShape,
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Black)
                        }
                    }
                    Text(
                        text = "EDITAR PERFIL",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontFamily = Poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(69.dp))
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        },
        bottomBar = {
            CustomBottomBar(navController = navController, selectedItem = "perfil")
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Box(contentAlignment = Alignment.BottomEnd) {
                    Surface(
                        modifier = Modifier.size(110.dp),
                        shape = CircleShape,
                        color = Color(0xFFF9F9F9),
                        border = BorderStroke(2.dp, MarromDesign.copy(alpha = 0.1f))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = R.drawable.icone_user),
                                contentDescription = null,
                                modifier = Modifier.size(70.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier.size(32.dp).offset(x = (-5).dp, y = (-5).dp),
                        shape = CircleShape,
                        color = MarromDesign
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = R.drawable.icone_pincel),
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(32.dp))
                    Column(modifier = Modifier.weight(1f)) {

                        EditField(label = "Nome", value = nome, onValueChange = { nome = it })

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text("CPF", fontFamily = Poppins, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                modifier = Modifier.fillMaxWidth().height(52.dp),
                                shape = RoundedCornerShape(14.dp),
                                color = Color(0xFFF5F5F5),
                                border = BorderStroke(1.dp, Color(0xFFEFEFEF))
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(text = cpf, color = Color.Gray, fontSize = 14.sp, fontFamily = Poppins)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        EditField(label = "Data de Nascimento", value = dataNasc, onValueChange = { dataNasc = it })

                        Spacer(modifier = Modifier.height(16.dp))
                        EditField(label = "E-mail", value = email, onValueChange = { email = it })

                        Spacer(modifier = Modifier.height(40.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.weight(1f).height(52.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Cancelar", color = Color.Gray, fontFamily = Poppins, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {},
                                modifier = Modifier.weight(1f).height(52.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MarromDesign),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Salvar", color = Color.White, fontFamily = Poppins, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(32.dp))
                }
            }
        }
    }
}

@Composable
fun EditField(label: String, value: String, onValueChange: (String) -> Unit) {
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
            textStyle = TextStyle(fontFamily = Poppins, fontSize = 14.sp, color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MarromDesign,
                unfocusedBorderColor = Color(0xFFEFEFEF),
                unfocusedContainerColor = Color(0xFFF9F9F9),
                focusedContainerColor = Color.White
            )
        )
    }
}