import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mmm_mobile.dao.FavouriteRecipeDao
import com.example.mmm_mobile.db.RecipeDataBase
import com.example.mmm_mobile.entity.FavouriteRecipe
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

}