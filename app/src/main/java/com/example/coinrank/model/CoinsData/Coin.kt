package com.example.coinrank.model.CoinsData

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("24hVolume")
    val _24hVolume: String,
    @SerializedName("btcPrice")
    val btcPrice: String,
    @SerializedName("change")
    val change: Double,
    @SerializedName("coinrankingUrl")
    val coinrankingUrl: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("iconUrl")
    val iconUrl: String,
    @SerializedName("listedAt")
    val listedAt: Int,
    @SerializedName("marketCap")
    val marketCap: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("sparkline")
    val sparkline: List<String>,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("uuid")
    val uuid: String
)