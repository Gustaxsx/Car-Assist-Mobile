package com.example.car_assist_mobile.data.network

import com.example.car_assist_mobile.data.model.ApiResponse
import com.example.car_assist_mobile.data.model.CarListResponse
import com.example.car_assist_mobile.data.model.CarRequest
import com.example.car_assist_mobile.data.model.LoginRequest
import com.example.car_assist_mobile.data.model.LoginResponse
import com.example.car_assist_mobile.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("v1/car-assist/usuario/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/car-assist/usuario")
    suspend fun cadastrarUsuario(
        @Body request: RegisterRequest
    ): Response<ApiResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/car-assist/veiculo-usuario")
    suspend fun cadastrarVeiculo(
        @Body carRequest: CarRequest
    ): Response<ApiResponse>

    @GET("v1/car-assist/usuario-veiculo/{id_usuario}")
    suspend fun buscarVeiculosPorUsuario(
        @Path("id_usuario") idUsuario: Int
    ): Response<CarListResponse>
}