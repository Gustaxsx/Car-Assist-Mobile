package com.example.car_assist_mobile.screens.cadastrodecarro

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // 💡 IMPORTADO
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll // 💡 IMPORTADO
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.ui.theme.Poppins

val MarromDesign = Color(0xFF73261D)

@Composable
fun RegisterCarScreen(
    navController: NavController,
    idUsuarioLogado: Int,
    viewModel: RegisterCarScreenViewModel = viewModel()
) {
    val context = LocalContext.current
    var showSuccessDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.selecionarFoto(it) }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            shape = RoundedCornerShape(20.dp),
            containerColor = Color.White,
            title = {
                Text(
                    text = "Sucesso!",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            text = {
                Text(
                    text = "Veículo cadastrado na sua garagem com sucesso!",
                    fontFamily = Poppins,
                    color = Color.Gray
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate("garagem/$idUsuarioLogado") {
                            popUpTo("garagem/{idUsuario}") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MarromDesign),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Continuar", fontFamily = Poppins, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(24.dp))
                    Surface(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable { if (!viewModel.isLoading) navController.popBackStack() },
                        shape = CircleShape,
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Black)
                        }
                    }
                    Text(
                        text = "CADASTRAR CARRO",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontFamily = Poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(69.dp))
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        },
        bottomBar = {
            CustomBottomBar(
                navController = navController,
                selectedItem = "garagem",
                idUsuarioLogado = idUsuarioLogado
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(110.dp),
                    shape = CircleShape,
                    color = Color(0xFFF9F9F9),
                    border = BorderStroke(2.dp, MarromDesign.copy(alpha = 0.1f))
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.clickable { galeriaLauncher.launch("image/*") }
                    ) {
                        if (viewModel.fotoVeiculoUri != null) {
                            AsyncImage(
                                model = viewModel.fotoVeiculoUri,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.icone_foto),
                                contentDescription = null,
                                modifier = Modifier.size(45.dp),
                                tint = Color.LightGray
                            )
                        }
                    }
                }

                Surface(
                    modifier = Modifier.size(32.dp).offset(x = (-5).dp, y = (-5).dp),
                    shape = CircleShape,
                    color = MarromDesign
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = R.drawable.icone_pincel),
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (viewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = viewModel.errorMessage,
                    color = Color.Red,
                    fontFamily = Poppins,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 12.dp),
                    textAlign = TextAlign.Center
                )
            }

            CarInput(label = "Modelo", value = viewModel.modelo, onValueChange = { viewModel.modelo = it })
            CarInput(label = "Marca", value = viewModel.marca, onValueChange = { viewModel.marca = it })
            CarInput(label = "Placa", value = viewModel.placa, onValueChange = { viewModel.placa = it.uppercase() })

            CarInput(
                label = "Quilometragem (km)",
                value = viewModel.quilometragem,
                onValueChange = { viewModel.quilometragem = it },
                keyboardType = KeyboardType.Number
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    CarInput(label = "Ano", value = viewModel.ano, onValueChange = { viewModel.ano = it }, isDropdown = true)
                }
                Box(modifier = Modifier.weight(1f)) {
                    CarInput(label = "Cor", value = viewModel.cor, onValueChange = { viewModel.cor = it }, isDropdown = true)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.cadastrarVeiculo(idUsuario = idUsuarioLogado, context = context) {
                        showSuccessDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MarromDesign),
                shape = RoundedCornerShape(14.dp),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text(
                        "Adicionar Veículo",
                        color = Color.White,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CarInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isDropdown: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(
            text = label,
            fontFamily = Poppins,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = TextStyle(fontFamily = Poppins, fontSize = 14.sp, color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MarromDesign,
                unfocusedBorderColor = Color(0xFFEFEFEF),
                unfocusedContainerColor = Color(0xFFF9F9F9),
                focusedContainerColor = Color.White
            ),
            trailingIcon = {
                if (isDropdown) {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )
    }
}