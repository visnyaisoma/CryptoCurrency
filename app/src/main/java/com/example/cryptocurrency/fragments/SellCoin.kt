package com.example.cryptocurrency.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.cryptocurrency.database.CoinHolding
import com.example.cryptocurrency.database.CoinTransaction
import com.example.cryptocurrency.database.PersistentCoin
import com.example.cryptocurrency.databinding.FragmentSellCoinBinding
import com.example.cryptocurrency.model.Coin
import com.example.cryptocurrency.viewmodels.CoinsViewModel
import com.example.cryptocurrency.viewmodels.HoldingsViewModel
import java.lang.StrictMath.pow
import java.util.*

class SellCoin : Fragment() {

    private lateinit var binding: FragmentSellCoinBinding
    private val holdingsViewModel: HoldingsViewModel by activityViewModels()
    private val coinsViewModel:CoinsViewModel by activityViewModels()
    private lateinit var coinHolding: CoinHolding
    private lateinit var coin:Coin
    private val args:SellCoinArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSellCoinBinding.inflate(layoutInflater)
        holdingsViewModel.readAllCoinHoldings().observe(viewLifecycleOwner,{
            for (item in it){
                if (item.coin.id==args.coinId){
                    coinHolding=item
                    binding.coinNameTv.text = coinHolding.coin.name
                    binding.imageView.load(coinHolding.coin.icon)
                    setClickListener()


                    val chosenFiat = args.chosenFiat
                    coinsViewModel.getCoinsResponse().observe(viewLifecycleOwner){
                            response -> if (response.isSuccessful){
                        response.body()!!.coins.forEach { responseCoin ->
                            if (responseCoin.name==coinHolding.coin.name){
                                coin = responseCoin
                                binding.sellCoinInputPrice.setText((coin.price*chosenFiat.rate).toString())
                            }
                        }
                    }
                    }
                }
            }
        })


        return binding.root
    }

    private fun setClickListener(){
        binding.btnSellCoin.setOnClickListener {
            val inputPrice = binding.sellCoinInputPrice.text
            var price = inputPrice!!.split(" ")[0].toDoubleOrNull()
            val inputAmount = binding.sellCoinInputAmount.text
            var amount = inputAmount!!.split(" ")[0].toDoubleOrNull()
            if (amount==null||price==null){
                Toast.makeText(requireContext(),"Please input valid values", Toast.LENGTH_SHORT).show()
            }
            else{
                if (amount>coinHolding.amount){
                    Toast.makeText(requireContext(),"Insufficient funds", Toast.LENGTH_SHORT).show()
                }
                else{
                    amount *=-1
                    price /= args.chosenFiat.rate
                    val transaction = CoinTransaction(0, PersistentCoin(coin),price,
                        Calendar.getInstance().time,amount,args.chosenFiat)
                    holdingsViewModel.addCoinTx(transaction)
                    if ((coinHolding.amount+amount)<1*pow(10.0,-9.0)){
                        holdingsViewModel.deleteCoinHolding(coinHolding)
                    }
                    else{
                        holdingsViewModel.updateCoinHolding(CoinHolding(0, PersistentCoin(coin),amount))
                    }
                    Toast.makeText(requireContext(),"Transaction successful", Toast.LENGTH_SHORT).show()

                    val action = SellCoinDirections.actionSellCoinToPortfolio()
                    findNavController().navigate(action)

                }
            }
        }
    }

}