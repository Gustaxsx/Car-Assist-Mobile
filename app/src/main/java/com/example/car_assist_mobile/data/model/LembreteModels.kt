package com.example.car_assist_mobile.data.model

data class LembreteRequest(
    val titulo: String,
    val descricao: String,
    val data_vencimento: String,
    val status: String,
    val fk_id_veiculo: Int,
    val fk_id_usuario: Int
)

data class LembreteResponse(
    val id: Int,
    val titulo: String,
    val descricao: String?,
    val data_vencimento: String?,
    val status: String?,
    val fk_id_veiculo: Int,
    val fk_id_usuario: Int
)

data class ApiResponseLembretes(
    val status: Boolean,
    val status_code: Int,
    val message: String,
    val data: List<LembreteResponse>?
)

data class ApiResponseSingleLembrete(
    val status: Boolean,
    val status_code: Int,
    val message: String,
    val data: List<LembreteResponse>?
)

// O formato que o seu backend Node.js devolve na rota de buscar os carros do usuário
data class ApiResponseVeiculosLembrete(
    val status: Boolean,
    val status_code: Int,
    val message: String,
    val data: List<VeiculoDropdown>? // 💡 Aqui nós dizemos que o data é uma lista de veículos!
)

// As colunas que vêm do seu banco de dados
data class VeiculoDropdown(
    val id: Int,
    val modelo: String,
    val placa: String
)