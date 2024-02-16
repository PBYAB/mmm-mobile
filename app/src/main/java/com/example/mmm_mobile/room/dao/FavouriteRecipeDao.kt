package com.example.mmm_mobile.room.dao

import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
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
import org.jetbrains.annotations.NotNull

@Dao
interface FavouriteRecipeDao {

    @Transaction
    @Query("SELECT * FROM favourite_recipe")
    fun getFavouriteRecipes(): LiveData<List<RecipeWithIngredients>>

    @Query("SELECT * FROM favourite_recipe")
    fun getFavouriteRecipesWithoutIngredients(): LiveData<List<FavouriteRecipe>>

    @WorkerThread
    @Query("""
    SELECT * FROM favourite_recipe 
    WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
    AND (servings IN (:servings) OR :servings IS NULL)
    AND (:minKcalPerServing IS NULL OR kcalPerServing >= :minKcalPerServing)
    AND (:maxKcalPerServing IS NULL OR kcalPerServing <= :maxKcalPerServing)
    ORDER BY CASE WHEN :sortBy = 'name' THEN name END ASC,
             CASE WHEN :sortBy = 'name' AND :sortDirection = 'DESC' THEN name END DESC,
             CASE WHEN :sortBy = 'servings' THEN servings END ASC,
             CASE WHEN :sortBy = 'servings' AND :sortDirection = 'DESC' THEN servings END DESC,
             CASE WHEN :sortBy = 'kcalPerServing' THEN kcalPerServing END ASC,
             CASE WHEN :sortBy = 'kcalPerServing' AND :sortDirection = 'DESC' THEN kcalPerServing END DESC
    LIMIT :limit OFFSET :offset
""")
    suspend fun getFavouriteRecipesWithPaginationAndFilters(
        name: String?,
        servings: List<Int>?,
        minKcalPerServing: Double?,
        maxKcalPerServing: Double?,
        sortBy: String?,
        sortDirection: String?,
        limit: Int,
        offset: Int
    ): List<FavouriteRecipe>



    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeIngredientCrossRef(crossRef: RecipeIngredientCrossRef): Long

    @WorkerThread
    @Update
    suspend fun updateFavouriteRecipe(favouriteRecipe: FavouriteRecipe)

    @WorkerThread
    @Query("DELETE FROM RecipeIngredientCrossRef WHERE recipeId = :recipeId")
    suspend fun deleteRecipeIngredientCrossRefs( recipeId: Long)

    @WorkerThread
    @Query("SELECT * FROM favourite_recipe WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Long): FavouriteRecipe

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteRecipe(favouriteRecipe: FavouriteRecipe): Long

    @WorkerThread
    @Query("DELETE FROM favourite_recipe WHERE id = :recipeId")
    suspend fun deleteFavouriteRecipe(recipeId: Long)
}