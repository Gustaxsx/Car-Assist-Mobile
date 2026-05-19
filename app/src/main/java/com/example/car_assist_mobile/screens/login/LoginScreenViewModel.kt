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

    fun realizarLogin(onSuccess: () -> Unit) {
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
                        email = email,
                        password = senha
                    )
                )
                isLoading = false

                if (response.isSuccessful && response.body() != null) {
                    onSuccess()
                } else {
                    errorMessage = "E-mail ou senha incorretos."
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Erro ao conectar ao servidor. Verifique sua API."
            }
        }
    }
}