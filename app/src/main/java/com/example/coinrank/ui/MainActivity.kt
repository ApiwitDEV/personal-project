package com.example.coinrank.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrank.R
import com.example.coinrank.databinding.ActivityMainBinding
import com.example.coinrank.model.CoinsData.CoinsData
import com.example.coinrank.repositories.LoadData
import com.example.coinrank.viewmodel.CoinListViewModel
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val limit : Int = 100
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
                    && viewModel.name.last() != ""
                    && viewModel.search.value.toString() == "") {
                    viewModel.addEndPosition()
                    viewModel.offset += limit
                    load()
                }
            }
        })

        binding.search.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.offset = 0
                viewModel.setSearch(p0.toString())
                load()
                return false
            }
        })
    }

    fun load() {
        val res = loader.service.getCoins( R.string.apiKey.toString() , viewModel.offset
            , limit , viewModel.search.value.toString() )
        res.enqueue(object : Callback<CoinsData> {
            override fun onResponse(call: Call<CoinsData>, response: Response<CoinsData>) {
                val coins : CoinsData
                if (response.isSuccessful) {
                    coins = response.body()!!
                    viewModel.removeEndPosition()
                    viewModel.setItem(coins)
                    if(viewModel.offset == 0 && viewModel.search.value.toString() == "") {
                        binding.viewModel = viewModel
                        setTopThreeCoinIcon(binding.icon1 , viewModel.top3Icon[0])
                        setTopThreeCoinIcon(binding.icon2 , viewModel.top3Icon[1])
                        setTopThreeCoinIcon(binding.icon3 , viewModel.top3Icon[2])
                        binding.adapter = CoinListAdapter(viewModel)
                    }
                    (binding.adapter as CoinListAdapter).notifyDataSetChanged()
                }
                else {
                    Toast.makeText(baseContext,response.code().toString()
                            +" HTTP ERROR",Toast.LENGTH_SHORT).show()
                    load()
                }
            }

            override fun onFailure(call: Call<CoinsData>, t: Throwable) {
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