package com.example.car_assist_mobile.screens.garagem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar

@Composable
fun GarageScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F7F7))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = null,
                    modifier = Modifier.size(55.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                    Text(text = "Olá Beatriz!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "contatobeatriz@email.com", fontSize = 12.sp, color = Color.Gray)
                }

                HeaderIcon(iconRes = R.drawable.icone_chat)
                Spacer(modifier = Modifier.width(8.dp))
                HeaderIcon(iconRes = R.drawable.icone_email)
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFFD9D9D9), RoundedCornerShape(18.dp))
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "91.6", fontSize = 42.sp, fontWeight = FontWeight.Black)
                    Text(
                        text = "SCORE DA GARAGEM",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CarCard(name = "Fastback Abarth", plate = "RFT5S34", score = "97", imageRes = R.drawable.car_1)
            Spacer(modifier = Modifier.height(16.dp))
            CarCard(name = "T-Cross", plate = "QXM7D19", score = "92", imageRes = R.drawable.car_1)
            Spacer(modifier = Modifier.height(16.dp))
            CarCard(name = "Onix", plate = "RZT5B67", score = "86", imageRes = R.drawable.car_1)

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterHorizontally).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                shape = RoundedCornerShape(50.dp)
            ) {
                Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "ADICIONAR CARRO", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(120.dp))
        }

        CustomBottomBar(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun HeaderIcon(iconRes: Int) {
    Box(
        modifier = Modifier
            .size(45.dp)
            .background(Color.White, CircleShape)
            .border(1.dp, Color(0xFFEEEEEE), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(20.dp))
    }
}

@Composable
fun CarCard(name: String, plate: String, score: String, imageRes: Int) {
    Card(
        modifier = Modifier.fillMaxWidth().height(160.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = plate, fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.weight(1f))

                // Badge do Score
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(35.dp)
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(50.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = score, fontWeight = FontWeight.Bold)
                }
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(140.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}