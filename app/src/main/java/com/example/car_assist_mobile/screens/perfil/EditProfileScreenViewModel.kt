package com.example.car_assist_mobile.screens.perfil

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.SessionManager
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class EditProfileScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application.applicationContext)

    var nome by mutableStateOf("")
    var cpf by mutableStateOf("")
    var dataNasc by mutableStateOf("")
    var email by mutableStateOf("")

    var urlFotoBanco by mutableStateOf("")

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

    fun confirmarSenhaEAtualizar(idUsuarioLogado: Int) {
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

        val fotoUsuarioPart = urlFotoBanco.trim().toRequestBody(mediaType)

        android.util.Log.d("DEBUG_MULTIPART", "== ENVIANDO SEM MUDAR DATA/FOTO ==")

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
                    foto = null
                )

                isLoading = false

                if (response.isSuccessful) {
                    val corpoResposta = response.body()

                    if (corpoResposta?.status == true) {
                        successMessage = "Perfil atualizado com sucesso!"

                        sessionManager.salvarSessao(
                            id = idUsuarioLogado,
                            nome = nome.trim(),
                            email = email.trim(),
                            cpf = cpf,
                            dataNasc = sessionManager.getUserDataNasc(),
                            senhaAtrevia = senhaBanco
                        )
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