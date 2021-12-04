package com.example.cryptocurrency.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentChooseCurrencyDialogBinding
import com.example.cryptocurrency.model.Fiat
import com.example.cryptocurrency.viewmodels.CoinsViewModel


class ChooseCurrencyDialog : DialogFragment() {
    private lateinit var binding: FragmentChooseCurrencyDialogBinding
    private val coinsViewModel:CoinsViewModel by activityViewModels()
    private var fiatList:List<Fiat> = emptyList()
    private var fiatStringList:ArrayList<String> = ArrayList()
    private lateinit var arrayAdapter:ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseCurrencyDialogBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_menu_item,fiatStringList)
        binding.currencyAcTv.setAdapter(arrayAdapter)
        initButtons()
        coinsViewModel.getFiatsResponse().observe(viewLifecycleOwner,{
            if (it.isSuccessful){
                if (it.body()!=null)
                {
                    fiatList= it.body()!!
                    for (fiat in fiatList){
                        fiatStringList.add(fiat.name)
                    }
                    arrayAdapter.notifyDataSetChanged()

                }
            }
        })
    }

    private fun initButtons(){
        val action = ChooseCurrencyDialogDirections.actionChooseCurrencyDialogToWallet()
        binding.currencyChooseBtn.setOnClickListener {
            coinsViewModel.chooseFiat(arrayAdapter.getPosition(binding.currencyAcTv.text.toString()))
            findNavController().navigate(action)
        }
        binding.currencyCancelBtn.setOnClickListener {
            findNavController().navigate(action)
        }
    }

}