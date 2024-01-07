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
import com.bumptech.glide.Glide
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.entity.IngredientUnit
import com.example.mmm_mobile.room.entity.Ingredient
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


    fun insertFavouriteRecipeWithIngredients(recipe: RecipeDTO) : Long {
        var recipeId: Long = 0
        viewModelScope.launch {

            recipeId = favouriteRecipeRepository.insertFavouriteRecipe(
                    FavouriteRecipe(
                        id = recipe.id ?: 0,
                        name = recipe.name ?: "",
                        servings = recipe.servings ?: 0,
                        image = downloadImage(
                            getApplication(),
                            recipe.coverImageUrl ?: ""
                            ) ?: byteArrayOf(),
                        instructions = recipe.instructions ?: "",
                        kcalPerServing = recipe.kcalPerServing ?: 0.0,
                        totalTime = recipe.totalTime ?: 0,
                        ingredients = recipe.ingredients?.map { ingredient ->
                            Ingredient(
                                name = ingredient.name ?: "",
                                amount = ingredient.amount ?: 0.0,
                                unit = IngredientUnit.valueOf(ingredient.unit?.value?.uppercase() ?: "OTHER"),
                            ) } ?: emptyList()
                    )
            )
        }
        return recipeId
    }

    fun getFavouriteRecipeWithIngredients(recipeId: Long): LiveData<FavouriteRecipe> {
        val result = MutableLiveData<FavouriteRecipe>()
        viewModelScope.launch {
            val recipe = favouriteRecipeRepository.findRecipeById(recipeId)
           result.value = recipe
        }
        return result
    }

    fun deleteFavouriteRecipeWithIngredients(recipeId: Long) {
        viewModelScope.launch {
            favouriteRecipeRepository.deleteRecipeIngredientCrossRefs(recipeId)
            favouriteRecipeRepository.deleteFavouriteRecipe(recipeId)
            recipeIngredientRepository.deleteOrphanRecipeIngredients()
        }
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