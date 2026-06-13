package com.example.car_assist_mobile.screens.gastos

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.GastoRequest
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddGastoViewModel : ViewModel() {

    var valor by mutableStateOf("")
    var dataGasto by mutableStateOf("") // O usuário vai digitar DD/MM/YYYY
    var idCategoriaSelecionada by mutableStateOf(1) // Categoria padrão

    var isLoading by mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow: SharedFlow<String> = _eventFlow.asSharedFlow()

    // 💡 LISTA DE CATEGORIAS (Ajuste os IDs conforme a sua tabela tbl_categoria_gasto no MySQL)
    val categoriasDisponiveis = mapOf(
        1 to "Combustível",
        2 to "Limpeza",
        3 to "Pedágio",
        4 to "Estacionamento",
        5 to "Manutenção",
        6 to "Multas"
    )

    fun cadastrarNovoGasto(idVeiculo: Int) {
        if (valor.isBlank() || dataGasto.isBlank()) {
            viewModelScope.launch { _eventFlow.emit("Preencha todos os campos.") }
            return
        }

        isLoading = true

        viewModelScope.launch {
            try {
                // Tenta converter o valor para Double (ex: "230.50")
                val valorFormatado = valor.replace(",", ".").toDoubleOrNull()

                if (valorFormatado == null) {
                    _eventFlow.emit("Digite um valor numérico válido.")
                    isLoading = false
                    return@launch
                }

                // Tenta converter a data de DD/MM/YYYY para YYYY-MM-DD (Padrão MySQL)
                val partesData = dataGasto.split("/")
                val dataMysql = if (partesData.size == 3) {
                    "${partesData[2]}-${partesData[1]}-${partesData[0]}"
                } else {
                    dataGasto // Envia como está se não tiver as barras, e deixa o banco rejeitar se tiver errado
                }

                val request = GastoRequest(
                    data_gasto = dataMysql,
                    valor = valorFormatado,
                    fk_id_veiculo = idVeiculo,
                    fk_id_categoria = idCategoriaSelecionada
                )

                val response = RetrofitClient.apiService.cadastrarGasto(request)

                if (response.isSuccessful) {
                    _eventFlow.emit("Sucesso")
                } else {
                    _eventFlow.emit("Erro ao cadastrar gasto.")
                }
            } catch (e: Exception) {
                Log.e("ADD_GASTO_ERROR", "Erro: ${e.message}")
                _eventFlow.emit("Falha de conexão com o servidor.")
            } finally {
                isLoading = false
            }
        }
    }
}