package com.example.car_assist_mobile.data.model

data class LoginRequest(
    val email: String,
    val password: String
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
    val status: Boolean?,
    val message: String?,
    val token: String? = null,
    val id_usuario: Int? = null
)


data class UsuarioLoginDetalhes(
    val id: Int,
    val nome: String?
)

data class LoginDataWrapper(
    val usuario: UsuarioLoginDetalhes
)


data class LoginResponse(
    val status: Boolean,
    val message: String?,
    val data: LoginDataWrapper?
)