package com.example.car_assist_mobile.data.model

data class LembreteResponse(
    val id: Int,
    val titulo: String,
    val descricao: String?,
    val data_vencimento: String?,
    val status: String?,
    val fk_id_veiculo: Int
)

data class ApiResponseLembretes(
    val status: Boolean,
    val status_code: Int,
    val message: String,
    val data: List<LembreteResponse>?
)