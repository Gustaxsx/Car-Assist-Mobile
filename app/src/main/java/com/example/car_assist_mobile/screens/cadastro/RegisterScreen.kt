package com.example.car_assist_mobile.screens.cadastro

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var dataNasc by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, y, m, d -> dataNasc = "$d/${m + 1}/$y" },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 32.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .border(0.5.dp, Color.LightGray, CircleShape)
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(20.dp), tint = Color.Gray)
                }

                Text(
                    text = "CADASTRO",
                    modifier = Modifier.weight(1f).padding(end = 45.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            RegisterField(label = "Nome", value = nome, onValueChange = { nome = it })
            Spacer(modifier = Modifier.height(12.dp))

            RegisterField(label = "CPF", value = cpf, onValueChange = { cpf = it })
            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Data de Nascimento", fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp), color = Color.Black)
                OutlinedTextField(
                    value = dataNasc,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clickable { datePickerDialog.show() },
                    shape = RoundedCornerShape(15.dp),
                    readOnly = true,
                    enabled = false,
                    textStyle = TextStyle(fontSize = 14.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        disabledBorderColor = Color(0xFFE0E0E0),
                        disabledContainerColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            RegisterField(label = "E-mail", value = email, onValueChange = { email = it })
            Spacer(modifier = Modifier.height(12.dp))

            RegisterField(label = "Senha", value = senha, onValueChange = { senha = it }, isPassword = true)
            Spacer(modifier = Modifier.height(12.dp))

            RegisterField(label = "Confirmar Senha", value = confirmarSenha, onValueChange = { confirmarSenha = it }, isPassword = true)

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4C4C4)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Salvar", color = Color.Black, fontWeight = FontWeight.Bold)
                }
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
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
            color = Color.Black
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedBorderColor = Color.Gray,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
    }
}