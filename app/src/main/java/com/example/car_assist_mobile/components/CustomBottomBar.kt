package com.example.car_assist_mobile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_assist_mobile.R

val RedPrimaryBar = Color(0xFF910D0D)
val SelectedTabBackground = Color(0xFFFDF4F4)
val UnselectedGray = Color(0xFF757575)

@Composable
fun CustomBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    selectedItem: String = "garagem",
    idUsuarioLogado: Int = 0
) {
    val barWidth = 320.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier
                .width(barWidth)
                .height(64.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 6.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BottomBarItem(
                    label = "Garagem",
                    iconRes = R.drawable.icone_carro,
                    isSelected = selectedItem == "garagem",
                    onClick = {
                        if (selectedItem != "garagem") {
                            navController.navigate("garagem/$idUsuarioLogado") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )

                BottomBarItem(
                    label = "Serviços",
                    iconRes = R.drawable.icone_servicos,
                    isSelected = selectedItem == "servicos",
                    onClick = {
                        if (selectedItem != "servicos") {
                            navController.navigate("service") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )

                BottomBarItem(
                    label = "Perfil",
                    iconRes = R.drawable.icone_user,
                    isSelected = selectedItem == "perfil",
                    onClick = {
                        if (selectedItem != "perfil") {
                            navController.navigate("profile") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
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
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .height(40.dp)
                .then(
                    if (isSelected) Modifier
                        .background(SelectedTabBackground, RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp)
                    else Modifier.width(48.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = if (isSelected) RedPrimaryBar else UnselectedGray,
                    modifier = Modifier.size(22.dp)
                )
                if (isSelected) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = label,
                        color = RedPrimaryBar,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        if (isSelected) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(3.dp)
                    .background(RedPrimaryBar, RoundedCornerShape(2.dp))
            )
        } else {
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}