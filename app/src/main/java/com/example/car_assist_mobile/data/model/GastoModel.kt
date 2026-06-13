package com.example.car_assist_mobile.data.model

data class GastosResponseWrapper(
    val status: Boolean,
    val status_code: Int,
    val message: String?,
    val data: List<GastoModel>?
)

data class GastoModel(
    val id: Int,
    val valor: Double,
    val data_gasto: String,
    val fk_id_categoria: Int,
    val nome_categoria: String?
)

data class GastoRequest(
    val data_gasto: String,
    val valor: Double,
    val fk_id_veiculo: Int,
    val fk_id_categoria: Int
)