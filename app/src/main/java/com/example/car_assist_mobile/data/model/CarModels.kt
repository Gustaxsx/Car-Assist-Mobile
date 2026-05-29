package com.example.car_assist_mobile.data.model

data class ApiResponseVeiculos(
    val status: Boolean,
    val status_code: Int,
    val message: String,
    val data: DataUsuarioVeiculo?
)

data class DataUsuarioVeiculo(
    val usuario_veiculo: List<UsuarioVeiculo>?
)

data class UsuarioVeiculo(
    val id_usuario: Int,
    val papel_usuario: String?,
    val data_vinculo: String?,
    val is_ativo: Int?,
    val veiculo: VeiculoResponse
)

data class VeiculoResponse(
    val id: Int,
    val placa: String,
    val marca: String?,
    val modelo: String,
    val cor: String?,
    val score: String?,
    val ano: Int?,
    val foto: String?
)

data class ApiResponseSingleVeiculo(
    val status: Boolean,
    val status_code: Int,
    val message: String,
    val data: DataSingleVeiculo?
)

data class DataSingleVeiculo(
    val veiculo: List<VeiculoResponse>?
)