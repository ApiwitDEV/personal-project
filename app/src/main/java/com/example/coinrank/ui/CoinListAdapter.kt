package com.example.coinrank.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrank.R
import com.example.coinrank.databinding.CoinItemBinding
import com.example.coinrank.viewmodel.CoinListViewModel

class CoinListAdapter(var viewModel: CoinListViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == 0) {
            val binding:CoinItemBinding = DataBindingUtil
                .inflate(inflater, R.layout.coin_item,parent,false)

            return coinListViewHolder(binding)
        }
        else {
            val view = inflater.inflate(R.layout.loading,parent,false)
            return loadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        viewModel.position = position
        if (getItemViewType(position) == 0) {
            holder as coinListViewHolder
            holder.binding.viewModel = viewModel
            holder.binding.executePendingBindings()
        }
        else {

        }
    }

    override fun getItemCount(): Int {
        return viewModel.name.size
    }

    override fun getItemViewType(position: Int): Int {
        if (viewModel.name[position] == "" ) {
            return 1
        }
        else {
            return 0
        }
    }
    class coinListViewHolder(binding: CoinItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }
    class loadingViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }
}