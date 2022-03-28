package com.example.drinksapp.domain

import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinkList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("search.php?")
    suspend fun getDrinksByName(@Query("s") drinkName: String): DrinkList


}