package com.example.mmm_mobile.viewmodel

import FavouriteRecipeRepository
import RecipeIngredientRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mmm_mobile.entity.FavouriteRecipe
import com.example.mmm_mobile.entity.RecipeIngredient
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

    fun insertFavouriteRecipe(favouriteRecipe: FavouriteRecipe) {
        viewModelScope.launch {
            favouriteRecipeRepository.insert(favouriteRecipe)
        }
        Log.d("RecipeViewModel", "insertFavouriteRecipe: $favouriteRecipe")
    }

    fun insertRecipeIngredient(recipeIngredient: RecipeIngredient) {
        viewModelScope.launch {
            recipeIngredientRepository.insert(recipeIngredient)
        }
        Log.d("RecipeViewModel", "insertRecipeIngredient: $recipeIngredient")
    }

    fun updateRecipeIngredient(recipeIngredient: RecipeIngredient) {
        viewModelScope.launch {
            recipeIngredientRepository.update(recipeIngredient)
        }
        Log.d("RecipeViewModel", "updateRecipeIngredient: $recipeIngredient")
    }

    fun deleteRecipeIngredient(recipeIngredient: RecipeIngredient) {
        viewModelScope.launch {
            recipeIngredientRepository.delete(recipeIngredient)
        }
        Log.d("RecipeViewModel", "deleteRecipeIngredient: $recipeIngredient")
    }


    fun deleteFavouriteRecipe(favouriteRecipe: FavouriteRecipe) {
        viewModelScope.launch {
            favouriteRecipeRepository.delete(favouriteRecipe)
        }
        Log.d("RecipeViewModel", "deleteFavouriteRecipe: $favouriteRecipe")
    }

}