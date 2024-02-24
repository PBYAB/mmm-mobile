package com.example.mmm_mobile.viewmodel

import com.example.mmm_mobile.data.repository.FavouriteRecipeRepository
import com.example.mmm_mobile.data.repository.RecipeIngredientRepository
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mmm_mobile.data.entity.RecipeWithIngredients
import com.example.mmm_mobile.workers.RecipeDownloadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteRecipeDetailsViewModel @Inject constructor(
    private val favouriteRecipeRepository: FavouriteRecipeRepository,
    private val recipeIngredientRepository: RecipeIngredientRepository,
) : ViewModel() {


    fun getFavouriteRecipeWithIngredients(recipeId: Long): LiveData<RecipeWithIngredients?> {
        val recipeWithIngredients = MutableLiveData<RecipeWithIngredients?>()
        viewModelScope.launch(Dispatchers.IO) {
            val recipe = favouriteRecipeRepository.findRecipeById(recipeId)
            if (recipe != null) {
                val ingredients = recipeIngredientRepository.getIngredientsForRecipe(recipeId)
                recipeWithIngredients.postValue(RecipeWithIngredients(recipe, ingredients))
            } else {
                recipeWithIngredients.postValue(null)
            }
        }
        return recipeWithIngredients
    }

    fun deleteFavouriteRecipeWithIngredients(recipeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteRecipeRepository.deleteRecipeIngredientCrossRefs(recipeId)
            favouriteRecipeRepository.deleteFavouriteRecipe(recipeId)
            recipeIngredientRepository.deleteOrphanRecipeIngredients()
        }
    }


    fun fetchAndSaveRecipeInBackground(recipeId: Long, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = Data.Builder()
                .putLong("recipe_id", recipeId)
                .build()

            val request = OneTimeWorkRequestBuilder<RecipeDownloadWorker>()
                .setInputData(data)
                .build()

            WorkManager.getInstance(context).enqueue(request)
        }
    }
}