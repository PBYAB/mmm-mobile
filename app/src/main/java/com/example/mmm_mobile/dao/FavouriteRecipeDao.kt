package com.example.mmm_mobile.dao

import androidx.room.*
import com.example.mmm_mobile.entity.FavouriteRecipe
import com.example.mmm_mobile.entity.RecipeIngredient
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteRecipeDao {

    @Query("SELECT * FROM favourite_recipe")
    fun getFavouriteRecipes(): Flow<List<FavouriteRecipe>>

    @Query("SELECT * FROM recipe_ingredient WHERE recipeId = :recipeId")
    fun getIngredientsForRecipe(recipeId: Long): Flow<List<RecipeIngredient>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteRecipe(favouriteRecipe: FavouriteRecipe)

    @Update
    suspend fun updateFavouriteRecipe(favouriteRecipe: FavouriteRecipe)

    @Query("DELETE FROM favourite_recipe WHERE id = :recipeId")
    suspend fun deleteFavouriteRecipe(recipeId: Long)
}