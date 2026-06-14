package com.example.car_assist_mobile.screens.adicionarmanutencao

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Close
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
import com.example.car_assist_mobile.ui.theme.RedDesign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddManutencaoScreen(
    navController: NavController,
    idUsuarioLogado: Int,
    idVeiculoAtual: Int,
    viewModel: AddManutencaoScreenViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(idUsuarioLogado, idVeiculoAtual) {
        viewModel.carregarPapelUsuario(idUsuarioLogado, idVeiculoAtual)
    }

    val podeEditar = viewModel.papelUsuario != "Visualizador"

    val savedState = navController.previousBackStackEntry?.savedStateHandle
    val editId = savedState?.get<Int>("edit_id")
    val isEditMode = editId != null

    fun formatarDataEdicao(isoData: String?, formatoSaida: String): String {
        if (isoData.isNullOrBlank()) return ""
        return try {
            val parser = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.getDefault())
            parser.timeZone = java.util.TimeZone.getTimeZone("UTC")
            val formatter = java.text.SimpleDateFormat(formatoSaida, java.util.Locale("pt", "BR"))
            val date = parser.parse(isoData)
            date?.let { formatter.format(it) } ?: ""
        } catch (e: Exception) { "" }
    }

    var dataAPI by remember { mutableStateOf(formatarDataEdicao(savedState?.get<String>("edit_data"), "yyyy-MM-dd")) }
    var dataUI by remember { mutableStateOf(formatarDataEdicao(savedState?.get<String>("edit_data"), "dd/MM/yyyy")) }
    var valor by remember { mutableStateOf(savedState?.get<String>("edit_custo") ?: "") }
    var km by remember { mutableStateOf(savedState?.get<String>("edit_km") ?: "") }
    var oficina by remember { mutableStateOf(savedState?.get<String>("edit_oficina") ?: "") }
    var pecas by remember { mutableStateOf(savedState?.get<String>("edit_pecas") ?: "") }
    var observacoes by remember { mutableStateOf(savedState?.get<String>("edit_obs") ?: "") }

    var selectedImagesUris by remember { mutableStateOf<List<Any>>(
        savedState?.get<ArrayList<String>>("edit_evidencias") ?: emptyList()
    ) }

    var fkIdTipoManutencao by remember { mutableStateOf<Int?>(savedState?.get<Int>("edit_tipo_id")) }
    var selectedTipoText by remember { mutableStateOf(savedState?.get<String>("edit_tipo_nome") ?: "") }

    LaunchedEffect(viewModel.isSuccess) {
        if (viewModel.isSuccess) {
            val mensagem = if (isEditMode) "Manutenção atualizada!" else "Manutenção salva com sucesso!"
            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    LaunchedEffect(viewModel.isDeleteSuccess) {
        if (viewModel.isDeleteSuccess) {
            Toast.makeText(context, "Manutenção excluída com sucesso!", Toast.LENGTH_SHORT).show()
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
                CustomBottomBar(
                    navController = navController,
                    selectedItem = "garagem",
                    idUsuarioLogado = idUsuarioLogado
                )
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
                        text = if (podeEditar) "MANUTENÇÕES" else "DETALHES (LEITURA)",
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
                    enabled = podeEditar,
                    onTipoSelected = { tipoItem ->
                        fkIdTipoManutencao = tipoItem.id
                        selectedTipoText = tipoItem.nome
                    }
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DataPickerInput(
                        valueUI = dataUI,
                        enabled = podeEditar,
                        onDateSelected = { uiDate, apiDate ->
                            dataUI = uiDate
                            dataAPI = apiDate
                        },
                        modifier = Modifier.weight(1f)
                    )
                    ManutencaoInput("Valor", value = valor, onValueChange = { valor = it }, enabled = podeEditar, placeholder = "Ex: 150.00", modifier = Modifier.weight(1f))
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ManutencaoInput("KM", value = km, onValueChange = { km = it }, enabled = podeEditar, placeholder = "Ex: 5000", modifier = Modifier.weight(1f))
                    ManutencaoInput("Oficina", value = oficina, onValueChange = { oficina = it }, enabled = podeEditar, placeholder = "Nome da Oficina", modifier = Modifier.weight(1f))
                }

                ManutencaoInput("Peças", value = pecas, onValueChange = { pecas = it }, enabled = podeEditar, placeholder = "Ex: Pastilha de freio, Óleo")

                EvidenciaImagePicker(
                    selectedImagesUris = selectedImagesUris,
                    enabled = podeEditar,
                    onImagesChanged = { selectedImagesUris = it }
                )

                ManutencaoInput("Observações", value = observacoes, onValueChange = { observacoes = it }, enabled = podeEditar, placeholder = "Escreva detalhes adicionais...")
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(20.dp))

                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = RedDesign)
                } else if (podeEditar) {
                    if (isEditMode) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.excluirManutencao(editId!!) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD32F2F)),
                                border = BorderStroke(1.dp, Color(0xFFD32F2F)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("EXCLUIR", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }

                            Button(
                                onClick = {
                                    viewModel.cadastrarManutencao(
                                        manutencaoId = editId,
                                        dataManutencao = dataAPI,
                                        custo = valor,
                                        quilometragem = km,
                                        oficina = oficina,
                                        observacoes = observacoes,
                                        pecas = pecas,
                                        fkIdTipoManutencao = fkIdTipoManutencao,
                                        fkIdUsuario = idUsuarioLogado,
                                        fkIdVeiculo = idVeiculoAtual,
                                        imagensUris = selectedImagesUris
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = RedDesign),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("SALVAR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        }
                    } else {
                        Button(
                            onClick = {
                                viewModel.cadastrarManutencao(
                                    dataManutencao = dataAPI,
                                    custo = valor,
                                    quilometragem = km,
                                    oficina = oficina,
                                    observacoes = observacoes,
                                    pecas = pecas,
                                    fkIdTipoManutencao = fkIdTipoManutencao,
                                    fkIdUsuario = idUsuarioLogado,
                                    fkIdVeiculo = idVeiculoAtual,
                                    imagensUris = selectedImagesUris
                                )
                            },
                            modifier = Modifier
                                .width(180.dp)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RedDesign),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                "SALVAR",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
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
    enabled: Boolean = true,
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
            enabled = enabled,
            placeholder = { Text(text = placeholder, color = Color.LightGray, fontSize = 13.sp) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 13.sp, color = if (enabled) Color.Black else Color.Gray),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE8E8E8),
                focusedBorderColor = RedDesign,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color(0xFFF9F9F9),
                disabledBorderColor = Color(0xFFE8E8E8)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoManutencaoDropdown(
    tiposList: List<TipoManutencaoItem>,
    selectedText: String,
    enabled: Boolean = true,
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
            onExpandedChange = { if (enabled) expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                placeholder = { Text("Selecione o tipo...", color = Color.LightGray, fontSize = 13.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = TextStyle(fontSize = 13.sp, color = if (enabled) Color.Black else Color.Gray),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFE8E8E8),
                    focusedBorderColor = RedDesign,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color(0xFFF9F9F9),
                    disabledBorderColor = Color(0xFFE8E8E8)
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
    selectedImagesUris: List<Any>,
    enabled: Boolean = true,
    onImagesChanged: (List<Any>) -> Unit,
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
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (enabled) Color.White else Color(0xFFF9F9F9),
                disabledContentColor = Color.LightGray
            ),
            border = BorderStroke(1.dp, Color(0xFFE8E8E8))
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
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
                items(selectedImagesUris) { item ->

                    val urlExtraida = if (item is Map<*, *> && item.containsKey("url")) {
                        item["url"].toString()
                    } else {
                        item
                    }

                    val modeloImagem = if (urlExtraida is String && urlExtraida.contains("localhost")) {
                        urlExtraida.replace("localhost", "10.0.2.2")
                    } else {
                        urlExtraida
                    }

                    Box(
                        modifier = Modifier.size(76.dp)
                    ) {
                        AsyncImage(
                            model = modeloImagem,
                            contentDescription = "Evidência selecionada",
                            modifier = Modifier
                                .size(66.dp)
                                .align(Alignment.BottomStart)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFE8E8E8), RoundedCornerShape(12.dp))
                                .background(Color(0xFFF5F5F5)),
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.icone_carro_moderno)
                        )

                        // Oculta o botão de deletar se não tiver permissão para editar
                        if (enabled) {
                            Surface(
                                modifier = Modifier
                                    .size(22.dp)
                                    .align(Alignment.TopEnd)
                                    .clickable { onImagesChanged(selectedImagesUris - item) },
                                shape = CircleShape,
                                color = Color(0xFFD32F2F),
                                shadowElevation = 2.dp
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Excluir Imagem",
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataPickerInput(
    valueUI: String,
    enabled: Boolean = true,
    onDateSelected: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatterUI = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale("pt", "BR"))
                        val formatterAPI = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())

                        formatterUI.timeZone = java.util.TimeZone.getTimeZone("UTC")
                        formatterAPI.timeZone = java.util.TimeZone.getTimeZone("UTC")

                        val date = java.util.Date(millis)
                        onDateSelected(formatterUI.format(date), formatterAPI.format(date))
                    }
                    showDialog = false
                }) {
                    Text("OK", color = RedDesign)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(modifier = modifier.padding(bottom = 6.dp)) {
        Text(
            text = "Data",
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = valueUI,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = TextStyle(fontSize = 13.sp, color = if (enabled) Color.Black else Color.Gray),
                placeholder = {
                    Text(
                        text = "DD/MM/AAAA",
                        color = Color.LightGray,
                        fontSize = 13.sp,
                        maxLines = 1
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFE8E8E8),
                    focusedBorderColor = RedDesign,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color(0xFFF9F9F9),
                    disabledBorderColor = Color(0xFFE8E8E8)
                )
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(enabled = enabled) { showDialog = true }
                    .background(Color.Transparent)
            )
        }
    }
}