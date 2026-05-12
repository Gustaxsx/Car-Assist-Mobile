package com.example.car_assist_mobile.screens.gastos

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.components.CustomBottomBar

@Composable
fun GastosScreen(navController: NavController) {

    var isSemanalSelected by remember { mutableStateOf(true) }

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
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    text = "GASTOS",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TabButton("Semanal", isSemanalSelected, Modifier.weight(1f)) { isSemanalSelected = true }
                TabButton("Mensal", !isSemanalSelected, Modifier.weight(1f)) { isSemanalSelected = false }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GastoItem("COMBUSTÍVEL", "R$ 230,00")
                GastoItem("LIMPEZA", "R$ 230,00")
                GastoItem("PEDÁGIO", "R$ 230,00")
                GastoItem("ESTACIONAMENTO", "R$ 230,00")
                GastoItem("MANUTENÇÃO", "R$ 230,00")
                GastoItem("MULTAS", "R$ 230,00")

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp).width(1.dp).height(20.dp),
                    color = Color.LightGray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        modifier = Modifier.width(130.dp).height(35.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFD9D9D9)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("Total", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                    Text(
                        text = "R$ 1380,00",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier.width(220.dp).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Inserir Novo Valor", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier
            .height(40.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) Color(0xFF999999) else Color(0xFFD9D9D9)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                color = Color.Black,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun GastoItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.width(130.dp).height(35.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFF666666)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(label, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}
