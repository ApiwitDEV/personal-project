package com.example.coinrank.UI

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coinrank.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlin.math.abs

class coinListAdapter(var data: MutableList<coinListItem>,var context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val viewTypeLoading:Int = 1
    val viewTypeItem:Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == viewTypeItem) {
            var view = LayoutInflater.from(parent.context).inflate(
                R.layout.coinlist_layout,parent,false
            )
            return coinListVHolder(view)
        }
        else {
            var view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_loading,parent,false
            )
            return loadingVHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == viewTypeItem) {
            holder as coinListVHolder
            var change: Double = data[position].Change
            holder.coinListChange.setTextColor(Color.parseColor("#06CA00"))
            if (change < 0) {
                change = abs(change)
                holder.coinListChange.setTextColor(Color.parseColor("#FF0000"))
            }
            holder.coinListImage.setImageResource(R.drawable.ic_baseline_person_24)
            holder.coinListPrice.setText("$" + data[position].Price.toString())
            holder.coinListName.setText(data[position].Name)
            holder.coinListSymbol.setText(data[position].Symbol)
            holder.coinListChange.setText(change.toString())
            if (data[position].icon.contains(".svg")) {
                GlideToVectorYou.init().with(context)
                    .load(Uri.parse(data[position].icon), holder.coinListImage)
            } else {
                Glide.with(context).load(data[position].icon).into(holder.coinListImage)
            }
        }

        else {

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        if(data.get(position).Name == "end") {
            return viewTypeLoading
        }
        else {
            return viewTypeItem
        }
    }
}