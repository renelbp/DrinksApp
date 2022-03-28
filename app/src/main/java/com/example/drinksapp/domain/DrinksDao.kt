package com.example.drinksapp.domain

import androidx.room.*
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinksEntity

@Dao
interface DrinksDao {

   @Query("SELECT * FROM DrinksEntity")
   suspend fun getAllFavoriteDrinks():List<DrinksEntity>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertFavorite(drink: DrinksEntity)

   @Delete
   suspend fun deleteDrink(drink: DrinksEntity)


}