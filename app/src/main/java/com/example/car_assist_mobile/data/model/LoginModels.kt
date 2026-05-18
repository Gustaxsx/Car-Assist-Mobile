package com.example.car_assist_mobile.data.model

data class LoginRequest(
    val email: String,
    val senha: String
)

data class RegisterRequest(
    val nome: String,
    val email: String,
    val cpf: String,
    val data_nascimento: String?,
    val senha: String,
    val foto_usuario: String? = null
)

data class ApiResponse(
    val status: Int?,
    val message: String?,
    val token: String? = null
)