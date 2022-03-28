package com.example.drinksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinksapp.DrinksAppDatabase
import com.example.drinksapp.R
import com.example.drinksapp.data.DataSource
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.databinding.FragmentMainBinding
import com.example.drinksapp.domain.RepoImplement
import com.example.drinksapp.ui.viewmodel.MainViewModel
import com.example.drinksapp.ui.viewmodel.VMFactory


class MainFragment : Fragment(), RVDrinksAdapter.OnDrinkClickListener {
    private val viewModel by viewModels<MainViewModel>{
        VMFactory(RepoImplement(DataSource(DrinksAppDatabase.getDatabase(requireActivity().applicationContext))))
    }

private lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewSetup()
        searchViewSetup()
        observersSetup()

    }


        private fun observersSetup(){
            viewModel.fetchDrinksList.observe(viewLifecycleOwner, Observer {result ->
                when(result){
                    is com.example.drinksapp.valueobject.Resource.Loading ->{
                        binding.progBar.visibility = View.VISIBLE
                    }
                    is com.example.drinksapp.valueobject.Resource.Success ->{
                        binding.rvDrinks.adapter = RVDrinksAdapter(requireContext(),result.data,this)
                        binding.progBar.visibility = View.GONE
                    }
                    is com.example.drinksapp.valueobject.Resource.Failure ->{
                        binding.progBar.visibility = View.GONE
                        Toast.makeText(requireContext(),"Error during download ${result.exception}",Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }



    private fun searchViewSetup(){
        binding.searchview.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setDrink(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun recyclerViewSetup(){
        binding.rvDrinks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDrinks.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
    }
    override fun onDrinkClick(drink: Drink) {
        val bundle = Bundle()
        bundle.putParcelable("drink",drink)
        findNavController().navigate(R.id.mainFragment_to_drinkDetails,bundle)
    }


}