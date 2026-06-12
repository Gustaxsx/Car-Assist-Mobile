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
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                // Mapeamento para o formato do banco
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

                if (response.isSuccessful && response.body()?.status == true) {
                    // 💡 PEGA O CÓDIGO DA RESPOSTA REAL DO BACKEND
                    val tokenDoBackend = response.body()?.data?.codigo_verificacao ?: "ERRO"

                    uiState = uiState.copy(
                        codigoGerado = tokenDoBackend,
                        isLoading = false
                    )
                    _eventFlow.emit(TransferenciaUiEvent.Sucesso)
                } else {
                    _eventFlow.emit(TransferenciaUiEvent.Erro("Erro ao processar: Código ${response.code()}"))
                }
            } catch (e: Exception) {
                _eventFlow.emit(TransferenciaUiEvent.Erro("Falha na conexão: ${e.message}"))
            }
        }
    }

    fun resetState() {
        uiState = TransferenciaUiState()
    }
}