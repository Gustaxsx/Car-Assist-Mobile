package com.example.car_assist_mobile.screens.editarcarro

import android.content.Context
import android.net.Uri
import android.util.Log
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

class EditCarViewModel : ViewModel() {

    var modelo by mutableStateOf("Carregando...")
    var marca by mutableStateOf("Aguarde")
    var placa by mutableStateOf("")
    var ano by mutableStateOf("")
    var cor by mutableStateOf("")
    var quilometragem by mutableStateOf("")
    var fotoUrl by mutableStateOf<String?>(null)

    var fotoSelecionadaUri by mutableStateOf<Uri?>(null)

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var papelUsuario by mutableStateOf("")

    fun carregarDadosDoVeiculo(veiculoId: Int, idUsuarioLogado: Int) {
        if (veiculoId == 0 || idUsuarioLogado == 0) return

        isLoading = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.buscarVeiculosPorUsuario(idUsuarioLogado)

                if (response.isSuccessful && response.body() != null) {
                    val listaRelacoes = response.body()!!.data?.usuario_veiculo ?: emptyList()
                    val relacao = listaRelacoes.find { it.veiculo.id == veiculoId }

                    relacao?.let {
                        papelUsuario = it.papel_usuario ?: ""

                        val veiculo = it.veiculo
                        modelo = veiculo.modelo
                        marca = veiculo.marca ?: ""
                        placa = veiculo.placa
                        ano = veiculo.ano?.toString() ?: ""
                        cor = veiculo.cor ?: ""
                        quilometragem = veiculo.quilometragem ?: ""
                        fotoUrl = veiculo.foto ?: veiculo.foto_veiculo
                    }
                }
            } catch (e: Exception) {
                Log.e("EDIT_ERROR", "Erro ao carregar dados: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    private fun prepararArquivoFoto(context: Context, uri: Uri): MultipartBody.Part? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val arquivoTemporario = File(context.cacheDir, "veiculo_edit_upload.jpg")
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

    fun atualizarVeiculo(veiculoId: Int, context: Context, onSuccess: () -> Unit) {
        if (modelo.isBlank() || placa.isBlank() || quilometragem.isBlank()) {
            errorMessage = "Preencha os campos obrigatórios."
            return
        }

        isLoading = true
        errorMessage = ""

        val mediaType = "text/plain".toMediaTypeOrNull()
        val idPart = veiculoId.toString().toRequestBody(mediaType)
        val modeloPart = modelo.trim().toRequestBody(mediaType)
        val marcaPart = marca.trim().toRequestBody(mediaType)
        val placaPart = placa.trim().uppercase().toRequestBody(mediaType)
        val anoPart = ano.trim().toRequestBody(mediaType)
        val corPart = cor.trim().toRequestBody(mediaType)
        val kmPart = quilometragem.trim().toRequestBody(mediaType)

        val fotoMultipart = fotoSelecionadaUri?.let { prepararArquivoFoto(context, it) }

        val fotoAntigaPart = if (fotoMultipart == null && !fotoUrl.isNullOrBlank()) {
            fotoUrl!!.trim().toRequestBody(mediaType)
        } else {
            null
        }

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.atualizarVeiculo(
                    idVeiculo = veiculoId,
                    idVeiculoBody = idPart,
                    modelo = modeloPart,
                    marca = marcaPart,
                    placa = placaPart,
                    ano = anoPart,
                    cor = corPart,
                    quilometragem = kmPart,
                    fotoVeiculoAntiga = fotoAntigaPart,
                    foto_veiculo = fotoMultipart
                )

                isLoading = false

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage = "Erro ao atualizar veículo no servidor."
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Falha de conexão com o servidor."
            }
        }
    }
}