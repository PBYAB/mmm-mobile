package com.example.mmm_mobile.services

import android.app.Application
import android.content.Context
import androidx.annotation.WorkerThread
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mmm_mobile.room.viewmodel.FavouriteRecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.RecipeApi
import org.openapitools.client.models.RecipeDTO

class RecipeDownloadWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    @WorkerThread
    override suspend fun doWork(): Result {
        val recipeId = inputData.getLong("recipe_id", 0L)
        if (recipeId != 0L) {
            val recipe = fetchRecipeById(recipeId)
            if (recipe != null) {
                val favouriteRecipeViewModel = FavouriteRecipeViewModel(applicationContext as Application)
                favouriteRecipeViewModel.insertFavouriteRecipeWithIngredients(recipe)
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
}

