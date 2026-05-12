package com.example.car_assist_mobile.screens.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
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

data class Message(val text: String, val isFromUser: Boolean)

@Composable
fun ChatBotScreen(navController: NavController) {
    var chatInput by remember { mutableStateOf("") }

    val messages = remember {
        mutableStateListOf(
            Message("Olá! Como posso ajudar você com seu veículo hoje?", false),
            Message("Gostaria de saber quando é minha próxima manutenção.", true),
            Message("Verifiquei aqui que seu Fastback Abarth precisa trocar o óleo em 500km.", false),
            Message("Obrigado!", true)
        )
    }

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
                .padding(horizontal = 24.dp)
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
                    text = "CHATBOT",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFFCCCCCC), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(messages) { message ->
                        ChatBubble(message)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = chatInput,
                    onValueChange = { chatInput = it },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedContainerColor = Color(0xFFD9D9D9),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    placeholder = { Text("Digite aqui...", fontSize = 14.sp) }
                )

                Surface(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            if (chatInput.isNotBlank()) {
                                messages.add(Message(chatInput, true))
                                chatInput = ""
                            }
                        },
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    val alignment = if (message.isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    val bgColor = if (message.isFromUser) Color.White else Color(0xFF333333)
    val textColor = if (message.isFromUser) Color.Black else Color.White
    val shape = if (message.isFromUser) {
        RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = alignment) {
        Surface(
            color = bgColor,
            shape = shape,
            modifier = Modifier.widthIn(max = 240.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = textColor,
                fontSize = 14.sp
            )
        }
    }
}