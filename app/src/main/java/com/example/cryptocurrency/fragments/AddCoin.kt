package com.example.cryptocurrency.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.cryptocurrency.database.CoinHolding
import com.example.cryptocurrency.database.CoinTransaction
import com.example.cryptocurrency.database.PersistentCoin
import com.example.cryptocurrency.databinding.FragmentAddCoinBinding
import com.example.cryptocurrency.model.Coin
import com.example.cryptocurrency.viewmodels.CoinsViewModel
import com.example.cryptocurrency.viewmodels.HoldingsViewModel
import java.util.*


class AddCoin : Fragment() {

    private lateinit var binding: FragmentAddCoinBinding
    private val args:AddCoinArgs by navArgs()
    private val coinsViewModel: CoinsViewModel by activityViewModels()
    private val holdingsViewModel: HoldingsViewModel by activityViewModels()
    private lateinit var coin:Coin


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCoinBinding.inflate(layoutInflater)
        val index = args.coinRank-1
        val chosenFiat = args.chosenfiat
        coinsViewModel.getCoinsResponse().observe(viewLifecycleOwner){
                response -> if (response.isSuccessful){
            coin=response.body()!!.coins[index]
            binding.coinNameTv.text=coin.name
            binding.imageView.load(coin.icon)
            val priceInput  = EditText(requireContext())
            priceInput.setText((coin.price*chosenFiat.rate).toString())
            binding.addCoinInputPrice.text=priceInput.text
        }
        }

        setClickListener()

        return binding.root
    }

    private fun setClickListener(){
        binding.btnAddToWallet.setOnClickListener {
            val inputPrice = binding.addCoinInputPrice.text
            var price = inputPrice!!.split(" ")[0].toDoubleOrNull()
            val inputAmount = binding.addCoinInputAmount.text
            val amount = inputAmount!!.split(" ")[0].toDoubleOrNull()
            if (amount==null||price==null){
                Toast.makeText(requireContext(),"Please input valid values",Toast.LENGTH_SHORT).show()
            }
            else{
                price /= args.chosenfiat.rate
                val transaction = CoinTransaction(0, PersistentCoin(coin),price,Calendar.getInstance().time,amount,args.chosenfiat)
                holdingsViewModel.addCoinTx(transaction)
                holdingsViewModel.updateCoinHolding(CoinHolding(0,PersistentCoin(coin),amount))
                val coinHolding = CoinHolding(0,PersistentCoin(coin), amount = amount)
                holdingsViewModel.addCoinHolding(coinHolding)
                Toast.makeText(requireContext(),"Transaction successful",Toast.LENGTH_SHORT).show()

                val action = AddCoinDirections.actionAddCoinToCoinDetails(coinRank = coin.rank)
                findNavController().navigate(action)
            }
        }
    }

}