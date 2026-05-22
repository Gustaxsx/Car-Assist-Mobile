package com.example.car_assist_mobile.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_assist_mobile.data.model.LoginRequest
import com.example.car_assist_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {

    var email by mutableStateOf("")
    var senha by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun realizarLogin(onSuccess: (Int) -> Unit) {
        if (email.isBlank() || senha.isBlank()) {
            errorMessage = "Por favor, preencha todos os campos."
            return
        }

        isLoading = true
        errorMessage = ""

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.login(
                    LoginRequest(
                        email = email.trim(),
                        password = senha.trim()
                    )
                )
                isLoading = false

                if (response.isSuccessful && response.body()?.status == true) {

                    val idLogado = response.body()?.data?.usuario?.id

                    if (idLogado != null && idLogado > 0) {
                        onSuccess(idLogado)
                    } else {
                        errorMessage = "Login aprovado, mas não foi possível identificar o utilizador."
                    }
                } else {
                    errorMessage = response.body()?.message ?: "E-mail ou senha incorretos."
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Erro ao conectar ao servidor. Verifique sua API."
                e.printStackTrace()
            }
        }
    }
}