package com.example.coinrank.viewmodel

import androidx.lifecycle.ViewModel
import com.example.coinrank.model.CoinData
import java.text.DecimalFormat

class CoinListViewModel : ViewModel() {

    var offset : Int = 0

    var top3Icon = mutableListOf<String>()
    var top3name = mutableListOf<String>()
    var top3symbol : MutableList<String> = mutableListOf()
    var top3price : MutableList<String> = mutableListOf()
    var top3change : MutableList<String> = mutableListOf()
    var top3colorOfChangeValue : MutableList<Boolean> = mutableListOf()

    var iconUrl : MutableList<String> = mutableListOf()
    var name : MutableList<String> = mutableListOf()
    var symbol : MutableList<String> = mutableListOf()
    var price : MutableList<String> = mutableListOf()
    var change : MutableList<String> = mutableListOf()
    var colorOfChangeValue : MutableList<Boolean> = mutableListOf() // true : Green (UP) or false : Red (Down)

    var position : Int = 0

    fun setItem(coins : CoinData , limit : Int) {
        var initial : Int
        if (offset == 0) {
            initial = 3
        }
        else {
            initial = 0
        }

        val format = DecimalFormat()
        format.applyPattern("#.###")

        for (position in 0 until limit) {
            val item = coins.data.coins[position]
            var formatPrice : String = format.format(item.price)
            if (position < initial) {
                top3Icon.add( item.iconUrl )
                top3name.add( item.name )
                top3symbol.add( item.symbol )

                top3price.add( "$"+formatPrice )
                if (item.change >= 0) {
                    top3colorOfChangeValue.add(true)
                }
                else {
                    top3colorOfChangeValue.add(false)
                }
                top3change.add( item.change.toString() )
            }
            else {
                iconUrl.add( item.iconUrl )
                name.add( item.name )
                symbol.add( item.symbol )

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
    }
    fun addEndPosition() {
        name.add( "" )
    }
    fun removeEndPosition() {
        name.remove("")
    }
}