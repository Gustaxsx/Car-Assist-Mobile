package com.example.car_assist_mobile.screens.manutencao

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.components.CustomBottomBar

data class Manutencao(val titulo: String, val data: String, val valor: String)

@Composable
fun ManutencaoScreen(navController: NavController) {

    val listaManutencoes = listOf(
        Manutencao("Troca de Óleo", "23/12/2025", "R$350,00"),
        Manutencao("Funilaria", "15/01/2026", "R$2.450,00"),
        Manutencao("Painel", "19/01/2026", "R$150,00")
    )

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
                    text = "MANUTENÇÕES",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(listaManutencoes) { item ->
                    CardManutencao(item)
                }
            }

            Button(
                onClick = { navController.navigate("AddManutencao")},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .height(48.dp)
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "NOVA MANUTENÇÃO",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun CardManutencao(manutencao: Manutencao) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0)),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text("Manutenção", fontSize = 11.sp, color = Color.LightGray)
            Text(manutencao.titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Data", fontSize = 11.sp, color = Color.LightGray)
                    Text(manutencao.data, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Box(modifier = Modifier.width(1.dp).height(30.dp).background(Color(0xFFF0F0F0)))

                Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                    Text("Valor", fontSize = 11.sp, color = Color.LightGray)
                    Text(manutencao.valor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
