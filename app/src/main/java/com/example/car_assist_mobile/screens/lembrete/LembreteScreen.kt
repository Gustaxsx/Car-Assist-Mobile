package com.example.car_assist_mobile.screens.lembrete

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
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

data class Reminder(val text: String, val isUrgent: Boolean)

@Composable
fun LembreteScreen(navController: NavController) {
    val reminders = listOf(
        Reminder("Troca de óleo em 2 dias", true),
        Reminder("Revisão dos freios", true),
        Reminder("Renovação do seguro", false),
        Reminder("Calibragem dos pneus", false),
        Reminder("Limpeza do ar-condicionado", false),
        Reminder("Troca do filtro de ar", false)
    )

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomBottomBar(navController = navController, selectedItem = "servicos")
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
                    text = "LEMBRETES",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(reminders) { reminder ->
                    ReminderItem(reminder)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {navController.navigate("AddLembrete")},
                modifier = Modifier
                    .height(48.dp)
                    .width(220.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(
                    Icons.Default.AddCircle,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "ADICIONAR LEMBRETE",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ReminderItem(reminder: Reminder) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    if (reminder.isUrgent) Color.Red else Color(0xFFC4C4C4),
                    CircleShape
                )
        )
        Spacer(modifier = Modifier.width(16.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            color = Color(0xFFF2F2F2),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = reminder.text,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}