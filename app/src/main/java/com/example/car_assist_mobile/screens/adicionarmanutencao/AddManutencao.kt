package com.example.car_assist_mobile.screens.adicionarmanutencao

import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.components.CustomBottomBar

@Composable
fun AddManutencaoScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomBottomBar(navController = navController, selectedItem = "garagem")
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.border(0.5.dp, Color.LightGray, CircleShape).size(45.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }
                    Text(
                        text = "MANUTENÇÕES",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                ManutencaoInput("Tipo de Manutenção")

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ManutencaoInput("Data", modifier = Modifier.weight(1f))
                    ManutencaoInput("Valor", modifier = Modifier.weight(1f))
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ManutencaoInput("KM", modifier = Modifier.weight(1f))
                    ManutencaoInput("Oficina", modifier = Modifier.weight(1f))
                }

                ManutencaoInput("Peças")
                ManutencaoInput("Evidência")
                ManutencaoInput("Observações")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .width(180.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "SALVAR",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ManutencaoInput(label: String, modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(bottom = 6.dp)) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
        )
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            modifier = Modifier.fillMaxWidth().height(45.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 13.sp, color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE8E8E8),
                focusedBorderColor = Color.Gray,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
    }
}