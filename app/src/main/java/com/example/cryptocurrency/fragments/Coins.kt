package com.example.cryptocurrency.fragments

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.adapter.CoinAdapter
import com.example.cryptocurrency.databinding.FragmentCoinsBinding
import com.example.cryptocurrency.viewmodels.CoinsViewModel


class Coins() : Fragment(),SearchView.OnQueryTextListener, Parcelable {

    private lateinit var binding : FragmentCoinsBinding
    private lateinit var adapter: CoinAdapter

    private val viewModel: CoinsViewModel by activityViewModels()
    private lateinit var searchView: SearchView

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View{
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCoinsBinding.inflate(layoutInflater)
        searchView=binding.searchView
        searchView.setOnQueryTextListener(this)
        initRecyclerView()
        viewModel.getCoinsResponse().observe(viewLifecycleOwner, {
                response -> if (response!=null){
            if (response.isSuccessful){
                if (response.body()!=null){
                    response.body()?.let { adapter.setData(it.coins) }
                }
            }
        } else{
            Toast.makeText(context,"Check network connection!",Toast.LENGTH_LONG).show()
        }
        })
        return binding.root
    }

    private fun initRecyclerView() {
        adapter = CoinAdapter()
        binding.coinRecycler.layoutManager = LinearLayoutManager(context)
        binding.coinRecycler.adapter = adapter
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!=null){
            searchCoins(newText)
        }
        return true
    }

    private fun searchCoins(query: String){
        viewModel.searchCoinResponse(query).observe(viewLifecycleOwner, {
                list -> adapter.setData(list)
        })
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coins> {
        override fun createFromParcel(parcel: Parcel): Coins {
            return Coins(parcel)
        }

        override fun newArray(size: Int): Array<Coins?> {
            return arrayOfNulls(size)
        }
    }
}