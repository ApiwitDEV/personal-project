package com.example.coinrank.Model

import com.google.gson.annotations.SerializedName

data class MyDataItem(
    @SerializedName("data")
    val _data_: Data,
    @SerializedName("status")
    val status: String
)