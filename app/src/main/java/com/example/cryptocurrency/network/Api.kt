package com.example.cryptocurrency.network

import com.example.cryptocurrency.model.Chart
import com.example.cryptocurrency.model.CoinList
import com.example.cryptocurrency.model.Fiat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("coins")
    suspend fun getCoins(
        @Query("skip")skip: Int,
        @Query("limit")limit:Int,
        @Query("currency")currency:String
    ):Response<CoinList>

    @GET("fiats")
    suspend fun getFiats():Response<List<Fiat>>

    @GET("charts")
    suspend fun getChart(
        @Query("period")period: String,
        @Query("coinId")coinId: String,
    ): Response<Chart>
}