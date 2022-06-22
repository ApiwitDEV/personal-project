package com.example.coinrank

import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso

@BindingAdapter("setIcon")
fun setIcon(view : View, iconUrl : String) {
    if (iconUrl.contains(".svg")) {
        GlideToVectorYou.init().with(view.context)
            .load(Uri.parse(iconUrl), view as ImageView)
    }
    else {
        Picasso.get().load(iconUrl).into(view as ImageView)
    }
}

@BindingAdapter("setTextColor")
fun setTextColor(view : TextView , colorStatus : Boolean) {
    view.setTextColor(Color.parseColor("#FF0000"))
    if (colorStatus)
        view.setTextColor(Color.parseColor("#06CA00"))
}