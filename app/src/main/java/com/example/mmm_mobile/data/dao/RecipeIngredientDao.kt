package com.example.mmm_mobile.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import androidx.room.Update
import com.example.mmm_mobile.data.entity.Ingredient
import com.example.mmm_mobile.data.entity.IngredientWithAmountAndUnit

@Dao
interface RecipeIngredientDao {

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM recipe_ingredient INNER JOIN RecipeIngredientCrossRef ON recipe_ingredient.id = RecipeIngredientCrossRef.ingredientId WHERE RecipeIngredientCrossRef.recipeId = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Long): List<IngredientWithAmountAndUnit>


    @Update
    suspend fun updateRecipeIngredient(ingredient: Ingredient)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeIngredient(ingredient: Ingredient): Long

    @Query("DELETE FROM recipe_ingredient WHERE id NOT IN (SELECT ingredientId FROM RecipeIngredientCrossRef)")
    suspend fun deleteOrphanRecipeIngredients()

    @Query("SELECT * FROM recipe_ingredient WHERE name = :name")
    suspend fun getIngredientByName(name: String): Ingredient?
}