package com.example.car_assist_mobile.data.network

import com.example.car_assist_mobile.data.model.ApiResponseVeiculos
import com.example.car_assist_mobile.data.model.ApiResponse
import com.example.car_assist_mobile.data.model.ApiResponseLembretes
import com.example.car_assist_mobile.data.model.ApiResponseSingleLembrete
import com.example.car_assist_mobile.data.model.ApiResponseSingleVeiculo
import com.example.car_assist_mobile.data.model.ApiResponseVeiculosLembrete
import com.example.car_assist_mobile.data.model.LembreteRequest
import com.example.car_assist_mobile.data.model.LoginRequest
import com.example.car_assist_mobile.data.model.LoginResponse
import com.example.car_assist_mobile.data.model.ProfileDataResponse
import com.example.car_assist_mobile.data.model.ProfileGetResponse
import com.example.car_assist_mobile.data.model.RegisterRequest
import com.example.car_assist_mobile.data.model.UpdateUserRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
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
        @Part foto_veiculo: MultipartBody.Part? = null
    ): Response<ApiResponse>

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

    @Headers("Content-Type: application/json")
    @POST("v1/car-assist/lembrete")
    suspend fun cadastrarLembrete(
        @Body request: LembreteRequest
    ): Response<ApiResponseSingleLembrete>

    @GET("v1/car-assist/lembrete/usuario/{idUsuario}")
    suspend fun buscarLembretesPorUsuario(
        @Path("idUsuario") idUsuario: Int
    ): Response<ApiResponseLembretes>

    @GET("v1/car-assist/veiculo/usuario/{idUsuario}")
    suspend fun buscarVeiculosDoUsuario(
        @Path("idUsuario") idUsuario: Int
    ): Response<com.example.car_assist_mobile.screens.adicionarlembrete.ApiResponseVeiculosLembrete>


}