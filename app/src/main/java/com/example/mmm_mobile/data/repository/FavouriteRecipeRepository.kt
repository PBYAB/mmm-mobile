package com.example.mmm_mobile.data.repository

import androidx.annotation.WorkerThread
import com.example.mmm_mobile.models.RecipeFilter
import com.example.mmm_mobile.data.dao.FavouriteRecipeDao
import com.example.mmm_mobile.data.entity.FavouriteRecipe
import com.example.mmm_mobile.data.entity.RecipeIngredientCrossRef
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteRecipeRepository @Inject constructor(
    private val favouriteRecipeDao: FavouriteRecipeDao,
) {

    fun findAllFavouriteRecipes() = favouriteRecipeDao.getFavouriteRecipes()

    fun findAllFavouriteRecipesWithoutIngredients() =
        favouriteRecipeDao.getFavouriteRecipesWithoutIngredients()

    @WorkerThread
    suspend fun findRecipeById(recipeId: Long): FavouriteRecipe {
         return favouriteRecipeDao.getRecipeById(recipeId)
    }

    @WorkerThread
    suspend fun insertFavouriteRecipe(
        recipe: FavouriteRecipe,
        ingredients: List<RecipeIngredientCrossRef>,
    ): Long {
            val recipeId = favouriteRecipeDao.insertFavouriteRecipe(recipe)
            ingredients.forEach { ingredient ->
                val crossRef =
                    RecipeIngredientCrossRef(recipeId = recipeId,
                        ingredientId = ingredient.ingredientId,
                        amount = ingredient.amount,
                        unit = ingredient.unit
                        )
                favouriteRecipeDao.insertRecipeIngredientCrossRef(crossRef)
            }
            return recipeId
    }

    @WorkerThread
    suspend fun deleteFavouriteRecipe(recipeId: Long) {
            favouriteRecipeDao.deleteRecipeIngredientCrossRefs(recipeId)
            favouriteRecipeDao.deleteFavouriteRecipe(recipeId)
    }

    @WorkerThread
    suspend fun deleteRecipeIngredientCrossRefs(recipeId: Long) {
        favouriteRecipeDao.deleteRecipeIngredientCrossRefs(recipeId)
    }

    @WorkerThread
    suspend fun getFavouriteRecipesWithPaginationAndFilters(
        filter: RecipeFilter,
        limit: Int,
        offset: Int
    ): List<FavouriteRecipe> {
        return favouriteRecipeDao.getFavouriteRecipesWithPaginationAndFilters(
                filter.name,
                filter.servings,
                filter.minKcalPerServing,
                filter.maxKcalPerServing,
                filter.sortBy,
                filter.sortDirection,
                limit,
                offset
            )
    }

    companion object {

        @Volatile private var instance: FavouriteRecipeRepository? = null

        fun getInstance(favouriteRecipeDao: FavouriteRecipeDao) =
            instance ?: synchronized(this) {
                instance ?: FavouriteRecipeRepository(favouriteRecipeDao).also { instance = it }
            }
    }
}