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

class AddLembreteViewModel : ViewModel() {

    var titulo by mutableStateOf("")
    var descricao by mutableStateOf("")
    var dataVencimento by mutableStateOf("")
    var status by mutableStateOf("Pendente")

    // Estados de controle da UI
    var isLoading by mutableStateOf(false)
    var mensagemSucesso by mutableStateOf("")
    var mensagemErro by mutableStateOf("")

    fun salvarLembrete(idVeiculo: Int, onSuccess: () -> Unit) {
        if (titulo.isBlank() || dataVencimento.isBlank()) {
            mensagemErro = "Título e Data de Vencimento são obrigatórios."
            return
        }

        isLoading = true
        mensagemErro = ""
        mensagemSucesso = ""

        viewModelScope.launch {
            try {
                val request = LembreteRequest(
                    titulo = titulo,
                    descricao = descricao,
                    data_vencimento = dataVencimento,
                    status = status,
                    fk_id_veiculo = idVeiculo
                )

                val response = RetrofitClient.apiService.cadastrarLembrete(request)
                isLoading = false

                if (response.isSuccessful && response.body()?.status == true) {
                    mensagemSucesso = "Lembrete adicionado com sucesso!"
                    onSuccess()
                } else {
                    mensagemErro = response.body()?.message ?: "Erro ao salvar lembrete no servidor."
                }
            } catch (e: Exception) {
                isLoading = false
                mensagemErro = "Falha na conexão com o servidor."
                Log.e("ADD_LEMBRETE_ERROR", "Erro: ${e.message}", e)
            }
        }
    }
}