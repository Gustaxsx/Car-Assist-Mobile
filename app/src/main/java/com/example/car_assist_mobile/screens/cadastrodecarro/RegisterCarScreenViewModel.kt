package com.example.car_assist_mobile.screens.cadastrodecarro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.CarRequest
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

class RegisterCarScreenViewModel : ViewModel() {

    var modelo by mutableStateOf("")
    var marca by mutableStateOf("")
    var placa by mutableStateOf("")
    var ano by mutableStateOf("")
    var cor by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun cadastrarVeiculo(idUsuario: Int, onSuccess: () -> Unit) {
        if (modelo.isBlank() || marca.isBlank() || placa.isBlank() || ano.isBlank() || cor.isBlank()) {
            errorMessage = "Preencha todos os campos obrigatórios."
            return
        }

        val anoInt = ano.toIntOrNull()
        if (anoInt == null || anoInt < 1900) {
            errorMessage = "Digite um ano válido."
            return
        }

        isLoading = true
        errorMessage = ""

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.cadastrarVeiculo(
                    CarRequest(
                        id_usuario = idUsuario,
                        placa = placa.trim().uppercase(),
                        modelo = modelo.trim(),
                        marca = marca.trim(),
                        cor = cor.trim().uppercase(),
                        ano = anoInt,
                        foto_veiculo = "https://exemplo.com/imagens/corolla-prata.jpg",
                        is_ativo = true
                    )
                )
                isLoading = false

                if (response.isSuccessful && response.body()?.status == true) {
                    onSuccess()
                } else {
                    errorMessage = response.body()?.message ?: "Erro ao cadastrar o veículo."
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Erro de conexão. Verifique se o servidor local está online."
                e.printStackTrace()
            }
        }
    }
}