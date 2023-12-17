import android.app.Application
import android.util.Log
import com.example.mmm_mobile.dao.FavouriteRecipeDao
import com.example.mmm_mobile.db.RecipeDataBase
import com.example.mmm_mobile.entity.FavouriteRecipe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    fun findAllFavouriteRecipes(): Flow<List<FavouriteRecipe>> {
        return favouriteRecipeDao.getFavouriteRecipes()
    }

    suspend fun insert(favouriteRecipe: FavouriteRecipe) {
        withContext(defaultDispatcher) {
            favouriteRecipeDao.insertFavouriteRecipe(favouriteRecipe)
            Log.d("FavouriteRecipeRepository", "insert: $favouriteRecipe")
        }
    }

    suspend fun update(favouriteRecipe: FavouriteRecipe) {
        withContext(defaultDispatcher) {
            favouriteRecipeDao.updateFavouriteRecipe(favouriteRecipe)
            Log.d("FavouriteRecipeRepository", "update: $favouriteRecipe")
        }
    }

    suspend fun delete(favouriteRecipe: FavouriteRecipe) {
        withContext(defaultDispatcher) {
            favouriteRecipeDao.deleteFavouriteRecipe(favouriteRecipe.id)
            Log.d("FavouriteRecipeRepository", "delete: $favouriteRecipe")
        }
    }


}