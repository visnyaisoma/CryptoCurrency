package com.example.cryptocurrency.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.cryptocurrency.model.Coin
import com.example.cryptocurrency.model.CoinList
import com.example.cryptocurrency.model.Fiat
import com.example.cryptocurrency.repository.CoinRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CoinsViewModel(application: Application) :AndroidViewModel(application) {

    private var coinsResponse: MutableLiveData<Response<CoinList>> = MutableLiveData()
    private var fiatResponse: MutableLiveData<Response<List<Fiat>>> = MutableLiveData()
    private var coinsSearch: MutableLiveData<ArrayList<Coin>> = MutableLiveData()
    private val chosenFiat: MutableLiveData<Fiat> = MutableLiveData()
    private val repository:CoinRepository = CoinRepository()
    private val sharedPreferences:SharedPreferences = application.getSharedPreferences("currency_preferences", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()



    fun getCoinsResponse():LiveData<Response<CoinList>>{
        viewModelScope.launch {
            val coins = repository.getCoins()
            coinsResponse.value = coins
        }
        return coinsResponse
    }

    fun getFiatsResponse():LiveData<Response<List<Fiat>>>{
        viewModelScope.launch {
            val fiats = repository.getFiats()
            fiatResponse.value= fiats
        }
        return  fiatResponse
    }

    fun chooseFiat(index:Int):MutableLiveData<Fiat>{
        getFiatsResponse()
        if (fiatResponse.value!=null){
            if (fiatResponse.value!!.isSuccessful){
                if (fiatResponse.value!!.body()!!.size>index){
                    chosenFiat.value = fiatResponse.value!!.body()!![index]
                    editor.apply{
                        putString("chosenCurrency", chosenFiat.value!!.name)
                        apply()
                    }
                    return chosenFiat
                }
            }
        }
        return MutableLiveData(Fiat("USD",1.0,"$","https://s3-us-west-2.amazonaws.com/coin-stats-icons/flags/USD.png"))
    }


    fun getChosenFiat():MutableLiveData<Fiat>{
        val chosen = sharedPreferences.getString("chosenCurrency","USD")
        getFiatsResponse()
        if (fiatResponse.value!=null){
            if (fiatResponse.value!!.isSuccessful){
                for (i in fiatResponse.value!!.body()!!) {
                    if (i.name==chosen){
                        chosenFiat.value=i
                        return chosenFiat
                    }
                }
            }
        }
        return MutableLiveData(Fiat("USD",1.0,"$","https://s3-us-west-2.amazonaws.com/coin-stats-icons/flags/USD.png"))
    }


    fun searchCoinResponse(queryString: String) : LiveData<ArrayList<Coin>> {
        queryCoins(queryString)
        return coinsSearch
    }

    private fun queryCoins(queryString: String){
        if (coinsResponse.value!=null){
            coinsSearch.value = ArrayList()
            if(coinsResponse.value!!.isSuccessful){
                if (!coinsResponse.value!!.body()!!.coins.isNullOrEmpty()){
                    coinsResponse.value?.let {
                        if(queryString.isEmpty()){
                            coinsSearch.value!!.addAll(it.body()!!.coins)
                        }
                        for (coin in it.body()!!.coins) {
                            if (coin.symbol.contains(queryString,ignoreCase = true)){
                                coinsSearch.value!!.add(coin)
                            }
                            else if(coin.name.contains(queryString,ignoreCase = true)) {
                                coinsSearch.value!!.add(coin)
                            }
                            else if(coin.id.contains(queryString,ignoreCase = true)) {
                                coinsSearch.value!!.add(coin)
                            }
                        }
                    }

                }
            }
        }

    }



}