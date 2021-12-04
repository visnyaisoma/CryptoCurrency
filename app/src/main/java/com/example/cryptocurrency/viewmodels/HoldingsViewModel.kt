package com.example.cryptocurrency.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.database.CoinHolding
import com.example.cryptocurrency.database.CoinTransaction
import com.example.cryptocurrency.database.HoldingsDatabase
import com.example.cryptocurrency.repository.HoldingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HoldingsViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository: HoldingsRepository

    init {
        val dao = HoldingsDatabase.getDataBase(application).dao()
        repository =  HoldingsRepository(dao)
    }
    fun readAllCoinsTransactions(): LiveData<List<CoinTransaction>>{
        return repository.readAllCoinsTransaction()
    }

    fun addCoinTx(coinTransaction: CoinTransaction){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCoinTx(coinTransaction)
        }
    }

    fun deleteCoinHolding(coinHolding: CoinHolding){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCoinHolding(coinHolding)
        }
    }

    fun addCoinHolding(coinHolding: CoinHolding):Long{
        var idk :Long = -100
        viewModelScope.launch(Dispatchers.IO) {
            idk = repository.addCoinHolding(coinHolding)
        }
        return idk
    }

    fun readAllCoinHoldings():LiveData<List<CoinHolding>>{
        return repository.readAllCoinHoldings()
    }

    fun updateCoinHolding(coinHolding: CoinHolding):Int {
        var res:Int = -1
        viewModelScope.launch(Dispatchers.IO) {
            res = repository.updateCoinHolding(coinHolding)
        }
        return res
    }
}