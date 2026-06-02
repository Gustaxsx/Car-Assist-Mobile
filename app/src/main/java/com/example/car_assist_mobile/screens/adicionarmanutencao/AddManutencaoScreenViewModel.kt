package com.example.car_assist_mobile.screens.adicionarmanutencao

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue // IMPORTANTE: Adicionado para o 'by' funcionar
import androidx.compose.runtime.setValue // IMPORTANTE: Adicionado para o 'by' funcionar
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.TipoManutencaoItem // IMPORTANTE: Import do seu Model
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

class AddManutencaoScreenViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isSuccess by mutableStateOf(false)
        private set

    // --- VARIÁVEL QUE ESTAVA FALTANDO ---
    var tiposManutencao by mutableStateOf<List<TipoManutencaoItem>>(emptyList())
        private set

    private val context = application.applicationContext

    // --- BLOCO QUE DISPARA A REQUISIÇÃO ASSIM QUE A VIEWMODEL INICIA ---
    init {
        carregarTiposManutencao()
    }

    // --- FUNÇÃO QUE ESTAVA FALTANDO PARA BUSCAR OS TIPOS DO BANCO ---
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

    fun cadastrarManutencao(
        dataManutencao: String,
        custo: String,
        quilometragem: String,
        oficina: String,
        observacoes: String,
        pecas: String,
        fkIdTipoManutencao: Int?,
        fkIdUsuario: Int,
        fkIdVeiculo: Int,
        imagensUris: List<Uri>
    ) {
        if (fkIdTipoManutencao == null) {
            errorMessage = "Por favor, selecione um tipo de manutenção."
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

                val evidenciasParts: List<MultipartBody.Part> = imagensUris.mapIndexed { index, uri ->
                    prepareImagePart("evidencias", uri, index)
                }.filterNotNull()

                val response = RetrofitClient.apiService.adicionarManutencao(
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