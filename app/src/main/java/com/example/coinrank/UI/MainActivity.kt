package com.example.coinrank.UI

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrank.Repository.CoinsService
import com.example.coinrank.Model.MyDataItem
import com.example.coinrank.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    val BASE_URL = "https://api.coinranking.com/v2/"
    var items = mutableListOf<coinListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reLoad()
    }

    fun reLoad() {

        var button:Button = findViewById(R.id.button)
        button.setVisibility(View.GONE)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CoinsService::class.java)

        val retrofitData = retrofitBuilder
            .getData("coinranking49246176de07b17763f685360c399d6097bc8d20d6a1aa09")

        retrofitData.enqueue(object:Callback<MyDataItem>{
            override fun onResponse(call: Call<MyDataItem>, response: Response<MyDataItem>) {
                val res = response.body()!!
                var number1Image = findViewById<ImageView>(R.id.number1Image)
                var number1Symbol = findViewById<TextView>(R.id.number1Symbol)
                var number1Name = findViewById<TextView>(R.id.number1Name)
                var number1Change = findViewById<TextView>(R.id.number1Change)
                var number2Image = findViewById<ImageView>(R.id.number2Image)
                var number2Symbol = findViewById<TextView>(R.id.number2Symbol)
                var number2Name = findViewById<TextView>(R.id.number2Name)
                var number2Change = findViewById<TextView>(R.id.number2Change)
                var number3Image = findViewById<ImageView>(R.id.number3Image)
                var number3Symbol = findViewById<TextView>(R.id.number3Symbol)
                var number3Name = findViewById<TextView>(R.id.number3Name)
                var number3Change = findViewById<TextView>(R.id.number3Change)

                GlideToVectorYou.init().with(baseContext).load(Uri.parse(res._data_.coins[0].iconUrl),number1Image)
                GlideToVectorYou.init().with(baseContext).load(Uri.parse(res._data_.coins[1].iconUrl),number2Image)
                GlideToVectorYou.init().with(baseContext).load(Uri.parse(res._data_.coins[2].iconUrl),number3Image)
                number1Symbol.setText(res._data_.coins[0].symbol)
                number2Symbol.setText(res._data_.coins[1].symbol)
                number3Symbol.setText(res._data_.coins[2].symbol)
                number1Name.setText(res._data_.coins[0].name)
                number2Name.setText(res._data_.coins[1].name)
                number3Name.setText(res._data_.coins[2].name)

                number1Change.setTextColor(Color.parseColor("#06CA00"))
                number1Change.setText(res._data_.coins[0].change.toString())
                if (res._data_.coins[0].change < 0) {
                    number1Change.text = abs(res._data_.coins[0].change).toString()
                    number1Change.setTextColor(Color.parseColor("#FF0000"))
                }

                number2Change.setTextColor(Color.parseColor("#06CA00"))
                number2Change.setText(res._data_.coins[1].change.toString())
                if (res._data_.coins[1].change < 0) {
                    number2Change.setText(abs(res._data_.coins[1].change).toString())
                    number2Change.setTextColor(Color.parseColor("#FF0000"))
                }

                number3Change.setTextColor(Color.parseColor("#06CA00"))
                number3Change.setText(res._data_.coins[2].change.toString())
                if (res._data_.coins[2].change < 0) {
                    number3Change.setText(abs(res._data_.coins[2].change).toString())
                    number3Change.setTextColor(Color.parseColor("#FF0000"))
                }
                loadMore(3,res)

                var coinListRV = findViewById<RecyclerView>(R.id.coinListRV)
                coinListRV.layoutManager = LinearLayoutManager(baseContext)
                coinListRV.adapter = coinListAdapter(items,baseContext)
                coinListRV.addOnScrollListener(object:RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val layoutManager:LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                        if(layoutManager.findLastCompletelyVisibleItemPosition() == items.size - 1) {

                            var currentSize:Int = items.size + 2
                            items.removeAt(items.size - 1)
                            loadMore(currentSize,res)
                            (coinListRV.adapter as coinListAdapter).notifyDataSetChanged()
                        }
                    }
                })
            }
            override fun onFailure(call: Call<MyDataItem>, t: Throwable) {
                button.setVisibility(View.VISIBLE)
                button.setOnClickListener{
                    reLoad()
                }
            }
        })
    }

    fun loadMore(currentSize:Int,res:MyDataItem) {
        var i:Int = currentSize
        var resSize:Int = res._data_.coins.size
        while(i < currentSize + 10) {
            if (i >= resSize) {
                Toast.makeText(baseContext, "End", Toast.LENGTH_SHORT).show()
                return
            }
            val format = DecimalFormat()
            format.applyPattern("0.00000")
            var price:String = format.format(res._data_.coins[i].price)
            items.add(
                coinListItem(res._data_.coins[i].iconUrl,price,
                res._data_.coins[i].name,res._data_.coins[i].symbol,res._data_.coins[i].change
                ,res._data_.coins[i].iconUrl)
            )
            ++i
        }
        items.add(coinListItem("",0,"end","",0.0,""))
    }

    fun loadTest(currentSize:Int) {
        var i:Int = currentSize
        while(i < currentSize + 10) {
            items.add(coinListItem("",i,"","",0.0,""))
            ++i
        }
        items.add(coinListItem("",0,"end","",0.0,""))
    }
}