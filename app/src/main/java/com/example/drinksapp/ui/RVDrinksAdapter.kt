package com.example.drinksapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.databinding.DrinksItemBinding

class RVDrinksAdapter(private val context: Context, private val drinksList: List<Drink>,
private val itemClickListener: OnDrinkClickListener) :
    RecyclerView.Adapter<RVDrinksAdapter.MainViewHolder>() {

    interface OnDrinkClickListener{
        fun onDrinkClick(drink: Drink)
    }

    inner class MainViewHolder(val binding: DrinksItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = DrinksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        with(holder) {
            with(drinksList[position]) {
                binding.tvName.text = this.name
                binding.tvDescription.text = this.description
                Glide.with(context).load(image).centerCrop().into(binding.imvDrink)
                itemView.setOnClickListener{itemClickListener.onDrinkClick(this)}
            }
        }
    }

    override fun getItemCount(): Int {
        return drinksList.size
    }


}