package com.example.car_assist_mobile.screens.gastos

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

class GastosViewModel : ViewModel() {

    var gastosAgrupados by mutableStateOf<Map<String, Double>>(emptyMap())
        private set

    var totalGasto by mutableStateOf(0.0)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun carregarGastosDoVeiculo(idVeiculo: Int) {
        if (idVeiculo == 0) return

        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.buscarGastosPorVeiculo(idVeiculo)

                if (response.isSuccessful && response.body()?.status == true) {
                    val listaGastos = response.body()?.data ?: emptyList()

                    val agrupamento = listaGastos.groupBy {
                        it.nome_categoria ?: "Outros"
                    }.mapValues { entrada ->
                        entrada.value.sumOf { it.valor }
                    }

                    gastosAgrupados = agrupamento
                    totalGasto = listaGastos.sumOf { it.valor }

                } else {
                    errorMessage = "Nenhum gasto encontrado para este veículo."
                    gastosAgrupados = emptyMap()
                    totalGasto = 0.0
                }
            } catch (e: Exception) {
                Log.e("GASTOS_ERROR", "Erro na API: ${e.message}")
                errorMessage = "Falha ao carregar gastos."
            } finally {
                isLoading = false
            }
        }
    }
}