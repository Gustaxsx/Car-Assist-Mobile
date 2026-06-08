//package com.example.car_assist_mobile.screens.lembrete
//
//import android.widget.Toast
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.example.car_assist_mobile.screens.adicionarlembrete.AddLembreteViewModel
//import com.example.car_assist_mobile.ui.theme.Poppins
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddLembreteScreen(
//    navController: NavController,
//    veiculoId: Int,
//    viewModel: AddLembreteViewModel = viewModel()
//) {
//    val context = LocalContext.current
//
//    LaunchedEffect(viewModel.mensagemSucesso, viewModel.mensagemErro) {
//        if (viewModel.mensagemSucesso.isNotBlank()) {
//            Toast.makeText(context, viewModel.mensagemSucesso, Toast.LENGTH_SHORT).show()
//            navController.popBackStack()
//        }
//        if (viewModel.mensagemErro.isNotBlank()) {
//            Toast.makeText(context, viewModel.mensagemErro, Toast.LENGTH_LONG).show()
//        }
//    }
//
//    Scaffold(containerColor = Color.White) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(horizontal = 32.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(10.dp))
//
//            // Header
//            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
//                IconButton(
//                    onClick = { navController.popBackStack() },
//                    modifier = Modifier.border(0.5.dp, Color.LightGray, CircleShape).size(45.dp)
//                ) {
//                    Icon(Icons.Default.ArrowBack, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
//                }
//                Text(
//                    text = "NOVO LEMBRETE",
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.Center,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Medium
//                )
//            }
//
//            Spacer(modifier = Modifier.height(40.dp))
//
//            OutlinedTextField(
//                value = viewModel.titulo,
//                onValueChange = { viewModel.titulo = it },
//                label = { Text("Título (Ex: Troca de Óleo)", fontFamily = Poppins) },
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(12.dp),
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = Color(0xFF910D0D)
//                )
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedTextField(
//                value = viewModel.descricao,
//                onValueChange = { viewModel.descricao = it },
//                label = { Text("Descrição detalhada", fontFamily = Poppins) },
//                modifier = Modifier.fillMaxWidth().height(120.dp),
//                shape = RoundedCornerShape(12.dp),
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = Color(0xFF910D0D)
//                )
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedTextField(
//                value = viewModel.dataVencimento,
//                onValueChange = { viewModel.dataVencimento = it },
//                label = { Text("Data de Vencimento (AAAA-MM-DD)", fontFamily = Poppins) },
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(12.dp),
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = Color(0xFF910D0D)
//                )
//            )
//
//            Spacer(modifier = Modifier.height(40.dp))
//
//            Button(
//                onClick = { viewModel.salvarLembrete(veiculoId) {} },
//                modifier = Modifier.height(48.dp).fillMaxWidth(0.8f),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF910D0D)),
//                shape = RoundedCornerShape(24.dp),
//                enabled = !viewModel.isLoading
//            ) {
//                if (viewModel.isLoading) {
//                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
//                } else {
//                    Text("SALVAR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
//                }
//            }
//        }
//    }
//}