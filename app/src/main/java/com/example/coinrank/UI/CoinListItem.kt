package com.example.coinrank.UI

class coinListItem {
    var Image:String
    var Price:Any
    var Name:String
    var Symbol:String
    var Change:Double
    var icon:String

    constructor(Image:String, Price: Any,Name:String,Symbol:String,Change:Double,icon:String) {
        this.Image = Image
        this.Price = Price
        this.Name = Name
        this.Symbol = Symbol
        this.Change = Change
        this.icon = icon
    }
}