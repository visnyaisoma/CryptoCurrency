package com.example.cryptocurrency.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptocurrency.model.Fiat
import java.util.Date

@Entity(tableName = "coin_transaction_table")
data class CoinTransaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @Embedded
    var coin: PersistentCoin,
    val txAtPrice: Double,
    var txDate: Date,
    var amount:Double,
    @Embedded
    var txFiat:Fiat
)