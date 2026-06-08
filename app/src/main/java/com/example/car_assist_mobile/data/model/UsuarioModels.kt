package com.example.car_assist_mobile.data.model

data class Usuario(
    val id: Int,
    val nome: String?,
    val email: String?,
    val cpf: String?,
    val data_nascimento: String?,
    val senha: String?,
    val foto_usuario: String?,
    val is_ativo: Any?
)

data class ProfileGetResponse(
    val status: Boolean,
    val status_code: Int,
    val data: ProfileGetData
)

data class ProfileGetData(
    val usuario: List<Usuario>?
)

data class ProfileDataResponse(
    val status: Boolean,
    val message: String?
)

data class UpdateUserRequest(
    val nome: String,
    val email: String,
    val data_nascimento: String
)