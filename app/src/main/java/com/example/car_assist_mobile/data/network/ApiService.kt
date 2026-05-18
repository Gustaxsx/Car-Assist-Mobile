package com.example.car_assist_mobile.data.network

import com.example.car_assist_mobile.data.model.ApiResponse
import com.example.car_assist_mobile.data.model.LoginRequest
import com.example.car_assist_mobile.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("v1/car-assist/usuario/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ApiResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/car-assist/usuario")
    suspend fun cadastrarUsuario(
        @Body request: RegisterRequest
    ): Response<ApiResponse>
}