import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mmm_mobile.room.dao.FavouriteRecipeDao
import com.example.mmm_mobile.room.db.RecipeDataBase
import com.example.mmm_mobile.room.entity.FavouriteRecipe
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

    fun findAllFavouriteRecipes(): LiveData<List<FavouriteRecipe>> {
        return favouriteRecipeDao.getFavouriteRecipes()
    }

    suspend fun findRecipeById(recipeId: Long): FavouriteRecipe {
        return withContext(defaultDispatcher) {
            favouriteRecipeDao.getRecipeById(recipeId)
        }
    }

    suspend fun insertFavouriteRecipe(favouriteRecipe: FavouriteRecipe) {
        withContext(defaultDispatcher) {
            favouriteRecipeDao.insertFavouriteRecipe(favouriteRecipe)
            Log.d("FavouriteRecipeRepository", "insertFavouriteRecipe: $favouriteRecipe")
        }
    }

    suspend fun deleteFavouriteRecipe(recipeId: Long) {
        withContext(defaultDispatcher) {
            favouriteRecipeDao.deleteFavouriteRecipe(recipeId)
            Log.d("FavouriteRecipeRepository", "deleteFavouriteRecipe: $recipeId")
        }
    }

}