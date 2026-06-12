package com.example.car_assist_mobile.data.model

data class AceitarTransferenciaRequest(
    val codigo_verificacao: String,
    val id_usuario_destino: Int
)

data class TokenTransferenciaRequest(
    val fk_id_veiculo: Int,
    val fk_id_usuario_origem: Int,
    val papel_concedido: String
)

data class TransferenciaResponseWrapper(
    val status: Boolean,
    val data: TokenTransferenciaData
)

data class TokenTransferenciaData(
    val codigo_verificacao: String
)
