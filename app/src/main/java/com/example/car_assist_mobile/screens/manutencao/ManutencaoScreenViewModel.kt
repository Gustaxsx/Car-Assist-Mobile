package com.example.car_assist_mobile.screens.manutencao

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.ManutencaoItemResponse
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class ManutencaoScreenViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var listaManutencoes by mutableStateOf<List<ManutencaoItemResponse>>(emptyList())
        private set

    var papelUsuario by mutableStateOf("")
        private set

    fun carregarDados(veiculoId: Int, idUsuarioLogado: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                // 1. Busca o papel do usuário logado neste veículo
                val responsePapel = RetrofitClient.apiService.buscarVeiculosPorUsuario(idUsuarioLogado)
                if (responsePapel.isSuccessful) {
                    val relacao = responsePapel.body()?.data?.usuario_veiculo?.find { it.veiculo.id == veiculoId }
                    papelUsuario = relacao?.papel_usuario ?: ""
                }

                // 2. Busca o histórico de manutenções
                val response = RetrofitClient.apiService.buscarManutencoesPorVeiculo(veiculoId)
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        listaManutencoes = response.body()?.data?.manutencao ?: emptyList()
                    } else {
                        listaManutencoes = emptyList()
                    }
                } else if (response.code() == 404) {
                    listaManutencoes = emptyList()
                } else {
                    errorMessage = "Erro ao carregar o histórico de manutenções."
                }
            } catch (e: Exception) {
                errorMessage = "Falha na conexão: Verifique sua internet."
            } finally {
                isLoading = false
            }
        }
    }

    fun formatarDataBR(dataIso: String): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
            val date = parser.parse(dataIso)
            date?.let { formatter.format(it) } ?: dataIso
        } catch (e: Exception) {
            dataIso
        }
    }

    fun formatarMoedaBR(valorStr: String): String {
        return try {
            val valorDouble = valorStr.toDouble()
            val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            formatador.format(valorDouble)
        } catch (e: Exception) {
            "R$ $valorStr"
        }
    }
}