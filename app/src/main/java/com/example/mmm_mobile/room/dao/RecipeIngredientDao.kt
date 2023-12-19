package com.example.mmm_mobile.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mmm_mobile.room.entity.RecipeIngredient

@Dao
interface RecipeIngredientDao {

    @Query("SELECT * FROM recipe_ingredient")
    fun getAll(): LiveData<List<RecipeIngredient>>

    @Query("SELECT * FROM recipe_ingredient WHERE recipeId = :recipeId")
    suspend fun getIngredientsByRecipeId(recipeId: Long): List<RecipeIngredient>

    @Insert
    suspend fun insertAll(vararg recipeIngredient: RecipeIngredient)

    @Query("DELETE FROM recipe_ingredient WHERE recipeId = :recipeId")
    suspend fun deleteIngredientsByRecipeId(recipeId: Long)

    @Update
    suspend fun updateRecipeIngredient(recipeIngredient: RecipeIngredient)

}