package com.example.cryptocurrency.database

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.example.cryptocurrency.model.Coin

data class PersistentCoin(
    @ColumnInfo(name = "coinID")val id : String,
    val icon : String,
    val name : String,
    val symbol : String,
    val rank : Int,
    val price : Double,
    val priceBtc : Double,
    val marketCap : Double,
    val availableSupply : Double,
    val totalSupply : Double,
){
    @Ignore constructor(coin:Coin):this(coin.id,coin.icon,coin.name,coin.symbol,coin.rank,coin.price,coin.priceBtc,coin.marketCap,coin.availableSupply,coin.totalSupply)
}