package com.example.cryptocurrency.adapter

import android.annotation.SuppressLint
import com.example.cryptocurrency.databinding.CoinHoldingCardLayoutBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cryptocurrency.database.CoinHolding
import com.example.cryptocurrency.R
import com.example.cryptocurrency.fragments.PortfolioDirections
import com.example.cryptocurrency.model.Fiat
import java.text.DecimalFormat


class CoinHoldingAdapter
    : RecyclerView.Adapter<CoinHoldingAdapter.CoinHoldingViewHolder>() {


    private var list = emptyList<CoinHolding>()
    private val df: DecimalFormat = DecimalFormat("#.####")
    private var chosenFiat = Fiat("USD",1.0,"$","https://s3-us-west-2.amazonaws.com/coin-stats-icons/flags/USD.png")

    inner class CoinHoldingViewHolder(var binding: CoinHoldingCardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinHoldingViewHolder {


        return CoinHoldingViewHolder(CoinHoldingCardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CoinHoldingViewHolder, position: Int) {
        val coinHolding = list[position]
        holder.binding.cardImage.load(coinHolding.coin.icon){
            placeholder(R.drawable.cryptocurrencies)
        }
        val price  = coinHolding.coin.price*chosenFiat.rate
        holder.binding.coinHoldingCardView.setOnClickListener {
            val action = PortfolioDirections.actionPortfolioToSellCoin(coinHolding.coin.id,chosenFiat)
            holder.binding.coinHoldingCardView.findNavController().navigate(action)
        }
        holder.binding.coinHoldingAmount.text = df.format(coinHolding.amount).toString()
        holder.binding.coinHoldingPrice.text = (df.format(price*coinHolding.amount).toString()+" "+chosenFiat.symbol)
        holder.binding.coinHoldingNameTv.text = coinHolding.coin.name
        holder.binding.coinHoldingSymbolTv.text = coinHolding.coin.symbol
    }


    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(coinHoldings :List<CoinHolding>){
        this.list = coinHoldings
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFiat(fiat: Fiat){
        this.chosenFiat = fiat

        notifyDataSetChanged()
    }

    fun getData():List<CoinHolding>{
        return list
    }
}