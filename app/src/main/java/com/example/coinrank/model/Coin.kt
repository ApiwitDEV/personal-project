package com.example.coinrank.model

data class Coin(
    val _24hVolume: String,
    val btcPrice: String,
    val change: Double,
    val coinrankingUrl: String,
    val color: String,
    val iconUrl: String,
    val listedAt: Int,
    val marketCap: String,
    val name: String,
    val price: Double,
    val rank: Int,
    val sparkline: List<String>,
    val symbol: String,
    val uuid: String
)