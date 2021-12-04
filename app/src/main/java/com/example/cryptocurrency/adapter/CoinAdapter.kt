package com.example.cryptocurrency.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.CoinCardLayoutBinding
import com.example.cryptocurrency.fragments.CoinsDirections
import com.example.cryptocurrency.model.Coin

class CoinAdapter : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    private var list = emptyList<Coin>()

    inner class CoinViewHolder(var binding: CoinCardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {

        return CoinViewHolder(CoinCardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.binding.cardTitle.text = list[position].name
        holder.binding.rankText.text = ("#"+list[position].rank.toString())
        holder.binding.cardImage.load(list[position].icon){
            placeholder(R.drawable.cryptocurrencies)
        }
        holder.binding.coinCardView.setOnClickListener {
            val action = CoinsDirections.actionCoinsToCoinDetails(list[position].rank)
            holder.binding.coinCardView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(coins :List<Coin>){
        this.list = coins
        notifyDataSetChanged()
    }
}