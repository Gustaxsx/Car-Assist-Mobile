package com.example.car_assist_mobile.screens.cadastro

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current

    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var dataNasc by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    // Calendario
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            dataNasc = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    // Estilo para os inputs
    val fieldModifier = Modifier.fillMaxWidth().height(50.dp)
    val fieldShape = RoundedCornerShape(12.dp)

    val fieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = Color(0xFFE0E0E0),
        focusedBorderColor = Color.Gray,
        unfocusedContainerColor = Color.White
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .padding(horizontal = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Cabeçalho

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .border(0.5.dp, Color.LightGray, CircleShape)
                    .clickable { navController.popBackStack()},
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
            }

            Text(
                text = "CADASTRO",
                modifier = Modifier.weight(1f).padding(end = 45.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        // Formulario

        // Nome
        Text("Nome", fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        OutlinedTextField(value = nome, onValueChange = { nome = it }, modifier = fieldModifier, shape = fieldShape, colors = fieldColors)

        Spacer(modifier = Modifier.height(16.dp))

        // CPF
        Text("CPF", fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        OutlinedTextField(value = cpf, onValueChange = { cpf = it }, modifier = fieldModifier, shape = fieldShape, colors = fieldColors)

        Spacer(modifier = Modifier.height(16.dp))

        // Data de Nascimento
        Text("Data de Nascimento", fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        OutlinedTextField(
            value = dataNasc,
            onValueChange = { dataNasc = it },
            modifier = fieldModifier.clickable { datePickerDialog.show() },
            shape = fieldShape,
            colors = fieldColors,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null, tint = Color.Gray)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // E-mail
        Text("E-mail", fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, modifier = fieldModifier, shape = fieldShape, colors = fieldColors)

        Spacer(modifier = Modifier.height(16.dp))

        // Senha
        Text("Senha", fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            modifier = fieldModifier,
            shape = fieldShape,
            colors = fieldColors,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirmar Senha
        Text("Confirmar Senha", fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        OutlinedTextField(
            value = confirmarSenha,
            onValueChange = { confirmarSenha = it },
            modifier = fieldModifier,
            shape = fieldShape,
            colors = fieldColors,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Botoes
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {navController.popBackStack()},
                modifier = Modifier.weight(1f).height(45.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE)),
                shape = RoundedCornerShape(10.dp)

            ) {
                Text("Cancelar", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { },
                modifier = Modifier.weight(1f).height(45.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4C4C4)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Salvar", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}