package com.example.mmm_mobile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mmm_mobile.entity.RecipeIngredient
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeIngredientDao {

    @Query("SELECT * FROM recipe_ingredient")
    fun getAll(): Flow<List<RecipeIngredient>>

    @Query("SELECT * FROM recipe_ingredient WHERE recipeId = :recipeId")
    fun getIngredientsByRecipeId(recipeId: Long): List<RecipeIngredient>


    @Insert
    fun insertAll(vararg recipeIngredient: RecipeIngredient)

    @Query("DELETE FROM recipe_ingredient WHERE recipeId = :recipeId")
    fun deleteIngredientsByRecipeId(recipeId: Long)

    @Update
    fun updateRecipeIngredient(recipeIngredient: RecipeIngredient)

}