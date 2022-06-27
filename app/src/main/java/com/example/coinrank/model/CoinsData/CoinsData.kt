package com.example.coinrank.model.CoinsData

import com.google.gson.annotations.SerializedName

data class CoinsData(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String
)