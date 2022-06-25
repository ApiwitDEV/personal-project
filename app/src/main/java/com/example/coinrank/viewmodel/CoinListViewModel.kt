package com.example.coinrank.viewmodel

import androidx.lifecycle.ViewModel
import com.example.coinrank.model.CoinData
import java.text.DecimalFormat

class CoinListViewModel : ViewModel() {

    var iconUrl : MutableList<String> = mutableListOf()
    var name : MutableList<String> = mutableListOf()
    var symbol : MutableList<String> = mutableListOf()
    var price : MutableList<String> = mutableListOf()
    var change : MutableList<String> = mutableListOf()
    var colorOfChangeValue : MutableList<Boolean> = mutableListOf() // true : Green (UP) or false : Red (Down)

    var position : Int = 0

    fun setItem(coins : CoinData , limit : Int) {

        var format = DecimalFormat()
        format.applyPattern("#.###")

        for (position in 0 until limit) {
            val item = coins.data.coins[position]
            iconUrl.add( item.iconUrl )
            name.add( item.name )
            symbol.add( item.symbol )

            var formatPrice : String = format.format(item.price)

            price.add( "$"+formatPrice )

            if (item.change >= 0) {
                colorOfChangeValue.add(true)
            }
            else {
                colorOfChangeValue.add(false)
            }
            change.add( item.change.toString() )
        }
    }
    fun addEndPosition() {
        name.add( "" )
    }
    fun removeEndPosition() {
        name.remove("")
    }
}