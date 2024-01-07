import android.app.Application
import android.util.Log
import com.example.mmm_mobile.room.dao.RecipeIngredientDao
import com.example.mmm_mobile.room.db.RecipeDataBase
import com.example.mmm_mobile.room.entity.RecipeIngredient
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

    suspend fun insertRecipeIngredient(recipeIngredient: RecipeIngredient) {
        withContext(defaultDispatcher) {
            recipeIngredientDao.insertAll(recipeIngredient)
            Log.d("RecipeIngredientRepository", "insertRecipeIngredient: $recipeIngredient")
        }
    }

    suspend fun deleteRecipeIngredientByRecipeId(recipeId: Long) {
        withContext(defaultDispatcher) {
            recipeIngredientDao.deleteIngredientsByRecipeId(recipeId)
            Log.d("RecipeIngredientRepository", "deleteRecipeIngredientByRecipeId: $recipeId")
        }
    }


}