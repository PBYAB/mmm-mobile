import android.app.Application
import com.example.mmm_mobile.dao.RecipeIngredientDao
import com.example.mmm_mobile.db.RecipeDataBase
import com.example.mmm_mobile.entity.RecipeIngredient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeIngredientRepository(
    application: Application,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val recipeIngredientDao: RecipeIngredientDao


    init {
        val recipeDataBase = RecipeDataBase.getDatabase(application)
        recipeIngredientDao = recipeDataBase.recipeIngredientDao()
    }

    suspend fun findRecipeIngredientsByRecipeId(recipeId: Long): List<RecipeIngredient>? {
        return withContext(defaultDispatcher) {
            recipeIngredientDao.getIngredientsByRecipeId(recipeId)
        }
    }


}