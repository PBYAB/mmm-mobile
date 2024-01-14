package com.example.mmm_mobile.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mmm_mobile.room.entity.Ingredient

@Dao
interface RecipeIngredientDao {

    @Query("SELECT * FROM recipe_ingredient INNER JOIN RecipeIngredientCrossRef ON recipe_ingredient.id = RecipeIngredientCrossRef.ingredientId WHERE RecipeIngredientCrossRef.recipeId = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Long): List<Ingredient>

    @Insert
    suspend fun insertAll(vararg ingredient: Ingredient): List<Long>

    @Update
    suspend fun updateRecipeIngredient(ingredient: Ingredient)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeIngredient(ingredient: Ingredient): Long

    @Query("DELETE FROM recipe_ingredient WHERE id NOT IN (SELECT ingredientId FROM RecipeIngredientCrossRef)")
    suspend fun deleteOrphanRecipeIngredients()

    @Query("SELECT * FROM recipe_ingredient WHERE id = :ingredientId")
    suspend fun getIngredientById(ingredientId: Long): Ingredient?

    @Query("SELECT * FROM recipe_ingredient")
    fun getIngredients(): List<Ingredient>
}