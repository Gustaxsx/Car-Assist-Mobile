package com.example.car_assist_mobile.screens.adquirircarro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.AceitarTransferenciaRequest
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Classes para controlar o estado da tela
sealed class AcquireCarState {
    object Idle : AcquireCarState()
    object Loading : AcquireCarState()
    object Success : AcquireCarState()
    data class Error(val message: String) : AcquireCarState()
}

class AcquireCarScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<AcquireCarState>(AcquireCarState.Idle)
    val uiState: StateFlow<AcquireCarState> = _uiState.asStateFlow()

    fun aceitarTransferencia(codigo: String, idUsuarioDestino: Int) {
        // Validação simples
        if (codigo.isBlank()) {
            _uiState.value = AcquireCarState.Error("Digite o código de verificação")
            return
        }

        _uiState.value = AcquireCarState.Loading

        viewModelScope.launch {
            try {
                val request = AceitarTransferenciaRequest(
                    codigo_verificacao = codigo,
                    id_usuario_destino = idUsuarioDestino
                )

                // Aqui você chama sua API (Ajuste o RetrofitClient para a sua estrutura)
                val response = RetrofitClient.apiService.aceitarTransferencia(request)

                if (response.isSuccessful) {
                    _uiState.value = AcquireCarState.Success
                } else {
                    _uiState.value = AcquireCarState.Error("Código inválido ou erro ao transferir")
                }
            } catch (e: Exception) {
                _uiState.value = AcquireCarState.Error("Falha na conexão: ${e.message}")
            }
        }
    }

    // Volta o estado para o normal após mostrar um erro
    fun resetState() {
        _uiState.value = AcquireCarState.Idle
    }
}