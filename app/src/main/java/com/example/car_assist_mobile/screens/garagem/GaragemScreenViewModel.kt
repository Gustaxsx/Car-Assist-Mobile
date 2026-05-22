package com.example.car_assist_mobile.screens.garagem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.VeiculoResponse
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

class GaragemScreenViewModel : ViewModel() {

    var listaVeiculos by mutableStateOf<List<VeiculoResponse>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

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