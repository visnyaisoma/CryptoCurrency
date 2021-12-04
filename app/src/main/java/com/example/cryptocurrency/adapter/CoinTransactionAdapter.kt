package com.example.cryptocurrency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cryptocurrency.database.CoinTransaction
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.CoinTransactionCardLayoutBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class CoinTransactionAdapter
    : RecyclerView.Adapter<CoinTransactionAdapter.CoinTransactionViewHolder>() {

    private var list = emptyList<CoinTransaction>()
    private val df: DecimalFormat = DecimalFormat("#.####")

    inner class CoinTransactionViewHolder(var binding: CoinTransactionCardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinTransactionViewHolder {

        return CoinTransactionViewHolder(CoinTransactionCardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CoinTransactionViewHolder, position: Int) {
        val coinTransaction = list[position]
        holder.binding.cardImage.load(coinTransaction.coin.icon){
            placeholder(R.drawable.cryptocurrencies)
        }
        holder.binding.coinTransactionAmount.text = df.format(coinTransaction.amount).toString()
        holder.binding.coinTransactionPrice.text = (df.format(coinTransaction.txAtPrice*coinTransaction.txFiat.rate).toString()+" "+coinTransaction.txFiat.symbol)
        holder.binding.coinTransactionNameTv.text = coinTransaction.coin.name
        holder.binding.coinTransactionSymbolTv.text = coinTransaction.coin.symbol
        val pattern = "yyyy-MM-dd hh:mm"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(coinTransaction.txDate)
        holder.binding.coinTransactionDateTv.text = date
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(coinTransactions :List<CoinTransaction>){
        this.list = coinTransactions
        notifyDataSetChanged()
    }
}