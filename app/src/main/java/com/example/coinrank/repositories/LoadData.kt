package com.example.coinrank.repositories

import com.example.coinrank.R
import com.example.coinrank.model.CoinData
import retrofit2.Call
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