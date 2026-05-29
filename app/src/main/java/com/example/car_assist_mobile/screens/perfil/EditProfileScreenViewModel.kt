package com.example.car_assist_mobile.screens.perfil

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.SessionManager
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class EditProfileScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application.applicationContext)

    var nome by mutableStateOf("")
    var cpf by mutableStateOf("")
    var dataNasc by mutableStateOf("")
    var email by mutableStateOf("")

    var urlFotoBanco by mutableStateOf("")

    var fotoSelecionadaUri by mutableStateOf<Uri?>(null)
        private set

    private var senhaBanco = ""

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var successMessage by mutableStateOf("")

    var mostrarDialogSenha by mutableStateOf(false)
    var senhaConfirmacao by mutableStateOf("")
    var erroSenhaDialog by mutableStateOf("")

    fun carregarPerfil(idUsuarioLogado: Int) {
        nome = sessionManager.getUserName()
        cpf = sessionManager.getUserCpf()
        email = sessionManager.getUserEmail()
        senhaBanco = sessionManager.getUserPassword()

        val dataBruta = sessionManager.getUserDataNasc()
        dataNasc = formatarDataParaExibicao(dataBruta)

        if (nome.isBlank() || cpf.isBlank() || urlFotoBanco.isBlank()) {
            buscarDadosFaltantesNoServidor(idUsuarioLogado)
        }
    }

    fun selecionarNovaFoto(uri: Uri) {
        fotoSelecionadaUri = uri
    }

    private fun buscarDadosFaltantesNoServidor(idUsuarioLogado: Int) {
        if (idUsuarioLogado == 0) return

        isLoading = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.buscarUsuarioPorId(idUsuarioLogado)
                isLoading = false

                if (response.isSuccessful && response.body() != null) {
                    val listaUsuarios = response.body()!!.data.usuario
                    if (!listaUsuarios.isNullOrEmpty()) {
                        val user = listaUsuarios.first()

                        nome = user.nome ?: ""
                        cpf = user.cpf ?: ""
                        email = user.email ?: email
                        senhaBanco = user.senha ?: senhaBanco
                        urlFotoBanco = user.foto_usuario ?: ""

                        val dataString = user.data_nascimento ?: ""
                        dataNasc = formatarDataParaExibicao(dataString)

                        sessionManager.salvarSessao(
                            id = idUsuarioLogado,
                            nome = nome,
                            email = email,
                            cpf = cpf,
                            dataNasc = dataString,
                            senhaAtrevia = senhaBanco
                        )
                    }
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Falha ao sincronizar dados com o servidor."
                e.printStackTrace()
            }
        }
    }

    private fun formatarDataParaExibicao(data: String): String {
        return if (data.isNotBlank()) {
            if (data.contains("T")) {
                val dataLimpa = data.substringBefore("T")
                val partes = dataLimpa.split("-")
                if (partes.size == 3) "${partes[2]}/${partes[1]}/${partes[0]}" else data
            } else if (data.contains("-")) {
                val partes = data.split("-")
                if (partes.size == 3) "${partes[2]}/${partes[1]}/${partes[0]}" else data
            } else {
                data
            }
        } else {
            ""
        }
    }

    fun acionarSalvar() {
        if (nome.isBlank() || email.isBlank()) {
            errorMessage = "Preencha todos os campos obrigatórios."
            return
        }
        errorMessage = ""
        senhaConfirmacao = ""
        erroSenhaDialog = ""
        mostrarDialogSenha = true
    }

    private fun prepararArquivoFoto(context: Context, uri: Uri): MultipartBody.Part? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val arquivoTemporario = File(context.cacheDir, "perfil_upload.jpg")
            val outputStream = FileOutputStream(arquivoTemporario)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            val requestFile = arquivoTemporario.asRequestBody("image/jpeg".toMediaTypeOrNull())

            return MultipartBody.Part.createFormData(
                "foto_usuario",
                arquivoTemporario.name,
                requestFile
            )

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun confirmarSenhaEAtualizar(idUsuarioLogado: Int, context: Context) {
        if (senhaConfirmacao.trim() != senhaBanco.trim()) {
            erroSenhaDialog = "Senha incorreta. Verifique os dados."
            return
        }

        mostrarDialogSenha = false
        isLoading = true
        errorMessage = ""
        successMessage = ""

        val mediaType = "text/plain".toMediaTypeOrNull()
        val idPart = idUsuarioLogado.toString().toRequestBody(mediaType)
        val nomePart = nome.trim().toRequestBody(mediaType)
        val emailPart = email.trim().toRequestBody(mediaType)
        val cpfPart = cpf.trim().toRequestBody(mediaType)
        val senhaPart = senhaBanco.trim().toRequestBody(mediaType)

        val fotoMultipart = fotoSelecionadaUri?.let { prepararArquivoFoto(context, it) }

        val fotoUsuarioPart = if (fotoMultipart == null) {
            urlFotoBanco.trim().toRequestBody(mediaType)
        } else {
            null
        }

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.atualizarUsuario(
                    id = idUsuarioLogado,
                    idUsuarioBody = idPart,
                    nome = nomePart,
                    email = emailPart,
                    cpf = cpfPart,
                    fotoUsuarioAntiga = fotoUsuarioPart,
                    senha = senhaPart,
                    foto = fotoMultipart
                )

                isLoading = false

                if (response.isSuccessful) {
                    val corpoResposta = response.body()

                    if (corpoResposta?.status == true) {
                        successMessage = "Perfil atualizado com sucesso!"
                        fotoSelecionadaUri = null

                        buscarDadosFaltantesNoServidor(idUsuarioLogado)
                    } else {
                        errorMessage = corpoResposta?.message ?: "Erro interno da API ao atualizar."
                    }
                } else {
                    errorMessage = "Erro interno do servidor."
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Falha de conexão ao salvar alterações."
            }
        }
    }
}