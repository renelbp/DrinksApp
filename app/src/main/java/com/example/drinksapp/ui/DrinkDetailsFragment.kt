package com.example.drinksapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.drinksapp.data.DataSource
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinksEntity
import com.example.drinksapp.databinding.FragmentDrinkDetailsBinding
import com.example.drinksapp.databinding.FragmentMainBinding
import com.example.drinksapp.domain.RepoImplement
import com.example.drinksapp.ui.viewmodel.MainViewModel
import com.example.drinksapp.ui.viewmodel.VMFactory


class DrinkDetails : Fragment() {
    private lateinit var binding: FragmentDrinkDetailsBinding
    private val viewModel by viewModels<MainViewModel>{
        VMFactory(RepoImplement(DataSource(DrinksAppDatabase.getDatabase(requireActivity().applicationContext))))
    }

    private lateinit var  drink: Drink

      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          requireArguments().let {
              drink = it.getParcelable<Drink>("drink")!!
              Log.d("DETALLES_FRAG","$drink ")
          }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDrinkDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(drink.image).centerCrop().into(binding.ivDrinks)
        binding.tvDrinkTitle.text = drink.name
        binding.tvDrinkDetails.text = drink.description
        if(drink.hasAlcohol =="Non_Alcoholic"){
            binding.tvHasAlcohol.text = "Non Alcoholic Drink"
        }else  binding.tvHasAlcohol.text = "Alcoholic Drink"

        binding.btnSaveDrink.setOnClickListener {
            viewModel.saveDrink(DrinksEntity(drink.drinkId,drink.name,drink.description,drink.image,drink.hasAlcohol))
            Toast.makeText(requireContext(),"Drink ${drink.name} Succesfully Saved",Toast.LENGTH_SHORT).show()
        }

        binding.btnDeleteDrink.setOnClickListener {
            val drinkFav= DrinksEntity(drink.drinkId,drink.name,drink.description,drink.image,drink.hasAlcohol)
            viewModel.deleteDrink(drinkFav)
            findNavController().navigate(R.id.drinkDetails_to_favoritesFragment)
            Toast.makeText(requireContext(),"Drink ${drinkFav .name} Succesfully Deleted",Toast.LENGTH_SHORT).show()

        }
    }

}