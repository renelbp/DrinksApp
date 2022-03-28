package com.example.drinksapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinksapp.DrinksAppDatabase
import com.example.drinksapp.R
import com.example.drinksapp.data.DataSource
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.databinding.FragmentFavoritesBinding
import com.example.drinksapp.domain.RepoImplement
import com.example.drinksapp.ui.viewmodel.MainViewModel
import com.example.drinksapp.ui.viewmodel.VMFactory


class FavoritesFragment : Fragment(), RVDrinksAdapter.OnDrinkClickListener {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModels<MainViewModel> {
        VMFactory(RepoImplement(DataSource(DrinksAppDatabase.getDatabase(requireActivity().applicationContext))))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewSetup()
        observersSetup()


    }

    private fun recyclerViewSetup(){
        binding.rvFavs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavs.addItemDecoration(DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL))
    }

    private fun observersSetup(){
        viewModel.getFavoriteDrinks().observe(viewLifecycleOwner, Observer {result ->
            when(result){
                is com.example.drinksapp.valueobject.Resource.Loading ->{
                }
                is com.example.drinksapp.valueobject.Resource.Success ->{
                   /*
                   val list= result.data
                    val listfav = mutableListOf<Drink>()
                    for (drink in list){
                        listfav.add(Drink(drink.drinkId,drink.name,drink.description,drink.image,drink.hasAlcohol))
                    }*/
                    val listfav= result.data.map {
                        Drink(it.drinkId,it.name,it.description,it.image,it.hasAlcohol)
                    }

                    binding.rvFavs.adapter = RVDrinksAdapter(requireContext(),listfav,this)
                    binding.progBar.visibility = View.GONE
                    Log.d("FAVS_LIST","${result.data}")
                }
                is com.example.drinksapp.valueobject.Resource.Failure ->{
                    Toast.makeText(requireContext(),"Error during showing ${result.exception}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDrinkClick(drink: Drink) {
        val bundle = Bundle()
        bundle.putParcelable("drink",drink)
        findNavController().navigate(R.id.favoritesFragment_to_DetailsFragment,bundle)
    }
}