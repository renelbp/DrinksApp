package com.example.drinksapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.drinksapp.data.model.Drink
import com.example.drinksapp.data.model.DrinksEntity
import com.example.drinksapp.domain.Repository
import com.example.drinksapp.valueobject.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repository) : ViewModel() {

    private val drinksData = MutableLiveData<String>()

    fun setDrink(drinkName: String) {
        drinksData.value = drinkName
    }

    init {
        setDrink("margarita")
    }

    val fetchDrinksList = drinksData.distinctUntilChanged().switchMap { drinkName ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getDrinksList(drinkName))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun saveDrink(drink: DrinksEntity){
        viewModelScope.launch {
            repo.saveDrinkToFavs(drink)
        }
    }

    fun getFavoriteDrinks() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getFavoriteDrinks())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun deleteDrink(drink: DrinksEntity) {
        viewModelScope.launch {
            repo.deleteDrinkfromFavs(drink)
        }
    }
}