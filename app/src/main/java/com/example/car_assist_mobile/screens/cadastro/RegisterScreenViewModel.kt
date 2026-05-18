package com.example.car_assist_mobile.screens.cadastro

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.RegisterRequest
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

class RegisterScreenViewModel : ViewModel() {

    var nome by mutableStateOf("")
    var cpf by mutableStateOf("")
    var dataNasc by mutableStateOf("")
    var email by mutableStateOf("")
    var senha by mutableStateOf("")
    var confirmarSenha by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun realizarCadastro(onSuccess: () -> Unit) {
        val cpfLimpo = cpf.replace(".", "").replace("-", "").trim()

        if (nome.isBlank() || email.isBlank() || cpf.isBlank() || senha.isBlank() || dataNasc.isBlank()) {
            errorMessage = "Preencha todos os campos obrigatórios."
            return
        }
        if (!email.contains("@")) {
            errorMessage = "Digite um e-mail válido."
            return
        }
        if (cpfLimpo.length != 11 || cpfLimpo.any { !it.isDigit() }) {
            errorMessage = "O CPF deve conter exatamente 11 dígitos numéricos."
            return
        }
        if (senha.length < 6) {
            errorMessage = "A senha deve ter pelo menos 6 caracteres."
            return
        }
        if (senha != confirmarSenha) {
            errorMessage = "As senhas não coincidem."
            return
        }

        val dataFormatadaParaOBanco = try {
            val partes: List<String> = dataNasc.split("/")

            if (partes.size == 3) {
                val dia = partes.get(0).trim().padStart(2, '0')
                val mes = partes.get(1).trim().padStart(2, '0')
                val ano = partes.get(2).trim()

                "$ano-$mes-$dia"
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }

        Log.d("CAR_ASSIST_DEBUG", "Data digitada na tela: $dataNasc")
        Log.d("CAR_ASSIST_DEBUG", "Data convertida para o Banco: $dataFormatadaParaOBanco")

        if (dataFormatadaParaOBanco == null) {
            errorMessage = "Formato de data de nascimento inválido."
            return
        }

        isLoading = true
        errorMessage = ""

        viewModelScope.launch {
            try {
                val requisicao = RegisterRequest(
                    nome = nome,
                    email = email,
                    cpf = cpfLimpo,
                    data_nascimento = dataFormatadaParaOBanco,
                    senha = senha
                )

                Log.i("CAR_ASSIST_DEBUG", "=== ENVIANDO PARA API ===")
                Log.i("CAR_ASSIST_DEBUG", "Nome: ${requisicao.nome}")
                Log.i("CAR_ASSIST_DEBUG", "Email: ${requisicao.email}")
                Log.i("CAR_ASSIST_DEBUG", "CPF: ${requisicao.cpf}")
                Log.i("CAR_ASSIST_DEBUG", "Data Nascimento: ${requisicao.data_nascimento}")
                Log.i("CAR_ASSIST_DEBUG", "=========================")

                val response = RetrofitClient.apiService.cadastrarUsuario(requisicao)
                isLoading = false

                if (response.isSuccessful) {
                    Log.d("CAR_ASSIST_DEBUG", "Cadastro realizado com Sucesso no Servidor!")
                    onSuccess()
                } else {
                    val erroServidor = response.errorBody()?.string()
                    Log.e("CAR_ASSIST_DEBUG", "Erro do Servidor: $erroServidor")
                    errorMessage = erroServidor ?: "Erro ao realizar cadastro."
                }
            } catch (e: Exception) {
                isLoading = false
                Log.e("CAR_ASSIST_DEBUG", "Erro de Conexão na requisição", e)
                errorMessage = "Erro de conexão. Verifique se o servidor local está online."
            }
        }
    }
}