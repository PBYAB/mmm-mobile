import android.app.Application
import androidx.annotation.WorkerThread
import com.example.mmm_mobile.models.RecipeFilter
import com.example.mmm_mobile.room.dao.FavouriteRecipeDao
import com.example.mmm_mobile.room.db.RecipeDataBase
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.entity.Ingredient
import com.example.mmm_mobile.room.entity.RecipeIngredientCrossRef
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class FavouriteRecipeRepository(
    application: Application,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val favouriteRecipeDao: FavouriteRecipeDao

    init {
        val recipeDataBase = RecipeDataBase.getDatabase(application)
        favouriteRecipeDao = recipeDataBase.favouriteRecipeDao()
    }

    fun findAllFavouriteRecipes() = favouriteRecipeDao.getFavouriteRecipes()

    fun findAllFavouriteRecipesWithoutIngredients() =
        favouriteRecipeDao.getFavouriteRecipesWithoutIngredients()

    @WorkerThread
    suspend fun findRecipeById(recipeId: Long): FavouriteRecipe {
        return withContext(defaultDispatcher) {
            favouriteRecipeDao.getRecipeById(recipeId)
        }
    }

    @WorkerThread
    suspend fun insertFavouriteRecipe(
        recipe: FavouriteRecipe,
        ingredients: List<RecipeIngredientCrossRef>,
    ): Long {
        return withContext(defaultDispatcher) {
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
            recipeId
        }
    }

    @WorkerThread
    suspend fun deleteFavouriteRecipe(recipeId: Long) {
        withContext(defaultDispatcher) {
            favouriteRecipeDao.deleteRecipeIngredientCrossRefs(recipeId)
            favouriteRecipeDao.deleteFavouriteRecipe(recipeId)
        }
    }

    @WorkerThread
    suspend fun insertRecipeIngredientCrossRef(crossRef: RecipeIngredientCrossRef): Long {
        return withContext(defaultDispatcher) {
            favouriteRecipeDao.insertRecipeIngredientCrossRef(crossRef)
        }
    }

    @WorkerThread
    suspend fun deleteRecipeIngredientCrossRefs(recipeId: Long) {
        withContext(defaultDispatcher) {
            favouriteRecipeDao.deleteRecipeIngredientCrossRefs(recipeId)
        }
    }

    @WorkerThread
    suspend fun updateFavouriteRecipe(recipe: FavouriteRecipe) {
        withContext(defaultDispatcher) {
            favouriteRecipeDao.updateFavouriteRecipe(recipe)
        }
    }

    @WorkerThread
    suspend fun getFavouriteRecipesWithPaginationAndFilters(
        filter: RecipeFilter,
        limit: Int,
        offset: Int
    ): List<FavouriteRecipe> {
        return withContext(defaultDispatcher) {
            favouriteRecipeDao.getFavouriteRecipesWithPaginationAndFilters(
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
    }
}