package com.example.ass2.api

import com.example.ass2.network.AdvancedApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://liyilin66.github.io/assignment/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: AdvancedApiService = retrofit.create(AdvancedApiService::class.java)
}
