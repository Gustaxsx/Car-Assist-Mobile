package com.example.car_assist_mobile.data.model

import com.google.gson.annotations.SerializedName

data class AceitarTransferenciaRequest(
    val codigo_verificacao: String,
    val id_usuario_destino: Int
)

data class TokenTransferenciaRequest(
    @SerializedName("fk_id_veiculo") val fk_id_veiculo: Int,
    @SerializedName("fk_id_usuario_origem") val fk_id_usuario_origem: Int,
    @SerializedName("papel_concedido") val papel_concedido: String
)
data class TransferenciaResponseWrapper(
    val status: Boolean,
    val data: TokenTransferenciaData
)

data class TokenTransferenciaData(
    val codigo_verificacao: String
)
