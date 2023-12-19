package com.example.mmm_mobile.room.viewmodel

import FavouriteRecipeRepository
import RecipeIngredientRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.entity.RecipeIngredient
import kotlinx.coroutines.launch

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val favouriteRecipeRepository: FavouriteRecipeRepository
    private val recipeIngredientRepository: RecipeIngredientRepository

    init {
        favouriteRecipeRepository = FavouriteRecipeRepository(application)
        recipeIngredientRepository = RecipeIngredientRepository(application)
    }

    fun findAllFavouriteRecipes() = favouriteRecipeRepository.findAllFavouriteRecipes()

    fun findRecipeById(recipeId: Long): LiveData<FavouriteRecipe> {
        val result = MutableLiveData<FavouriteRecipe>()
        viewModelScope.launch {
            result.value = favouriteRecipeRepository.findRecipeById(recipeId)
        }
        return result
    }

    fun findRecipeIngredientsByRecipeId(recipeId: Long): LiveData<List<RecipeIngredient>> {
        val result = MutableLiveData<List<RecipeIngredient>>()
        viewModelScope.launch {
            result.value = recipeIngredientRepository.findRecipeIngredientsByRecipeId(recipeId)
        }
        return result
    }
}