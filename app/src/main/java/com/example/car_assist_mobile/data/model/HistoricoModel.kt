package com.example.car_assist_mobile.data.model

data class ApiResponseHistoricoDonos(
    val status: Boolean,
    val status_code: Int,
    val message: String,
    val data: HistoricoDataWrapper?
)

data class HistoricoDataWrapper(
    val usuario_veiculo: List<HistoricoDono>?
)

data class HistoricoDono(
    val id_usuario: Int,
    val fk_id_usuario: Int?,
    val papel_usuario: String?,
    val data_vinculo: String?,
    val data_desvinculo: String?,
    val is_ativo: Int?
)

data class DonoDetalhado(
    val id_usuario: Int,
    val nome: String,
    val foto_usuario: String?,
    val papel_usuario: String?,
    val data_vinculo: String?,
    val data_desvinculo: String?,
    val is_ativo: Int?
)