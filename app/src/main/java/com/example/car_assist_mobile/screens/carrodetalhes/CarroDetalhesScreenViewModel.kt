package com.example.car_assist_mobile.screens.carrodetalhes

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

class DetailsCarViewModel : ViewModel() {

    var modelo by mutableStateOf("Carregando...")
    var marca by mutableStateOf("Aguarde")
    var placa by mutableStateOf("")
    var fotoUrl by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)

    // Armazena o papel correto mapeado da lista ("Proprietário", "Editor", "Visualizador")
    var papelUsuario by mutableStateOf("")

    fun carregarDadosDoVeiculo(veiculoId: Int, idUsuarioLogado: Int) {
        if (veiculoId == 0 || idUsuarioLogado == 0) return

        isLoading = true
        viewModelScope.launch {
            try {
                // Faz a chamada na rota que retorna o array "usuario_veiculo" mapeado no seu DataUsuarioVeiculo
                val response = RetrofitClient.apiService.buscarVeiculosPorUsuario(idUsuarioLogado)

                if (response.isSuccessful && response.body() != null) {
                    val listaRelacoes = response.body()!!.data?.usuario_veiculo ?: emptyList()

                    // Filtra o item correto da lista comparando com o veiculoId que a tela recebeu
                    val relacaoDoVeiculo = listaRelacoes.find { it.veiculo.id == veiculoId }

                    if (relacaoDoVeiculo != null) {
                        papelUsuario = relacaoDoVeiculo.papel_usuario ?: ""

                        val car = relacaoDoVeiculo.veiculo
                        modelo = car.modelo
                        marca = car.marca ?: "Marca Desconhecida"
                        placa = car.placa ?: ""
                        fotoUrl = car.foto ?: car.foto_veiculo
                    } else {
                        modelo = "Veículo não encontrado"
                        marca = ""
                    }
                } else {
                    modelo = "Erro na API"
                    marca = ""
                }
            } catch (e: Exception) {
                Log.e("DETAILS_ERROR", "Erro ao carregar os dados do veículo: ${e.message}")
                modelo = "Erro ao carregar"
                marca = ""
            } finally {
                isLoading = false
            }
        }
    }
}