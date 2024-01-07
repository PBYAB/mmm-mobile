package com.example.mmm_mobile.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.entity.Ingredient
import com.example.mmm_mobile.room.entity.RecipeIngredientCrossRef

@Dao
interface FavouriteRecipeDao {

    @Query("SELECT * FROM favourite_recipe")
    fun getFavouriteRecipes():  LiveData<List<FavouriteRecipe>>

//    @Transaction
//    @Query("""
//        SELECT recipe_ingredient.* FROM recipe_ingredient
//        INNER JOIN RecipeIngredientCrossRef ON recipe_ingredient.id = RecipeIngredientCrossRef.ingredientId
//        WHERE RecipeIngredientCrossRef.recipeId = :recipeId
//    """)
//    suspend fun getIngredientsForRecipe(recipeId: Long): List<Ingredient>

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