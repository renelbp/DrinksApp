package com.example.drinksapp.domain

import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinksEntity
import com.example.drinksapp.valueobject.Resource

interface Repository {
    //GET DRINKS LIS FROM API BY NAME/TYPE
    suspend fun getDrinksList(drinkName: String): Resource<List<Drink>>
//GET FAVORITE DRIKS LIST FROM DB
    suspend fun getFavoriteDrinks(): Resource<List<DrinksEntity>>
    //SAVE DRINK TO DB
    suspend fun saveDrinkToFavs(drink: DrinksEntity)
    // DELETE DRINK
    suspend fun deleteDrinkfromFavs(drink: DrinksEntity)
}