package com.example.cryptocurrency.model

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id") val id : String,
    @SerializedName("icon") val icon : String,
    @SerializedName("name") val name : String,
    @SerializedName("symbol") val symbol : String,
    @SerializedName("rank") val rank : Int,
    @SerializedName("price") val price : Double,
    @SerializedName("priceBtc") val priceBtc : Double,
    @SerializedName("volume") val volume : Double,
    @SerializedName("marketCap") val marketCap : Double,
    @SerializedName("availableSupply") val availableSupply : Double,
    @SerializedName("totalSupply") val totalSupply : Double,
    @SerializedName("priceChange1h") val priceChange1h : Double,
    @SerializedName("priceChange1d") val priceChange1d : Double,
    @SerializedName("priceChange1w") val priceChange1w : Double,
    @SerializedName("websiteUrl") val websiteUrl : String,
    @SerializedName("twitterUrl") val twitterUrl : String,
    @SerializedName("exp") val exp : List<String>,
)