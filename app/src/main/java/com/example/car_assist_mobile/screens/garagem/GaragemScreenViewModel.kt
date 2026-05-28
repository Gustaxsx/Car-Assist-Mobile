package com.example.car_assist_mobile.screens.garagem

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.SessionManager
import com.example.car_assist_mobile.data.model.VeiculoResponse
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

class GaragemScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application.applicationContext)

    var listaVeiculos by mutableStateOf<List<VeiculoResponse>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    var nomeUsuario by mutableStateOf("Olá!")
    var emailUsuario by mutableStateOf("")
    var urlFotoUsuario by mutableStateOf("")

    fun carregarDadosDoUsuario(idUsuarioLogado: Int) {
        val nomeSalvo = sessionManager.getUserName()
        emailUsuario = sessionManager.getUserEmail()

        if (nomeSalvo.isNotBlank()) {
            nomeUsuario = "Olá $nomeSalvo!"
        } else {
            buscarDadosUsuarioNoServidor(idUsuarioLogado)
        }
    }

    private fun buscarDadosUsuarioNoServidor(idUsuarioLogado: Int) {
        if (idUsuarioLogado == 0) return

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.buscarUsuarioPorId(idUsuarioLogado)
                if (response.isSuccessful && response.body() != null) {
                    val listaUsuarios = response.body()!!.data.usuario
                    if (!listaUsuarios.isNullOrEmpty()) {
                        val user = listaUsuarios.first()

                        val nomeApi = user.nome ?: ""
                        nomeUsuario = "Olá $nomeApi!"
                        emailUsuario = user.email ?: emailUsuario
                        urlFotoUsuario = user.foto_usuario ?: ""

                        sessionManager.salvarSessao(
                            id = idUsuarioLogado,
                            nome = nomeApi,
                            email = emailUsuario,
                            cpf = user.cpf ?: "",
                            dataNasc = user.data_nascimento ?: "",
                            senhaAtrevia = sessionManager.getUserPassword()
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun buscarVeiculosDaGaragem(idUsuario: Int) {
        isLoading = true
        errorMessage = ""

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.buscarVeiculosPorUsuario(idUsuario)
                isLoading = false

                if (response.isSuccessful && response.body() != null) {
                    val corpoResposta = response.body()!!
                    val vinculos = corpoResposta.dados.listaVinculos ?: emptyList()

                    listaVeiculos = vinculos
                        .filter { it.id_usuario == idUsuario }
                        .map { it.veiculo }

                } else {
                    listaVeiculos = emptyList()
                }
            } catch (e: Exception) {
                isLoading = false
                listaVeiculos = emptyList()
                errorMessage = "Erro ao conectar com a garagem local."
                e.printStackTrace()
            }
        }
    }
}