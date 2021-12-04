package com.example.cryptocurrency.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao


@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCoinTx(coinTransaction: CoinTransaction)

    @Query("SELECT * FROM coin_transaction_table ORDER BY txDate DESC")
    fun readAllCoinTxs():LiveData<List<CoinTransaction>>

    @Query(value = "SELECT * FROM coin_holdings_table ORDER BY rank DESC")
    fun readAllCoinHoldings():LiveData<List<CoinHolding>>

    @Query("DELETE FROM coin_holdings_table WHERE id=:iD")
    suspend fun deleteCoinHolding(iD: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCoinHolding(coin: CoinHolding):Long

    @Query("SELECT * FROM coin_holdings_table WHERE coinID = :coinId")
    fun getCoinHoldingByCoinID(coinId: String): LiveData<CoinHolding>

    @Query("UPDATE coin_holdings_table SET amount=amount+:plusAmount, price=:price WHERE coinID = :coinId")
    suspend fun updateCoinHolding(coinId: String,plusAmount: Double,price:Double):Int
}