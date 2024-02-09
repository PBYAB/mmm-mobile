package com.example.mmm_mobile.room.viewmodel

import FavouriteRecipeRepository
import RecipeIngredientRepository
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.entity.Ingredient
import com.example.mmm_mobile.room.entity.IngredientUnit
import com.example.mmm_mobile.room.entity.RecipeIngredientCrossRef
import com.example.mmm_mobile.room.entity.RecipeWithIngredients
import com.example.mmm_mobile.services.RecipeDownloadWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.models.RecipeDTO
import java.io.ByteArrayOutputStream

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

    fun findAllFavouriteRecipesWithoutIngredients() =
        favouriteRecipeRepository.findAllFavouriteRecipesWithoutIngredients()

    fun insertFavouriteRecipeWithIngredients(recipe: RecipeDTO): Long {
        var recipeId: Long = 0
        viewModelScope.launch {
            val ingredients = recipe.ingredients?.map { ingredient ->
                Ingredient(
                    id = ingredient.id,
                    name = ingredient.name
                )
            } ?: listOf()
            val ingredientIds = ingredients.map { ingredient ->
                recipeIngredientRepository.insertIngredientIfNotExists(ingredient)
            }

            val crossRefs = recipe.ingredients?.map { ingredient ->
                RecipeIngredientCrossRef(
                    recipeId = recipe.id,
                    ingredientId = ingredient.id,
                    amount = ingredient.amount ?: 0.0,
                    unit = IngredientUnit.valueOf(ingredient.unit?.name ?: "")
                )
            } ?: listOf()

            recipeId = favouriteRecipeRepository.insertFavouriteRecipe(
                FavouriteRecipe(
                    id = recipe.id,
                    name = recipe.name,
                    servings = recipe.servings ?: 0,
                    image = downloadImage(
                        getApplication(),
                        recipe.coverImageUrl ?: ""
                    ) ?: byteArrayOf(),
                    instructions = recipe.instructions ?: "",
                    kcalPerServing = recipe.kcalPerServing ?: 0.0,
                    totalTime = recipe.totalTime ?: 0,
                    rating = recipe.averageRating ?: 0.0
                ),
                crossRefs
            )
        }
        return recipeId
    }

    fun getFavouriteRecipeWithIngredients(recipeId: Long): LiveData<RecipeWithIngredients?> {
        val recipeWithIngredients = MutableLiveData<RecipeWithIngredients?>()
        viewModelScope.launch {
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
        viewModelScope.launch {
            favouriteRecipeRepository.deleteRecipeIngredientCrossRefs(recipeId)
            favouriteRecipeRepository.deleteFavouriteRecipe(recipeId)
            recipeIngredientRepository.deleteOrphanRecipeIngredients()
        }
    }


    fun fetchAndSaveRecipeInBackground(recipeId: Long, context: Context) {
        val data = Data.Builder()
            .putLong("recipe_id", recipeId)
            .build()

        val request = OneTimeWorkRequestBuilder<RecipeDownloadWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }

    private suspend fun downloadImage(context: Context, url: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            try {
                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get()

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.toByteArray()
            } catch (e: Exception) {
                null
            }
        }
    }
}