package com.example.mmm_mobile.room.viewmodel

import FavouriteRecipeRepository
import RecipeIngredientRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.entity.IngredientUnit
import com.example.mmm_mobile.room.entity.RecipeIngredient
import kotlinx.coroutines.launch
import org.openapitools.client.models.Recipe
import org.openapitools.client.models.RecipeDTO
import java.util.Locale

class FavouriteRecipeViewModel(
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

    fun insertFavouriteRecipe(recipe : RecipeDTO) {
        viewModelScope.launch {
            Log.d("FavouriteRecipeViewModel", "insertFavouriteRecipe: $recipe")

            favouriteRecipeRepository.insertFavouriteRecipe(
                FavouriteRecipe(
                    id = recipe.id ?: 0,
                    name = recipe.name ?: "",
                    servings = recipe.servings ?: 0,
                    totalTime = recipe.totalTime ?: 0,
                    kcalPerServings = recipe.kcalPerServing ?: 0.0,
                    instructions = recipe.instructions ?: "",
                    image = recipe.coverImageUrl ?: ""
                )
            )

            recipe.ingredients?.forEach {
                recipeIngredientRepository.insertRecipeIngredient(
                    RecipeIngredient(
                        name = it.name ?: "",
                        amount = it.amount ?: 0.0,
                        unit = IngredientUnit.valueOf(it.unit?.name?.uppercase(Locale.ROOT) ?: "OTHER"),
                        recipeId = recipe.id ?: 0,
                        ingredientId = it.ingredientId ?: 0,
                        id = 0
                    )
                )
            }
        }
    }

    fun deleteFavouriteRecipe(recipeId: Long) {
        viewModelScope.launch {
            favouriteRecipeRepository.deleteFavouriteRecipe(recipeId)
            recipeIngredientRepository.deleteRecipeIngredientByRecipeId(recipeId)
        }
    }

}