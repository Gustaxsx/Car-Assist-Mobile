package com.example.car_assist_mobile.screens.adicionarmanutencao

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.TipoManutencaoItem
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.net.URL

class AddManutencaoScreenViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isSuccess by mutableStateOf(false)
        private set

    var isDeleteSuccess by mutableStateOf(false)
        private set

    var tiposManutencao by mutableStateOf<List<TipoManutencaoItem>>(emptyList())
        private set

    var papelUsuario by mutableStateOf("")
        private set

    private val context = application.applicationContext

    init {
        carregarTiposManutencao()
    }

    fun carregarPapelUsuario(idUsuarioLogado: Int, veiculoId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.buscarVeiculosPorUsuario(idUsuarioLogado)
                if (response.isSuccessful) {
                    val relacao = response.body()?.data?.usuario_veiculo?.find { it.veiculo.id == veiculoId }
                    papelUsuario = relacao?.papel_usuario ?: ""
                }
            } catch (e: Exception) {
                // Silencioso, mantém o valor vazio
            }
        }
    }

    fun carregarTiposManutencao() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.buscarTiposManutencao()
                if (response.isSuccessful && response.body()?.status == true) {
                    tiposManutencao = response.body()?.data?.tipos_manutencao ?: emptyList()
                } else {
                    errorMessage = "Não foi possível carregar os tipos de manutenção."
                }
            } catch (e: Exception) {
                errorMessage = "Erro de conexão ao buscar tipos de manutenção."
            }
        }
    }

    fun excluirManutencao(manutencaoId: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            isDeleteSuccess = false

            try {
                val response = RetrofitClient.apiService.deletarManutencao(manutencaoId)
                if (response.isSuccessful) {
                    isDeleteSuccess = true
                } else {
                    errorMessage = "Erro ao excluir: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Falha ao conectar: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun cadastrarManutencao(
        manutencaoId: Int? = null,
        dataManutencao: String,
        custo: String,
        quilometragem: String,
        oficina: String,
        observacoes: String,
        pecas: String,
        fkIdTipoManutencao: Int?,
        fkIdUsuario: Int,
        fkIdVeiculo: Int,
        imagensUris: List<Any>
    ) {
        if (fkIdTipoManutencao == null) {
            errorMessage = "Por favor, selecione um tipo de manutenção."
            return
        }

        if (imagensUris.isEmpty()) {
            errorMessage = "É obrigatório adicionar pelo menos uma imagem como evidência."
            return
        }

        if (imagensUris.size > 3) {
            errorMessage = "Você só pode selecionar no máximo 3 imagens como evidência."
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            isSuccess = false

            try {
                val dataPart = createPartFromString(dataManutencao)
                val custoPart = createPartFromString(custo)
                val kmPart = createPartFromString(quilometragem)
                val oficinaPart = createPartFromString(oficina)
                val obsPart = createPartFromString(observacoes)
                val pecasPart = createPartFromString(pecas)
                val tipoPart = createPartFromString(fkIdTipoManutencao.toString())
                val usuarioPart = createPartFromString(fkIdUsuario.toString())
                val veiculoPart = createPartFromString(fkIdVeiculo.toString())

                val evidenciasParts = mutableListOf<MultipartBody.Part>()

                withContext(Dispatchers.IO) {
                    imagensUris.forEachIndexed { index, item ->
                        val itemProcessado = if (item is Map<*, *> && item.containsKey("url")) {
                            item["url"].toString()
                        } else {
                            item
                        }

                        if (itemProcessado is String) {
                            val part = prepareImagePartFromUrl("evidencias", itemProcessado, index)
                            if (part != null) evidenciasParts.add(part)
                        } else if (itemProcessado is Uri) {
                            val part = prepareImagePart("evidencias", itemProcessado, index)
                            if (part != null) evidenciasParts.add(part)
                        }
                    }
                }

                val response = if (manutencaoId != null) {
                    RetrofitClient.apiService.atualizarManutencao(
                        manutencaoId = manutencaoId,
                        dataManutencao = dataPart,
                        custo = custoPart,
                        quilometragem = kmPart,
                        oficina = oficinaPart,
                        observacoes = obsPart,
                        idTipoManutencao = tipoPart,
                        idUsuario = usuarioPart,
                        idVeiculo = veiculoPart,
                        pecas = pecasPart,
                        evidencias = if (evidenciasParts.isNotEmpty()) evidenciasParts else null
                    )
                } else {
                    RetrofitClient.apiService.adicionarManutencao(
                        dataManutencao = dataPart,
                        custo = custoPart,
                        quilometragem = kmPart,
                        oficina = oficinaPart,
                        observacoes = obsPart,
                        idTipoManutencao = tipoPart,
                        idUsuario = usuarioPart,
                        idVeiculo = veiculoPart,
                        pecas = pecasPart,
                        evidencias = if (evidenciasParts.isNotEmpty()) evidenciasParts else null
                    )
                }

                if (response.isSuccessful) {
                    isSuccess = true
                } else {
                    errorMessage = "Erro no servidor: ${response.code()} - ${response.errorBody()?.string()}"
                }

            } catch (e: Exception) {
                errorMessage = "Falha na conexão: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun createPartFromString(value: String): RequestBody {
        return value.toRequestBody(MultipartBody.FORM)
    }

    private fun prepareImagePartFromUrl(partName: String, urlString: String, index: Int): MultipartBody.Part? {
        return try {
            val finalUrl = if (urlString.contains("localhost")) {
                urlString.replace("localhost", "10.0.2.2")
            } else {
                urlString
            }
            val connection = URL(finalUrl).openConnection()
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            val bytes = connection.getInputStream().readBytes()
            val requestFile = bytes.toRequestBody("image/jpeg".toMediaTypeOrNull(), 0, bytes.size)
            val fileName = "evidencia_mantida_$index.jpg"
            MultipartBody.Part.createFormData(partName, fileName, requestFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun prepareImagePart(partName: String, fileUri: Uri, index: Int): MultipartBody.Part? {
        return try {
            val contentResolver = context.contentResolver
            val mimeType = contentResolver.getType(fileUri) ?: "image/jpeg"
            val inputStream: InputStream? = contentResolver.openInputStream(fileUri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()

            if (bytes != null) {
                val requestFile = bytes.toRequestBody(mimeType.toMediaTypeOrNull(), 0, bytes.size)
                val fileName = "evidencia_$index.${mimeType.substringAfter("/")}"
                MultipartBody.Part.createFormData(partName, fileName, requestFile)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun clearError() {
        errorMessage = null
    }
}