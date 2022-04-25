package com.example.coinrank.UI

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrank.R

class coinListVHolder(v: View) : RecyclerView.ViewHolder(v) {
    var coinListPrice = v.findViewById<TextView>(R.id.coinListItemPrice)
    var coinListImage = v.findViewById<ImageView>(R.id.coinListItemImage)
    var coinListName = v.findViewById<TextView>(R.id.Name)
    var coinListSymbol = v.findViewById<TextView>(R.id.Symbol)
    var coinListChange = v.findViewById<TextView>(R.id.Change)
}