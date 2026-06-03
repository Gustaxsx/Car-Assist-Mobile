package com.example.car_assist_mobile.screens.adicionarlembrete

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.LembreteRequest
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

// 💡 1. Estruturas para o Kotlin entender o que vem do banco de dados na busca de veículos
data class VeiculoDropdown(val id: Int, val modelo: String, val placa: String)

data class ApiResponseVeiculosLembrete(
    val status: Boolean,
    val status_code: Int,
    val message: String,
    val data: List<VeiculoDropdown>? // Dizemos ao Kotlin que 'data' é uma lista de veículos
)

// 💡 2. Classe auxiliar para exibir os dados no Dropdown da tela
data class VeiculoSimples(val id: Int, val modelo: String, val placa: String)

class AddLembreteViewModel : ViewModel() {

    var titulo by mutableStateOf("")
    var descricao by mutableStateOf("")
    var dataVencimento by mutableStateOf("") // Receberá DD/MM/AAAA
    var status by mutableStateOf("Pendente")

    // Variáveis para o Dropdown de Veículos
    var listaVeiculos by mutableStateOf<List<VeiculoSimples>>(emptyList())
    var veiculoSelecionado by mutableStateOf<VeiculoSimples?>(null)

    var isLoading by mutableStateOf(false)
    var mensagemSucesso by mutableStateOf("")
    var mensagemErro by mutableStateOf("")

    // Busca os carros do usuário assim que a tela abre
    fun carregarVeiculosDoUsuario(idUsuario: Int) {
        viewModelScope.launch {
            try {
                // 💡 Certifique-se de que no seu ApiService.kt a função retorna Response<ApiResponseVeiculos>
                val response = RetrofitClient.apiService.buscarVeiculosDoUsuario(idUsuario)

                if (response.isSuccessful && response.body() != null) {
                    // O Kotlin agora reconhece o .data e o emptyList() perfeitamente
                    val veiculosApi: List<VeiculoDropdown> = response.body()!!.data ?: emptyList()

                    listaVeiculos = veiculosApi.map {
                        VeiculoSimples(id = it.id, modelo = it.modelo, placa = it.placa)
                    }
                }
            } catch (e: Exception) {
                Log.e("LEMBRETE_DEBUG", "Erro ao carregar veículos: ${e.message}")
            }
        }
    }

    fun salvarLembrete(idUsuario: Int, onSuccess: () -> Unit) {
        if (titulo.isBlank() || dataVencimento.isBlank()) {
            mensagemErro = "Título e Data são obrigatórios."
            return
        }

        if (veiculoSelecionado == null) {
            mensagemErro = "Por favor, selecione um veículo."
            return
        }

        // LÓGICA DA DATA: Transforma DD/MM/AAAA em AAAA-MM-DD
        val partesData = dataVencimento.split("/")
        if (partesData.size != 3 || partesData[2].length != 4) {
            mensagemErro = "Data inválida! Use o formato DD/MM/AAAA."
            return
        }
        val dataFormatadaAPI = "${partesData[2]}-${partesData[1]}-${partesData[0]}"

        isLoading = true
        mensagemErro = ""
        mensagemSucesso = ""

        viewModelScope.launch {
            try {
                val request = LembreteRequest(
                    titulo = titulo,
                    descricao = descricao,
                    data_vencimento = dataFormatadaAPI, // Envia no padrão do banco
                    status = status,
                    fk_id_veiculo = veiculoSelecionado!!.id, // Pega o ID do carro escolhido
                    fk_id_usuario = idUsuario
                )

                val response = RetrofitClient.apiService.cadastrarLembrete(request)
                isLoading = false

                if (response.isSuccessful) {
                    mensagemSucesso = "Lembrete adicionado com sucesso!"
                    onSuccess()
                } else {
                    mensagemErro = "Erro ao salvar: O servidor recusou os dados."
                    Log.e("LEMBRETE_DEBUG", "Erro HTTP: ${response.code()} | Corpo: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                isLoading = false
                mensagemErro = "Falha na conexão com o servidor."
                Log.e("LEMBRETE_DEBUG", "Falha crítica: ${e.message}", e)
            }
        }
    }
}