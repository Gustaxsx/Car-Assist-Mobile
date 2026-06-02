package com.example.car_assist_mobile.screens.adicionarmanutencao

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.car_assist_mobile.R
import com.example.car_assist_mobile.components.CustomBottomBar
import com.example.car_assist_mobile.data.model.TipoManutencaoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddManutencaoScreen(
    navController: NavController,
    viewModel: AddManutencaoScreenViewModel = viewModel()
) {
    val context = LocalContext.current

    var data by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var km by remember { mutableStateOf("") }
    var oficina by remember { mutableStateOf("") }
    var pecas by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }
    var selectedImagesUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    // Estados para o Select/Dropdown do Tipo de Manutenção
    var fkIdTipoManutencao by remember { mutableStateOf<Int?>(null) }
    var selectedTipoText by remember { mutableStateOf("") }

    LaunchedEffect(viewModel.isSuccess) {
        if (viewModel.isSuccess) {
            Toast.makeText(context, "Manutenção salva com sucesso!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    LaunchedEffect(viewModel.errorMessage) {
        viewModel.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .border(0.5.dp, Color.LightGray, CircleShape)
                            .size(45.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }
                    Text(
                        text = "MANUTENÇÕES",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                TipoManutencaoDropdown(
                    tiposList = viewModel.tiposManutencao,
                    selectedText = selectedTipoText,
                    onTipoSelected = { tipoItem ->
                        fkIdTipoManutencao = tipoItem.id
                        selectedTipoText = tipoItem.nome
                    }
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ManutencaoInput("Data", value = data, onValueChange = { data = it }, placeholder = "Ex: 2025-01-20", modifier = Modifier.weight(1f))
                    ManutencaoInput("Valor", value = valor, onValueChange = { valor = it }, placeholder = "Ex: 150.00", modifier = Modifier.weight(1f))
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ManutencaoInput("KM", value = km, onValueChange = { km = it }, placeholder = "Ex: 5000", modifier = Modifier.weight(1f))
                    ManutencaoInput("Oficina", value = oficina, onValueChange = { oficina = it }, placeholder = "Nome da Oficina", modifier = Modifier.weight(1f))
                }

                ManutencaoInput("Peças", value = pecas, onValueChange = { pecas = it }, placeholder = "Ex: Pastilha de freio, Óleo")

                EvidenciaImagePicker(
                    selectedImagesUris = selectedImagesUris,
                    onImagesChanged = { selectedImagesUris = it }
                )

                ManutencaoInput("Observações", value = observacoes, onValueChange = { observacoes = it }, placeholder = "Escreva detalhes adicionais...")
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(20.dp))

                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.Gray)
                } else {
                    Button(
                        onClick = {
                            viewModel.cadastrarManutencao(
                                dataManutencao = data,
                                custo = valor,
                                quilometragem = km,
                                oficina = oficina,
                                observacoes = observacoes,
                                pecas = pecas,
                                fkIdTipoManutencao = fkIdTipoManutencao, // Passando ID vindo do Select
                                fkIdUsuario = 1,        // ID fixo para teste
                                fkIdVeiculo = 9,        // ID fixo para teste
                                imagensUris = selectedImagesUris
                            )
                        },
                        modifier = Modifier
                            .width(180.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "SALVAR",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ManutencaoInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = 6.dp)) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, color = Color.LightGray, fontSize = 13.sp) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 13.sp, color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE8E8E8),
                focusedBorderColor = Color.Gray,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoManutencaoDropdown(
    tiposList: List<TipoManutencaoItem>,
    selectedText: String,
    onTipoSelected: (TipoManutencaoItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(bottom = 6.dp)) {
        Text(
            text = "Tipo de Manutenção",
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Selecione o tipo...", color = Color.LightGray, fontSize = 13.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = TextStyle(fontSize = 13.sp, color = Color.Black),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFE8E8E8),
                    focusedBorderColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tiposList.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(text = tipo.nome, color = Color.Black, fontSize = 13.sp) },
                        onClick = {
                            onTipoSelected(tipo)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EvidenciaImagePicker(
    selectedImagesUris: List<Uri>,
    onImagesChanged: (List<Uri>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            if ((selectedImagesUris + uris).size <= 3) {
                onImagesChanged(selectedImagesUris + uris)
            } else {
                Toast.makeText(context, "Limite máximo de 3 imagens atingido!", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(modifier = modifier.padding(bottom = 6.dp)) {
        Text(
            text = "Evidência",
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
        )

        OutlinedButton(
            onClick = {
                if (selectedImagesUris.size < 3) {
                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                } else {
                    Toast.makeText(context, "Você já selecionou 3 imagens.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE8E8E8)),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (selectedImagesUris.size >= 3) "Limite máximo atingido" else "Selecionar imagens...",
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Icon(
                    painter = painterResource(id = R.drawable.icone_clip),
                    contentDescription = "Adicionar arquivos",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        if (selectedImagesUris.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedImagesUris) { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = "Evidência selecionada",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                onImagesChanged(selectedImagesUris - uri)
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}