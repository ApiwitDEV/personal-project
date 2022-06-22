package com.example.coinrank.ui

import android.os.Bundle
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

        binding.lifecycleOwner = this

        load(viewModel , 0)
    }

    fun load(viewModel: CoinListViewModel , offset0 : Int) {
        var offset = offset0
        val res = loader.service.getCoins( R.string.apiKey.toString() , offset , limit )
        res.enqueue(object : Callback<CoinData> {
            override fun onResponse(call: Call<CoinData>, response: Response<CoinData>) {
                val coins : CoinData
                if (response.body() != null) {
                    viewModel.removeEndPosition()
                    coins = response.body()!!
                }
                else return
                viewModel.setItem(coins, limit)
                if (offset == 0) {
                    binding.adapter = CoinListAdapter(viewModel)
                }
                else {
                    (binding.RV.adapter as CoinListAdapter).notifyDataSetChanged()
                }
                binding.RV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager: LinearLayoutManager =
                            recyclerView.layoutManager as LinearLayoutManager
                        if (layoutManager.findLastCompletelyVisibleItemPosition() == viewModel.name.size - 1) {
                            if (viewModel.name[viewModel.name.size - 1] != "") {
                                viewModel.addEndPosition()
                                offset += limit
                            }
                            load(viewModel,offset)
                        }
                    }
                })
            }

            override fun onFailure(call: Call<CoinData>, t: Throwable) {
                Toast.makeText(baseContext,"Fail Loading",Toast.LENGTH_LONG).show()
            }
        })
    }
}