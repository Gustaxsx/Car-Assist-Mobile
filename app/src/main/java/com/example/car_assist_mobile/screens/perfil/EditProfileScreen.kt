package com.example.car_assist_mobile.screens.perfil

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.data.SessionManager
import com.example.car_assist_mobile.ui.theme.Poppins
import com.example.car_assist_mobile.ui.theme.RedDesign

@Composable
fun EditProfileScreen(
    navController: NavController,
    idUsuarioLogado: Int,
    viewModel: EditProfileScreenViewModel = viewModel()
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    var mostrarDialogLogout by remember { mutableStateOf(false) }

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.selecionarNovaFoto(it) }
    }

    LaunchedEffect(idUsuarioLogado) {
        viewModel.carregarPerfil(idUsuarioLogado)
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
                            .clickable { navController.popBackStack() },
                        shape = CircleShape,
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Black)
                        }
                    }
                    Text(
                        text = "EDITAR PERFIL",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontFamily = Poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Surface(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable { mostrarDialogLogout = true },
                        shape = CircleShape,
                        color = Color(0xFFFFF0F0),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Sair da Conta",
                                modifier = Modifier.size(20.dp),
                                tint = RedDesign
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        },
        bottomBar = {
            CustomBottomBar(
                navController = navController,
                selectedItem = "perfil",
                idUsuarioLogado = idUsuarioLogado
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            if (viewModel.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = RedDesign)
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    if (viewModel.errorMessage.isNotBlank()) {
                        Text(viewModel.errorMessage, color = Color.Red, fontFamily = Poppins, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 24.dp), textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    if (viewModel.successMessage.isNotBlank()) {
                        Text(viewModel.successMessage, color = Color(0xFF2E7D32), fontFamily = Poppins, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 24.dp), textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier.clickable { galeriaLauncher.launch("image/*") }
                    ) {
                        Surface(
                            modifier = Modifier.size(110.dp),
                            shape = CircleShape,
                            color = Color(0xFFF9F9F9),
                            border = BorderStroke(2.dp, RedDesign.copy(alpha = 0.1f))
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (viewModel.fotoSelecionadaUri != null) {
                                    AsyncImage(
                                        model = viewModel.fotoSelecionadaUri,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                } else if (viewModel.urlFotoBanco.isNotBlank()) {
                                    AsyncImage(
                                        model = viewModel.urlFotoBanco,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                                        contentScale = ContentScale.Crop,
                                        error = painterResource(id = R.drawable.icone_user)
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icone_user),
                                        contentDescription = null,
                                        modifier = Modifier.size(70.dp),
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                        }

                        Surface(
                            modifier = Modifier.size(32.dp).offset(x = (-5).dp, y = (-5).dp),
                            shape = CircleShape,
                            color = RedDesign
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

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(32.dp))
                        Column(modifier = Modifier.weight(1f)) {

                            EditField(label = "Nome", value = viewModel.nome, onValueChange = { viewModel.nome = it })

                            Spacer(modifier = Modifier.height(16.dp))

                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text("CPF", fontFamily = Poppins, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                Spacer(modifier = Modifier.height(6.dp))
                                Surface(
                                    modifier = Modifier.fillMaxWidth().height(52.dp),
                                    shape = RoundedCornerShape(14.dp),
                                    color = Color(0xFFF5F5F5),
                                    border = BorderStroke(1.dp, Color(0xFFEFEFEF))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(text = viewModel.cpf, color = Color.Gray, fontSize = 14.sp, fontFamily = Poppins)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text("Data de Nascimento", fontFamily = Poppins, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                Spacer(modifier = Modifier.height(6.dp))
                                Surface(
                                    modifier = Modifier.fillMaxWidth().height(52.dp),
                                    shape = RoundedCornerShape(14.dp),
                                    color = Color(0xFFF5F5F5),
                                    border = BorderStroke(1.dp, Color(0xFFEFEFEF))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(text = viewModel.dataNasc, color = Color.Gray, fontSize = 14.sp, fontFamily = Poppins)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            EditField(label = "E-mail", value = viewModel.email, onValueChange = { viewModel.email = it })

                            Spacer(modifier = Modifier.height(40.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = { navController.popBackStack() },
                                    modifier = Modifier.weight(1f).height(52.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Text("Cancelar", color = Color.Gray, fontFamily = Poppins, fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = { viewModel.acionarSalvar() },
                                    modifier = Modifier.weight(1f).height(52.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = RedDesign),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Text("Salvar", color = Color.White, fontFamily = Poppins, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(32.dp))
                    }
                }
            }

            if (viewModel.mostrarDialogSenha) {
                AlertDialog(
                    onDismissRequest = { viewModel.mostrarDialogSenha = false },
                    shape = RoundedCornerShape(20.dp),
                    containerColor = Color.White,
                    title = {
                        Text(
                            text = "Confirme sua Senha",
                            fontFamily = Poppins,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    },
                    text = {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Para aplicar as alterações, informe a sua senha de acesso:",
                                fontFamily = Poppins,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = viewModel.senhaConfirmacao,
                                onValueChange = { viewModel.senhaConfirmacao = it },
                                placeholder = { Text("Senha do aplicativo", fontFamily = Poppins) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                textStyle = TextStyle(fontFamily = Poppins, fontSize = 14.sp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = RedDesign,
                                    unfocusedBorderColor = Color(0xFFEFEFEF)
                                )
                            )
                            if (viewModel.erroSenhaDialog.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = viewModel.erroSenhaDialog,
                                    color = Color.Red,
                                    fontFamily = Poppins,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { viewModel.confirmarSenhaEAtualizar(idUsuarioLogado, context) },
                            colors = ButtonDefaults.buttonColors(containerColor = RedDesign),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Confirmar", fontFamily = Poppins, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { viewModel.mostrarDialogSenha = false },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color(0xFFDCDCDC))
                        ) {
                            Text("Cancelar", fontFamily = Poppins, fontWeight = FontWeight.Bold, color = Color.Gray)
                        }
                    }
                )
            }

            if (mostrarDialogLogout) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogLogout = false },
                    shape = RoundedCornerShape(20.dp),
                    containerColor = Color.White,
                    title = {
                        Text("Sair da conta", fontFamily = Poppins, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    },
                    text = {
                        Text(
                            text = "Tem certeza que deseja sair do aplicativo Car Assist? Você precisará fazer login novamente.",
                            fontFamily = Poppins,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                sessionManager.limparSessao()
                                mostrarDialogLogout = false
                                navController.navigate("login") {
                                    popUpTo(0)
                                    launchSingleTop = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = RedDesign),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Sair", fontFamily = Poppins, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { mostrarDialogLogout = false },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color(0xFFDCDCDC))
                        ) {
                            Text("Cancelar", fontFamily = Poppins, fontWeight = FontWeight.Bold, color = Color.Gray)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EditField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
            textStyle = TextStyle(fontFamily = Poppins, fontSize = 14.sp, color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RedDesign,
                unfocusedBorderColor = Color(0xFFEFEFEF),
                unfocusedContainerColor = Color(0xFFF9F9F9),
                focusedContainerColor = Color.White
            )
        )
    }
}