import android.app.Application
import com.example.mmm_mobile.room.dao.FavouriteRecipeDao
import com.example.mmm_mobile.room.db.RecipeDataBase
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.entity.RecipeIngredientCrossRef
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    suspend fun findRecipeById(recipeId: Long): FavouriteRecipe {
        return withContext(defaultDispatcher) {
            favouriteRecipeDao.getRecipeById(recipeId)
        }
    }

    suspend fun insertFavouriteRecipe(recipe: FavouriteRecipe) : Long {
        return withContext(defaultDispatcher) {
            favouriteRecipeDao.insertFavouriteRecipe(recipe)
        }
    }

    suspend fun deleteFavouriteRecipe(recipeId: Long) {
        withContext(defaultDispatcher) {
            favouriteRecipeDao.deleteFavouriteRecipe(recipeId)
        }
    }

    suspend fun insertRecipeIngredientCrossRef(crossRef: RecipeIngredientCrossRef): Long {
        return withContext(defaultDispatcher) {
            favouriteRecipeDao.insertRecipeIngredientCrossRef(crossRef)
        }
    }

    suspend fun deleteRecipeIngredientCrossRefs(recipeId: Long) {
        withContext(defaultDispatcher) {
            favouriteRecipeDao.deleteRecipeIngredientCrossRefs(recipeId)
        }
    }
}