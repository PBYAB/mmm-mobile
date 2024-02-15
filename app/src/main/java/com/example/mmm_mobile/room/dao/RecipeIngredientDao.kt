package com.example.mmm_mobile.room.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.mmm_mobile.room.entity.Ingredient
import com.example.mmm_mobile.room.entity.IngredientWithAmountAndUnit

@Dao
interface RecipeIngredientDao {

    @Transaction
    @WorkerThread
    @Query("SELECT * FROM recipe_ingredient INNER JOIN RecipeIngredientCrossRef ON recipe_ingredient.id = RecipeIngredientCrossRef.ingredientId WHERE RecipeIngredientCrossRef.recipeId = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Long): List<IngredientWithAmountAndUnit>

    @WorkerThread
    @Insert
    suspend fun insertAll(vararg ingredient: Ingredient): List<Long>

    @WorkerThread
    @Update
    suspend fun updateRecipeIngredient(ingredient: Ingredient)

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeIngredient(ingredient: Ingredient): Long

    @WorkerThread
    @Query("DELETE FROM recipe_ingredient WHERE id NOT IN (SELECT ingredientId FROM RecipeIngredientCrossRef)")
    suspend fun deleteOrphanRecipeIngredients()

    @WorkerThread
    @Query("SELECT * FROM recipe_ingredient WHERE name = :name")
    suspend fun getIngredientByName(name: String): Ingredient?

    @WorkerThread
    @Query("SELECT * FROM recipe_ingredient")
    fun getIngredients(): List<Ingredient>
}