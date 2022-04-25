package com.example.coinrank.Repository

import com.example.coinrank.Model.MyDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsService {

    @GET("coins")
    fun getData(@Query("coinranking49246176de07b17763f685360c399d6097bc8d20d6a1aa09") apiKey:String):Call<MyDataItem>
}