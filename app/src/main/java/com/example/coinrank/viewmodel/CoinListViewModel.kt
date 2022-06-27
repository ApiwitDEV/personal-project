package com.example.coinrank.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coinrank.model.CoinsData.CoinsData
import java.text.DecimalFormat

class CoinListViewModel : ViewModel() {

    var offset : Int = 0
    private var _search = MutableLiveData<String>("")
    var search : LiveData<String> = _search

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

    fun setItem(coins : CoinsData) {

        val format = DecimalFormat()
        format.applyPattern("#.###")

        if (offset == 0 && search.value.toString() == "") {
            setEmpty()
            var counter = 0
            for (item in coins.data.coins) {
                val formatPrice : String = format.format(item.price)
                if (counter < 3) {
                    top3Icon.add( item.iconUrl )
                    top3name.add( item.name )
                    top3symbol.add( item.symbol )
                    top3price.add("$"+formatPrice)
                    top3change.add( item.change.toString() )
                    if (item.change >= 0) {
                        colorOfChangeValue.add(true)
                    }
                    else {
                        colorOfChangeValue.add(false)
                    }
                    change.add( item.change.toString() )
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
                counter += 1
            }
        }
        else if (search.value.toString() != "") {
            setEmpty()
            for (item in coins.data.coins) {
                val formatPrice : String = format.format(item.price)
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
        else {
            for (item in coins.data.coins) {
                val formatPrice : String = format.format(item.price)
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

    fun setEmpty() {
        iconUrl = mutableListOf()
        name = mutableListOf()
        symbol = mutableListOf()
        price = mutableListOf()
        change = mutableListOf()
        colorOfChangeValue = mutableListOf()
    }

    fun addEndPosition() {
        name.add( "" )
    }

    fun removeEndPosition() {
        name.remove("")
    }

    fun setSearch(text : String) {
        _search.value = text
    }
}