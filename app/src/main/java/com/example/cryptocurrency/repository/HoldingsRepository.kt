package com.example.cryptocurrency.repository

import androidx.lifecycle.LiveData
import com.example.cryptocurrency.database.CoinHolding
import com.example.cryptocurrency.database.CoinTransaction
import com.example.cryptocurrency.database.Dao


class HoldingsRepository(private val dao: Dao) {
    fun readAllCoinsTransaction():LiveData<List<CoinTransaction>>{
        return dao.readAllCoinTxs()
    }

    suspend fun addCoinTx(coinTransaction: CoinTransaction){
        dao.addCoinTx(coinTransaction)
    }

    suspend fun deleteCoinHolding(coinHolding: CoinHolding){
        dao.deleteCoinHolding(coinHolding.id)
    }

    suspend fun addCoinHolding(coinHolding: CoinHolding):Long{
        return dao.addCoinHolding(coinHolding)
    }

    fun readAllCoinHoldings():LiveData<List<CoinHolding>>{
        return dao.readAllCoinHoldings()
    }

    suspend fun updateCoinHolding(coinHolding: CoinHolding):Int {
        return dao.updateCoinHolding(coinHolding.coin.id,coinHolding.amount,coinHolding.coin.price)
    }
}