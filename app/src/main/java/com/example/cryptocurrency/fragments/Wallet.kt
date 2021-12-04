package com.example.cryptocurrency.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.adapter.CoinTransactionAdapter
import com.example.cryptocurrency.databinding.FragmentWalletBinding
import com.example.cryptocurrency.model.Fiat
import com.example.cryptocurrency.viewmodels.CoinsViewModel
import com.example.cryptocurrency.viewmodels.HoldingsViewModel

class Wallet : Fragment() {

    private lateinit var binding : FragmentWalletBinding
    private val holdingsViewModel:HoldingsViewModel by activityViewModels()
    private val coinsViewModel:CoinsViewModel by activityViewModels()
    private lateinit var transactionAdapter: CoinTransactionAdapter
    private var adapterInit = false
    private lateinit var chosenFiat:Fiat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View{
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentWalletBinding.inflate(layoutInflater)

        initFab()
        coinsViewModel.getChosenFiat().observe(viewLifecycleOwner,{//12
            chosenFiat=it
            if (!adapterInit)
                initRecyclerView()
            adapterInit=true
        })
        holdingsViewModel.readAllCoinsTransactions().observe(viewLifecycleOwner){
            if (it!=null){
                transactionAdapter.setData(it)
            }
        }

        return binding.root
    }

    private fun initFab() {
        binding.floatingActionButton.setOnClickListener {
            val action = WalletDirections.actionWalletToChooseCurrencyDialog()
            findNavController().navigate(action)
        }
    }

    private fun initRecyclerView() {
        transactionAdapter = CoinTransactionAdapter()
        binding.walletList.layoutManager = LinearLayoutManager(context)
        binding.walletList.adapter = transactionAdapter
    }
}