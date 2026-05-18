package com.example.car_assist_mobile.screens.cadastro

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

        if (nome.isBlank() || email.isBlank() || cpf.isBlank() || senha.isBlank()) {
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

        isLoading = true
        errorMessage = ""

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.cadastrarUsuario(
                    RegisterRequest(
                        nome = nome,
                        email = email,
                        cpf = cpfLimpo,
                        data_nascimento = dataNasc,
                        senha = senha
                    )
                )
                isLoading = false

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage = response.errorBody()?.string() ?: "Erro ao realizar cadastro."
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Erro de conexão. Verifique se o servidor local está online."
            }
        }
    }
}