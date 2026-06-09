package com.example.car_assist_mobile.data.model

data class AceitarTransferenciaRequest(
    val codigo_verificacao: String,
    val id_usuario_destino: Int
)