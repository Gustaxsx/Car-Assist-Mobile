package com.example.car_assist_mobile.data.model

data class TipoManutencaoResponse(
    val status: Boolean,
    val status_code: Int,
    val message: String,
    val data: TipoManutencaoData
)

data class TipoManutencaoData(
    val tipos_manutencao: List<TipoManutencaoItem>
)

data class TipoManutencaoItem(
    val id: Int,
    val nome: String,
    val valor_score: Int,
    val descricao: String
)

data class ManutencaoRequest(
    val data_manutencao: String,
    val custo: Double,
    val quilometragem: Int,
    val oficina: String,
    val observacoes: String,
    val fk_id_tipo_manutencao: Int,
    val fk_id_usuario: Int,
    val fk_id_veiculo: Int,
    val pecas: String
)

data class ManutencaoResponse(
    val message: String? = null
)

data class ManutencaoListResponse(
    val status: Boolean,
    val status_code: Int,
    val message: String,
    val data: ManutencaoDataWrap?
)

data class ManutencaoDataWrap(
    val manutencao: List<ManutencaoItemResponse>
)

data class ManutencaoItemResponse(
    val id: Int,
    val id_usuario: Int,
    val id_veiculo: Int,
    val data_manutencao: String,
    val custo: String,
    val quilometragem: Int,
    val oficina: String,
    val pecas: String,
    val observacoes: String,
    val tipo_manutencao: TipoManutencaoDetalhe?
)

data class TipoManutencaoDetalhe(
    val id: Int,
    val nome: String
)