package com.example.coinrank.repositories

import com.example.coinrank.R
import com.example.coinrank.model.CoinsData.CoinsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CoinService {
    @GET("coins")
    fun getCoins(@Header(R.string.apiKey.toString()) apiKey : String
                ,@Query("offset") offset : Int
                ,@Query("limit") limit : Int
                ,@Query("search") search: String)
    : Call<CoinsData>
}