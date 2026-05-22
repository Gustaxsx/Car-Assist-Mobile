package com.example.car_assist_mobile.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    //Quando for testar coloque o mesmo IP do sua rede de wifi no caso o IPV4
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}