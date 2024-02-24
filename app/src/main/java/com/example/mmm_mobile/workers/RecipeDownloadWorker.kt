package com.example.mmm_mobile.workers

import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mmm_mobile.data.repository.FavouriteRecipeRepository
import com.example.mmm_mobile.data.repository.RecipeIngredientRepository
import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.RequiresPermission
import androidx.annotation.WorkerThread
import com.bumptech.glide.Glide
import com.example.mmm_mobile.data.entity.FavouriteRecipe
import com.example.mmm_mobile.data.entity.Ingredient
import com.example.mmm_mobile.data.entity.RecipeIngredientCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.RecipeApi
import org.openapitools.client.models.RecipeDTO
import com.example.mmm_mobile.models.IngredientUnit
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.ByteArrayOutputStream

@HiltWorker
class RecipeDownloadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val favouriteRecipeRepository: FavouriteRecipeRepository,
    private val recipeIngredientRepository: RecipeIngredientRepository
) : CoroutineWorker(appContext, workerParams) {

    @WorkerThread
    override suspend fun doWork(): Result {
        val recipeId = inputData.getLong("recipe_id", 0L)
        if (recipeId != 0L) {
            val recipe = fetchRecipeById(recipeId)
            if (recipe != null) {
                insertFavouriteRecipeWithIngredients(recipe,applicationContext)
                return Result.success()
            }
        }
        return Result.failure()
    }

    @WorkerThread
    private suspend fun fetchRecipeById(id: Long): RecipeDTO? {
        val recipeApi = RecipeApi()
        return withContext(Dispatchers.IO) {
            try {
                recipeApi.getById(id)
            } catch (e: Exception) {
                null
            }
        }
    }

    private suspend fun insertFavouriteRecipeWithIngredients(recipe: RecipeDTO, context: Context): Long {
        val ingredients = recipe.ingredients?.map { ingredient ->
            Ingredient(
                id = ingredient.id,
                name = ingredient.name
            )
        } ?: listOf()
        ingredients.forEach { ingredient ->
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

        return favouriteRecipeRepository.insertFavouriteRecipe(
            FavouriteRecipe(
                id = recipe.id,
                name = recipe.name,
                servings = recipe.servings,
                image = downloadImage(
                    context,
                    recipe.coverImageUrl ?: ""
                ) ?: byteArrayOf(),
                instructions = recipe.instructions ?: "",
                kcalPerServing = recipe.kcalPerServing,
                totalTime = recipe.totalTime,
                rating = recipe.averageRating
            ),
            crossRefs
        )
    }

    @RequiresPermission(android.Manifest.permission.INTERNET)
    @WorkerThread
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

