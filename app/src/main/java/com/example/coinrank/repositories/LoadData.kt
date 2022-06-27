package com.example.coinrank.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoadData {

    private val BASE_URL = "https://api.coinranking.com/v2/"
    val service : CoinService

    constructor() {
        val retrofit:Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(CoinService::class.java)
    }
}