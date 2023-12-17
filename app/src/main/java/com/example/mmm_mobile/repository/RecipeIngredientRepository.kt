import android.app.Application
import android.util.Log
import com.example.mmm_mobile.dao.RecipeIngredientDao
import com.example.mmm_mobile.db.RecipeDataBase
import com.example.mmm_mobile.entity.RecipeIngredient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    fun findAllRecipeIngredients(): Flow<List<RecipeIngredient>> {
        return recipeIngredientDao.getAll()
    }

    suspend fun insert(recipeIngredient: RecipeIngredient) {
        withContext(defaultDispatcher) {
            recipeIngredientDao.insertAll(recipeIngredient)
            Log.d("RecipeIngredientRepository", "insert: $recipeIngredient")
        }
    }

    suspend fun update(recipeIngredient: RecipeIngredient) {
        withContext(defaultDispatcher) {
            recipeIngredientDao.updateRecipeIngredient(recipeIngredient)
            Log.d("RecipeIngredientRepository", "update: $recipeIngredient")
        }
    }

    suspend fun delete(recipeIngredient: RecipeIngredient) {
        withContext(defaultDispatcher) {
            recipeIngredientDao.deleteIngredientsByRecipeId(recipeIngredient.recipeId)
            Log.d("RecipeIngredientRepository", "delete: $recipeIngredient")
        }
    }

}