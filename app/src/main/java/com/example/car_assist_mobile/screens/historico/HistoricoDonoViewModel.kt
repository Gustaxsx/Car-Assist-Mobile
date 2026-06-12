package com.example.car_assist_mobile.screens.historico

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.DonoDetalhado
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class HistoricoDonoViewModel : ViewModel() {

    var listaHistorico by mutableStateOf<List<DonoDetalhado>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun buscarHistoricoDoVeiculo(veiculoId: Int) {
        if (veiculoId == 0) return

        isLoading = true
        errorMessage = ""

        viewModelScope.launch {
            try {
                val responseVinculos = RetrofitClient.apiService.buscarHistoricoDonos(veiculoId)

                if (responseVinculos.isSuccessful && responseVinculos.body() != null) {
                    val listaDeVinculos = responseVinculos.body()!!.data?.usuario_veiculo

                    if (!listaDeVinculos.isNullOrEmpty()) {

                        val chamadasSimultaneas = listaDeVinculos.map { vinculo ->
                            async {
                                val idDoDono = vinculo.id_usuario ?: vinculo.fk_id_usuario ?: 0

                                var nomeEncontrado = "Usuário Desconhecido"
                                var fotoEncontrada: String? = null

                                if (idDoDono != 0) {
                                    try {
                                        val userResponse = RetrofitClient.apiService.buscarUsuarioPorId(idDoDono)
                                        if (userResponse.isSuccessful && userResponse.body() != null) {
                                            val usuarioApi = userResponse.body()!!.data.usuario?.firstOrNull()
                                            if (usuarioApi != null) {
                                                nomeEncontrado = usuarioApi.nome ?: "Usuário sem nome"
                                                fotoEncontrada = usuarioApi.foto_usuario
                                            }
                                        }
                                    } catch (e: Exception) {
                                        Log.e("HISTORY_USER_ERROR", "Falha ao buscar o usuário $idDoDono")
                                    }
                                }

                                DonoDetalhado(
                                    id_usuario = idDoDono,
                                    nome = nomeEncontrado,
                                    foto_usuario = fotoEncontrada,
                                    papel_usuario = vinculo.papel_usuario,
                                    data_vinculo = vinculo.data_vinculo,
                                    data_desvinculo = vinculo.data_desvinculo,
                                    is_ativo = vinculo.is_ativo
                                )
                            }
                        }

                        val historicoCompleto = chamadasSimultaneas.awaitAll()
                        listaHistorico = historicoCompleto.sortedByDescending { it.is_ativo }

                    } else {
                        errorMessage = "Nenhum histórico encontrado para este veículo."
                    }
                } else {
                    errorMessage = "Falha ao carregar o histórico."
                }
            } catch (e: Exception) {
                errorMessage = "Erro de conexão ao montar o histórico."
                Log.e("HISTORY_ERROR", "O erro real foi: ", e)
            } finally {
                isLoading = false
            }
        }
    }

    // 💡 NOVA FUNÇÃO: Remove o acesso e recarrega a tela
    fun removerAcesso(idUsuario: Int, veiculoId: Int, onSuccess: () -> Unit) {
        isLoading = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.removerAcessoVeiculo(idUsuario, veiculoId)
                if (response.isSuccessful) {
                    buscarHistoricoDoVeiculo(veiculoId) // Atualiza a lista
                    onSuccess()
                } else {
                    errorMessage = "Erro ao revogar acesso."
                    isLoading = false
                }
            } catch (e: Exception) {
                errorMessage = "Falha na conexão ao revogar acesso."
                isLoading = false
                Log.e("HISTORY_ERROR", "Erro ao remover: ", e)
            }
        }
    }
}