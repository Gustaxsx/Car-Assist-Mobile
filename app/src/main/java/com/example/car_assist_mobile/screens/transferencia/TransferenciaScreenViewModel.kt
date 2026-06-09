package com.example.car_assist_mobile.screens.transferencia

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

data class TransferenciaUiState(
    val emailDestinatario: String = "",
    val senhaConfirmacao: String = "",
    val nivelPermissao: String = "Transferir propriedade definitiva",
    val nomeVeiculo: String = "Civic SI",
    val placaVeiculo: String = "EXE3006",
    val codigoGerado: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val nomeNovoDono: String = "Destinatário de Teste",
    val cpfNovoDono: String = "000.000.000-00",
    val emailNovoDono: String = ""
)

sealed interface TransferenciaUiEvent {
    object Sucesso : TransferenciaUiEvent
    data class Erro(val message: String) : TransferenciaUiEvent
}

class TransferenciaViewModel : ViewModel() {

    var uiState by mutableStateOf(TransferenciaUiState())
        private set

    private val _eventFlow = MutableSharedFlow<TransferenciaUiEvent>()
    val eventFlow: SharedFlow<TransferenciaUiEvent> = _eventFlow.asSharedFlow()

    fun onEmailChanged(novoEmail: String) {
        uiState = uiState.copy(
            emailDestinatario = novoEmail,
            emailNovoDono = novoEmail
        )
    }

    fun onSenhaChanged(novaSenha: String) {
        uiState = uiState.copy(senhaConfirmacao = novaSenha)
    }

    fun onPermissaoChanged(novaPermissao: String) {
        uiState = uiState.copy(nivelPermissao = novaPermissao)
    }

    fun carregarDadosVeiculo(idVeiculo: String) {
        uiState = uiState.copy(
            nomeVeiculo = "Civic SI",
            placaVeiculo = "EXE3006"
        )
    }

    fun gerarCodigoTransferencia() {
        if (uiState.emailDestinatario.isBlank() || uiState.senhaConfirmacao.isBlank()) {
            viewModelScope.launch {
                _eventFlow.emit(TransferenciaUiEvent.Erro("Preencha todos os campos obrigatórios."))
            }
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                val codigoFake = (10000000..99999999).random().toString()

                uiState = uiState.copy(
                    codigoGerado = codigoFake,
                    isLoading = false
                )
                _eventFlow.emit(TransferenciaUiEvent.Sucesso)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Erro ao gerar código"
                )
                _eventFlow.emit(TransferenciaUiEvent.Erro(uiState.errorMessage!!))
            }
        }
    }

    fun resetState() {
        uiState = TransferenciaUiState()
    }
}