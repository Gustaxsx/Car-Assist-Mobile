package com.example.car_assist_mobile.data.network

import com.example.car_assist_mobile.data.model.AceitarTransferenciaRequest
import com.example.car_assist_mobile.data.model.ApiResponseVeiculos
import com.example.car_assist_mobile.data.model.ApiResponse
import com.example.car_assist_mobile.data.model.ApiResponseHistoricoDonos
//import com.example.car_assist_mobile.data.model.ApiResponseLembrete
//import com.example.car_assist_mobile.data.model.ApiResponseLembretes
//import com.example.car_assist_mobile.data.model.ApiResponseSingleLembrete
import com.example.car_assist_mobile.data.model.ApiResponseSingleVeiculo
//import com.example.car_assist_mobile.data.model.LembreteRequest
import com.example.car_assist_mobile.data.model.LoginRequest
import com.example.car_assist_mobile.data.model.LoginResponse
import com.example.car_assist_mobile.data.model.ManutencaoListResponse
import com.example.car_assist_mobile.data.model.ManutencaoResponse
import com.example.car_assist_mobile.data.model.ProfileDataResponse
import com.example.car_assist_mobile.data.model.ProfileGetResponse
import com.example.car_assist_mobile.data.model.RegisterRequest
import com.example.car_assist_mobile.data.model.TipoManutencaoResponse
import com.example.car_assist_mobile.data.model.UpdateUserRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("v1/car-assist/usuario/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("v1/car-assist/veiculo/{id}")
    suspend fun buscarVeiculoPorId(
        @Path("id") id: Int
    ): Response<ApiResponseSingleVeiculo>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("v1/car-assist/usuario")
    suspend fun cadastrarUsuario(
        @Body request: RegisterRequest
    ): Response<ApiResponse>

    @Multipart
    @POST("v1/car-assist/veiculo-usuario")
    suspend fun cadastrarVeiculo(
        @Part("id_usuario") idUsuario: RequestBody,
        @Part("modelo") modelo: RequestBody,
        @Part("marca") marca: RequestBody,
        @Part("placa") placa: RequestBody,
        @Part("ano") ano: RequestBody,
        @Part("cor") cor: RequestBody,
        @Part("quilometragem") quilometragem: RequestBody,
        @Part foto_veiculo: MultipartBody.Part? = null
    ): Response<ApiResponse>

    @Multipart
    @PUT("v1/car-assist/veiculo/{id}")
    suspend fun atualizarVeiculo(
        @Path("id") idVeiculo: Int,
        @Part("id") idVeiculoBody: RequestBody,
        @Part("modelo") modelo: RequestBody,
        @Part("marca") marca: RequestBody,
        @Part("placa") placa: RequestBody,
        @Part("ano") ano: RequestBody,
        @Part("cor") cor: RequestBody,
        @Part("quilometragem") quilometragem: RequestBody,
        @Part("foto_veiculo") fotoVeiculoAntiga: RequestBody?,
        @Part foto_veiculo: MultipartBody.Part? = null
    ): Response<ApiResponse>

    @GET("v1/car-assist/usuario-veiculo/veiculo/{veiculo}")
    suspend fun buscarHistoricoDonos(
        @Path("veiculo") idVeiculo: Int
    ): Response<ApiResponseHistoricoDonos>
    @GET("v1/car-assist/usuario-veiculo/{id}")
    suspend fun buscarVeiculosPorUsuario(
        @Path("id") idUsuario: Int
    ): Response<ApiResponseVeiculos>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("v1/car-assist/usuario/{id}")
    suspend fun buscarUsuarioPorId(
        @Path("id") id: Int
    ): Response<ProfileGetResponse>

    @Multipart
    @PUT("v1/car-assist/usuario/{id}")
    suspend fun atualizarUsuario(
        @Path("id") id: Int,
        @Part("id") idUsuarioBody: RequestBody,
        @Part("nome") nome: RequestBody,
        @Part("email") email: RequestBody,
        @Part("cpf") cpf: RequestBody,
        @Part("foto_usuario") fotoUsuarioAntiga: RequestBody?,
        @Part("senha") senha: RequestBody,
        @Part foto: MultipartBody.Part? = null
    ): Response<ProfileDataResponse>

    @Multipart
    @POST("v1/car-assist/manutencao-evidencia")
    suspend fun adicionarManutencao(
        @Part("data_manutencao") dataManutencao: RequestBody,
        @Part("custo") custo: RequestBody,
        @Part("quilometragem") quilometragem: RequestBody,
        @Part("oficina") oficina: RequestBody,
        @Part("observacoes") observacoes: RequestBody,
        @Part("fk_id_tipo_manutencao") idTipoManutencao: RequestBody,
        @Part("fk_id_usuario") idUsuario: RequestBody,
        @Part("fk_id_veiculo") idVeiculo: RequestBody,
        @Part("pecas") pecas: RequestBody,
        @Part evidencias: List<MultipartBody.Part>? = null
    ): Response<ManutencaoResponse>

    @Multipart
    @PUT("v1/car-assist/manutencao/{id}")
    suspend fun atualizarManutencao(
        @Path("id") manutencaoId: Int,
        @Part("data_manutencao") dataManutencao: RequestBody,
        @Part("custo") custo: RequestBody,
        @Part("quilometragem") quilometragem: RequestBody,
        @Part("oficina") oficina: RequestBody,
        @Part("observacoes") observacoes: RequestBody,
        @Part("fk_id_tipo_manutencao") idTipoManutencao: RequestBody,
        @Part("fk_id_usuario") idUsuario: RequestBody,
        @Part("fk_id_veiculo") idVeiculo: RequestBody,
        @Part("pecas") pecas: RequestBody,
        @Part evidencias: List<MultipartBody.Part>? = null
    ): Response<ManutencaoResponse>

    @DELETE("v1/car-assist/manutencao/{id}")
    suspend fun deletarManutencao(
        @Path("id") manutencaoId: Int
    ): Response<ManutencaoResponse>

    @GET("v1/car-assist/tipo-manutencao/")
    suspend fun buscarTiposManutencao(): Response<TipoManutencaoResponse>

    @GET("v1/car-assist/manutencao-veiculo/{id}")
    suspend fun buscarManutencoesPorVeiculo(
        @Path("id") veiculoId: Int
    ): Response<ManutencaoListResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("v1/car-assist/transferencia/aceitar")
    suspend fun aceitarTransferencia(
        @Body request: AceitarTransferenciaRequest
    ): Response<ApiResponse>

}
