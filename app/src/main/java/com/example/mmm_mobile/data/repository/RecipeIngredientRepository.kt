package com.example.mmm_mobile.data.repository

import androidx.annotation.WorkerThread
import com.example.mmm_mobile.data.dao.RecipeIngredientDao
import com.example.mmm_mobile.data.entity.Ingredient
import com.example.mmm_mobile.data.entity.IngredientWithAmountAndUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeIngredientRepository @Inject constructor(
    private val recipeIngredientDao: RecipeIngredientDao,
) {

    @WorkerThread
    suspend fun getIngredientsForRecipe(recipeId: Long): List<IngredientWithAmountAndUnit> {
        return recipeIngredientDao.getIngredientsForRecipe(recipeId)
    }

    @WorkerThread
    suspend fun insertIngredientIfNotExists(ingredient: Ingredient): Long {
        val existingIngredient = recipeIngredientDao.getIngredientByName(ingredient.name)
        return existingIngredient?.id ?: recipeIngredientDao.insertRecipeIngredient(
            ingredient
        )
    }

    suspend fun deleteOrphanRecipeIngredients() {
        recipeIngredientDao.deleteOrphanRecipeIngredients()
    }

    companion object {

        @Volatile private var instance: RecipeIngredientRepository? = null

        fun getInstance(recipeIngredientDao: RecipeIngredientDao) =
            instance ?: synchronized(this) {
                instance ?: RecipeIngredientRepository(recipeIngredientDao).also { instance = it }
            }
    }
}