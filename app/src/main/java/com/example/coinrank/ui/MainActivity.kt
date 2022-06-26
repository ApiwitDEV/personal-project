package com.example.coinrank.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrank.R
import com.example.coinrank.databinding.ActivityMainBinding
import com.example.coinrank.model.CoinData
import com.example.coinrank.repositories.LoadData
import com.example.coinrank.viewmodel.CoinListViewModel
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val limit : Int = 25
    private lateinit var binding:ActivityMainBinding

    private val loader = LoadData()

    private val viewModel by lazy {
        ViewModelProvider(this).get(CoinListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil
            .setContentView(this,R.layout.activity_main)

        binding.main = this

        binding.lifecycleOwner = this

        load()
        binding.RV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager: LinearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == viewModel.name.size - 1
                    && viewModel.name[viewModel.name.size - 1] != "") {
                    viewModel.addEndPosition()
                    viewModel.offset += limit
                    load()
                }
            }
        })
    }

    fun load() {
        val res = loader.service.getCoins( R.string.apiKey.toString() , viewModel.offset , limit )
        res.enqueue(object : Callback<CoinData> {
            override fun onResponse(call: Call<CoinData>, response: Response<CoinData>) {
                val coins : CoinData
                if (response.body() != null) {
                    coins = response.body()!!
                    viewModel.removeEndPosition()
                    viewModel.setItem(coins, limit)
                    if(viewModel.offset == 0) {
                        binding.viewModel = viewModel
                        setTopThreeCoinIcon(binding.icon1 , viewModel.top3Icon[0])
                        setTopThreeCoinIcon(binding.icon2 , viewModel.top3Icon[1])
                        setTopThreeCoinIcon(binding.icon3 , viewModel.top3Icon[2])
                        binding.adapter = CoinListAdapter(viewModel)
                    }
                    (binding.adapter as CoinListAdapter).notifyDataSetChanged()
                }
                else load()
            }

            override fun onFailure(call: Call<CoinData>, t: Throwable) {
                Toast.makeText(baseContext,"Fail Loading",Toast.LENGTH_LONG).show()
            }
        })
    }

    fun setTopThreeCoinIcon(view : View , iconUrl : String) {
        if (iconUrl.contains(".svg")) {
            GlideToVectorYou.init().with(view.context)
                .load(Uri.parse(iconUrl), view as ImageView)
        }
        else {
            Picasso.get().load(iconUrl).into(view as ImageView)
        }
    }

    fun floatingButtonClick() {
        binding.RV.smoothScrollToPosition(0)
        Toast.makeText(baseContext,"go to top",Toast.LENGTH_SHORT).show()
    }
}