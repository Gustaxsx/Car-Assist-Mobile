package com.example.car_assist_mobile.screens.cadastro

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.ui.theme.Poppins
import java.util.Calendar

val MarromDesign = Color(0xFF73261D)

@Composable
fun RegisterScreen(navController: NavController) {

    val context = LocalContext.current

    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var dataNasc by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, y, m, d ->
            dataNasc = "$d/${m + 1}/$y"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .size(700.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-560).dp)
                .clip(CircleShape)
                .background(MarromDesign.copy(alpha = 0.06f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier
                        .size(42.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    shape = CircleShape,
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFEAEAEA))
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "Crie sua conta",
                        fontFamily = Poppins,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(42.dp))
            }

            Spacer(modifier = Modifier.height(34.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Preencha seus dados",
                    fontFamily = Poppins,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MarromDesign
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Falta pouco para você entrar no time.",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                RegisterField(
                    label = "Nome Completo",
                    value = nome,
                    onValueChange = { nome = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {

                        RegisterField(
                            label = "CPF",
                            value = cpf,
                            onValueChange = { cpf = it }
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {

                        Column {

                            Text(
                                text = "Nascimento",
                                fontFamily = Poppins,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            OutlinedTextField(
                                value = dataNasc,
                                onValueChange = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .clickable {
                                        datePickerDialog.show()
                                    },
                                shape = RoundedCornerShape(14.dp),
                                readOnly = true,
                                enabled = false,
                                singleLine = true,
                                textStyle = TextStyle(
                                    fontFamily = Poppins,
                                    fontSize = 13.sp
                                ),
                                placeholder = {
                                    Text(
                                        text = "DD/MM/AAAA",
                                        fontSize = 11.sp
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledTextColor = Color.Black,
                                    disabledBorderColor = Color(0xFFEFEFEF),
                                    disabledContainerColor = Color(0xFFF9F9F9)
                                )
                            )
                        }
                    }
                }

                RegisterField(
                    label = "E-mail",
                    value = email,
                    onValueChange = { email = it }
                )

                RegisterField(
                    label = "Senha",
                    value = senha,
                    onValueChange = { senha = it },
                    isPassword = true
                )

                RegisterField(
                    label = "Confirmar Senha",
                    value = confirmarSenha,
                    onValueChange = { confirmarSenha = it },
                    isPassword = true
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MarromDesign
                    )
                ) {

                    Text(
                        text = "CONCLUIR CADASTRO",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 13.sp,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                TextButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {

                    Text(
                        text = "Já tenho uma conta. Voltar",
                        fontFamily = Poppins,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun RegisterField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = label,
            fontFamily = Poppins,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = Poppins,
                fontSize = 13.sp
            ),
            visualTransformation =
                if (isPassword)
                    PasswordVisualTransformation()
                else
                    VisualTransformation.None,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MarromDesign,
                unfocusedBorderColor = Color(0xFFEFEFEF),
                unfocusedContainerColor = Color(0xFFF9F9F9),
                focusedContainerColor = Color.White
            )
        )
    }
}