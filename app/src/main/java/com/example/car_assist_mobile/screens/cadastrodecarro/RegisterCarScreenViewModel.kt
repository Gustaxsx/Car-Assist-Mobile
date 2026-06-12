package com.example.car_assist_mobile.screens.cadastrodecarro

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class RegisterCarScreenViewModel : ViewModel() {

    var modelo by mutableStateOf("")
    var marca by mutableStateOf("")
    var placa by mutableStateOf("")
    var ano by mutableStateOf("")
    var cor by mutableStateOf("")

    // 💡 NOVO: Variável de quilometragem
    var quilometragem by mutableStateOf("")

    var fotoVeiculoUri by mutableStateOf<Uri?>(null)
        private set

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun selecionarFoto(uri: Uri) {
        fotoVeiculoUri = uri
    }

    private fun prepararArquivoFoto(context: Context, uri: Uri): MultipartBody.Part? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val arquivoTemporario = File(context.cacheDir, "veiculo_upload.jpg")
            val outputStream = FileOutputStream(arquivoTemporario)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            val requestFile = arquivoTemporario.asRequestBody("image/jpeg".toMediaTypeOrNull())

            return MultipartBody.Part.createFormData("foto_veiculo", arquivoTemporario.name, requestFile)

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun cadastrarVeiculo(idUsuario: Int, context: Context, onSuccess: () -> Unit) {
        if (modelo.isBlank() || marca.isBlank() || placa.isBlank() || ano.isBlank() || cor.isBlank() || quilometragem.isBlank()) {
            errorMessage = "Preencha todos os campos obrigatórios."
            return
        }

        isLoading = true
        errorMessage = ""

        val mediaType = "text/plain".toMediaTypeOrNull()
        val idUsuarioPart = idUsuario.toString().toRequestBody(mediaType)
        val modeloPart = modelo.trim().toRequestBody(mediaType)
        val marcaPart = marca.trim().toRequestBody(mediaType)
        val placaPart = placa.trim().uppercase().toRequestBody(mediaType)
        val anoPart = ano.trim().toRequestBody(mediaType)
        val corPart = cor.trim().toRequestBody(mediaType)

        val quilometragemPart = quilometragem.trim().toRequestBody(mediaType)

        val fotoMultipart = fotoVeiculoUri?.let { prepararArquivoFoto(context, it) }

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.cadastrarVeiculo(
                    idUsuario = idUsuarioPart,
                    modelo = modeloPart,
                    marca = marcaPart,
                    placa = placaPart,
                    ano = anoPart,
                    cor = corPart,
                    quilometragem = quilometragemPart,
                    foto_veiculo = fotoMultipart
                )

                isLoading = false

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage = "Erro interno da API ao cadastrar o veículo."
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Falha de conexão ao cadastrar veículo."
                e.printStackTrace()
            }
        }
    }
}