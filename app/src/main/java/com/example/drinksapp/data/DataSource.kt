package com.example.drinksapp.data

import com.example.drinksapp.DrinksAppDatabase
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinksEntity
import com.example.drinksapp.valueobject.Resource
import com.example.drinksapp.valueobject.RetrofitClient

class DataSource(private val drinksAppDatabase: DrinksAppDatabase) {

    suspend fun getDrinksByName(drinkName: String): Resource<List<Drink>>{
        return  Resource.Success(RetrofitClient.webService.getDrinksByName(drinkName).drinkList)
    }

    suspend fun insertDrinkintoRoom(drink: DrinksEntity){
        drinksAppDatabase.drinksDao().insertFavorite(drink)
    }

    suspend fun getFavoriteDrinks(): Resource<List<DrinksEntity>> {
        return Resource.Success(drinksAppDatabase.drinksDao().getAllFavoriteDrinks())
    }

    suspend fun deleteDrinkfromRoom(drink: DrinksEntity) {
        drinksAppDatabase.drinksDao().deleteDrink(drink)
    }
}