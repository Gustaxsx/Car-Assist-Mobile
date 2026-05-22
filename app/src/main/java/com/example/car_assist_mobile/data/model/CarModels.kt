package com.example.car_assist_mobile.data.model

import com.google.gson.annotations.SerializedName

data class CarRequest(
    val id_usuario: Int,
    val placa: String,
    val modelo: String,
    val marca: String,
    val cor: String,
    val ano: Int,
    val foto_veiculo: String?,
    val is_ativo: Boolean
)

data class VeiculoResponse(
    val id: Int,
    val placa: String,
    val modelo: String,
    val cor: String,
    val score: String?,
    val ano: Int,
    @SerializedName("foto")
    val foto_veiculo: String?,
    val marca: String?
)

data class VinculoResponse(
    val id_usuario: Int,
    val papel_usuario: String?,
    val is_ativo: Int,
    val veiculo: VeiculoResponse
)

data class DataWrapper(
    @SerializedName("usuario_veiculo")
    val listaVinculos: List<VinculoResponse>?
)


data class CarListResponse(
    val status: Boolean,
    val message: String?,
    @SerializedName("data")
    val dados: DataWrapper
)