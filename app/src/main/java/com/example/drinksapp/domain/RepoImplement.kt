package com.example.drinksapp.domain

import com.example.drinksapp.data.DataSource
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinksEntity
import com.example.drinksapp.valueobject.Resource

class RepoImplement(private val dataSource: DataSource): Repository {
    override suspend fun getDrinksList(drinkName: String): Resource<List<Drink>> {
        return  dataSource.getDrinksByName(drinkName)
    }

    override suspend fun getFavoriteDrinks(): Resource<List<DrinksEntity>> {
        return dataSource.getFavoriteDrinks()
    }

    override suspend fun saveDrinkToFavs(drink: DrinksEntity) {
        dataSource.insertDrinkintoRoom(drink)
    }

    override suspend fun deleteDrinkfromFavs(drink: DrinksEntity) {
        dataSource.deleteDrinkfromRoom(drink)
    }
}