package com.example.car_assist_mobile.screens.transferencia

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.TokenTransferenciaRequest
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

data class TransferenciaUiState(
    val emailDestinatario: String = "",
    val senhaConfirmacao: String = "",
    val nivelPermissao: String = "Proprietário",
    val nomeVeiculo: String = "Carregando...",
    val placaVeiculo: String = "",
    val idVeiculo: Int = 0,
    val codigoGerado: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface TransferenciaUiEvent {
    object Sucesso : TransferenciaUiEvent
    data class Erro(val message: String) : TransferenciaUiEvent
}

class TransferenciaScreenViewModel : ViewModel() {

    var uiState by mutableStateOf(TransferenciaUiState())

    private val _eventFlow = MutableSharedFlow<TransferenciaUiEvent>()
    val eventFlow: SharedFlow<TransferenciaUiEvent> = _eventFlow.asSharedFlow()

    fun onEmailChanged(novoEmail: String) {
        uiState = uiState.copy(emailDestinatario = novoEmail)
    }

    fun onSenhaChanged(novaSenha: String) {
        uiState = uiState.copy(senhaConfirmacao = novaSenha)
    }

    fun onPermissaoChanged(novaPermissao: String) {
        uiState = uiState.copy(nivelPermissao = novaPermissao)
    }

    fun carregarDadosVeiculo(id: Int, nome: String, placa: String) {
        uiState = uiState.copy(
            idVeiculo = id,
            nomeVeiculo = nome,
            placaVeiculo = placa
        )
    }

    fun gerarCodigoTransferencia(idUsuarioLogado: Int) {
        if (uiState.emailDestinatario.isBlank() || uiState.senhaConfirmacao.isBlank()) {
            viewModelScope.launch {
                _eventFlow.emit(TransferenciaUiEvent.Erro("Preencha seu e-mail e senha para confirmar."))
            }
            return
        }

        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val papelMapeado = when (uiState.nivelPermissao) {
                    "Leitura" -> "Visualizador"
                    "Editável" -> "Editor"
                    else -> "Proprietário"
                }

                val request = TokenTransferenciaRequest(
                    fk_id_veiculo = uiState.idVeiculo,
                    fk_id_usuario_origem = idUsuarioLogado,
                    papel_concedido = papelMapeado
                )

                val response = RetrofitClient.apiService.gerarTokenTransferencia(request)

                if (response.isSuccessful && response.body() != null) {
                    val jsonResponse = response.body()!!

                    val tokenDoBackend = try {
                        when {
                            jsonResponse.has("codigo_verificacao") -> jsonResponse.get("codigo_verificacao").asString
                            jsonResponse.has("data") && jsonResponse.getAsJsonObject("data").has("codigo_verificacao") ->
                                jsonResponse.getAsJsonObject("data").get("codigo_verificacao").asString
                            jsonResponse.has("codigo") -> jsonResponse.get("codigo").asString
                            else -> "CODIGO_GERADO_MAS_NAO_ENCONTRADO_NO_JSON"
                        }
                    } catch (e: Exception) {
                        "ERRO_LEITURA"
                    }

                    uiState = uiState.copy(
                        codigoGerado = tokenDoBackend,
                        isLoading = false
                    )
                    _eventFlow.emit(TransferenciaUiEvent.Sucesso)

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("TRANSFER_ERROR", "Erro ${response.code()}: $errorBody")
                    uiState = uiState.copy(isLoading = false)
                    _eventFlow.emit(TransferenciaUiEvent.Erro("Erro no servidor: Dados recusados."))
                }

            } catch (e: Exception) {
                Log.e("TRANSFER_ERROR", "Exceção: ${e.message}")
                uiState = uiState.copy(isLoading = false)
                _eventFlow.emit(TransferenciaUiEvent.Erro("Falha de conexão com a API."))
            }
        }
    }

    fun resetState() {
        uiState = TransferenciaUiState()
    }
}