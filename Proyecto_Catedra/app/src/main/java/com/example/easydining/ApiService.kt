package com.example.easydining

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("Restaurantes")
    fun getRestaurantes(): Call<List<Restaurante>>
}