package com.example.mmm_mobile.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.entity.RecipeIngredientCrossRef
import com.example.mmm_mobile.room.entity.RecipeWithIngredients

@Dao
interface FavouriteRecipeDao {

    @Transaction
    @Query("SELECT * FROM favourite_recipe")
    fun getFavouriteRecipes(): LiveData<List<RecipeWithIngredients>>

    @Query("SELECT * FROM favourite_recipe")
    fun getFavouriteRecipesWithoutIngredients(): LiveData<List<FavouriteRecipe>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeIngredientCrossRef(crossRef: RecipeIngredientCrossRef): Long

    @Update
    suspend fun updateFavouriteRecipe(favouriteRecipe: FavouriteRecipe)

    @Query("DELETE FROM RecipeIngredientCrossRef WHERE recipeId = :recipeId")
    suspend fun deleteRecipeIngredientCrossRefs(recipeId: Long)

    @Query("SELECT * FROM favourite_recipe WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Long): FavouriteRecipe

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteRecipe(favouriteRecipe: FavouriteRecipe): Long

    @Query("DELETE FROM favourite_recipe WHERE id = :recipeId")
    suspend fun deleteFavouriteRecipe(recipeId: Long)
}