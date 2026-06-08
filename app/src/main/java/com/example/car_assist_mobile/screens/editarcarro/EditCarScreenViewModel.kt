package com.example.car_assist_mobile.screens.editarcarro

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

class EditCarViewModel : ViewModel() {

    var modelo by mutableStateOf("Carregando...")
    var marca by mutableStateOf("Aguarde")
    var placa by mutableStateOf("")
    var ano by mutableStateOf("")
    var cor by mutableStateOf("")
    var fotoUrl by mutableStateOf<String?>(null)

    var isLoading by mutableStateOf(false)

    fun carregarDadosDoVeiculo(veiculoId: Int) {
        if (veiculoId == 0) return

        isLoading = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.buscarVeiculoPorId(veiculoId)

                if (response.isSuccessful && response.body() != null) {
                    val veiculo = response.body()!!.data?.veiculo?.firstOrNull()

                    veiculo?.let {
                        modelo = it.modelo
                        marca = it.marca ?: "Marca Desconhecida"
                        placa = it.placa
                        ano = it.ano?.toString() ?: ""
                        cor = it.cor ?: ""
                        fotoUrl = it.foto
                    }
                }
            } catch (e: Exception) {
                Log.e("EDIT_ERROR", "Erro ao carregar dados: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}