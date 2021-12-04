package com.example.cryptocurrency.repository

import com.example.cryptocurrency.network.RetrofitInstance
import com.example.cryptocurrency.model.Chart
import com.example.cryptocurrency.model.CoinList
import com.example.cryptocurrency.model.Fiat

import retrofit2.Response


class CoinRepository {
    suspend fun getCoins():Response<CoinList> {
        return RetrofitInstance.api.getCoins(
            skip = 0,
            limit = 1000,
            currency = "USD"
        )
    }

    suspend fun getFiats():Response<List<Fiat>>{
        return RetrofitInstance.api.getFiats()
    }

    suspend fun getChart(period: String, coinId:String):Response<Chart>{
        return RetrofitInstance.api.getChart(period = period,coinId=coinId)
    }
}