package com.example.car_assist_mobile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R

@Composable
fun CustomBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    selectedItem: String = "perfil"
) {
    Box(
        modifier = modifier
            .padding(bottom = 24.dp)
            .width(320.dp)
            .height(64.dp)
            .background(Color(0xFF2D3239), RoundedCornerShape(50.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            BottomBarItem(
                label = "Garagem",
                iconRes = R.drawable.icone_carro,
                isSelected = selectedItem == "garagem",
                onClick = {
                    if (selectedItem != "garagem") {
                        navController.navigate("garage")
                    }
                }
            )

            BottomBarItem(
                label = "Postos",
                iconRes = R.drawable.gas_station,
                isSelected = selectedItem == "postos",
                onClick = {
                    if (selectedItem != "postos") {
                        navController.navigate("service")
                    }
                }
            )

            BottomBarItem(
                label = "Perfil",
                iconRes = R.drawable.user,
                isSelected = selectedItem == "perfil",
                onClick = {
                    if (selectedItem != "perfil") {
                        navController.navigate("profile")
                    }
                }
            )
        }
    }
}

@Composable
fun BottomBarItem(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    if (isSelected) {
        Box(
            modifier = Modifier
                .height(48.dp)
                .background(Color(0xFFF3F3F3), RoundedCornerShape(50.dp))
                .clickable { onClick() }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    } else {

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF3A4048), CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}