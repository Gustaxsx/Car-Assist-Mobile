package com.example.car_assist_mobile.screens.historico

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.HistoricoDono
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

class HistoricoDonoViewModel : ViewModel() {

    var listaHistorico by mutableStateOf<List<HistoricoDono>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun buscarHistoricoDoVeiculo(veiculoId: Int) {
        if (veiculoId == 0) return

        isLoading = true
        errorMessage = ""

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.buscarHistoricoDonos(veiculoId)

                isLoading = false

                if (response.isSuccessful && response.body() != null) {
                    // 💡 CORREÇÃO: Acessando os dados através da camada "usuario_veiculo"
                    val listaDeDonos = response.body()!!.data?.usuario_veiculo

                    if (!listaDeDonos.isNullOrEmpty()) {
                        // Ordena para que o dono ATUAL (is_ativo = 1) fique no topo da lista
                        listaHistorico = listaDeDonos.sortedByDescending { it.is_ativo }
                    } else {
                        errorMessage = "Nenhum histórico encontrado."
                    }
                } else {
                    errorMessage = "Nenhum histórico encontrado."
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Erro de conexão ao buscar histórico."
                Log.e("HISTORY_ERROR", "O erro real foi: ", e)
            }
        }
    }
}