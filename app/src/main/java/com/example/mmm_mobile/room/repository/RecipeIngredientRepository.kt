import android.app.Application
import androidx.annotation.WorkerThread
import com.example.mmm_mobile.room.dao.RecipeIngredientDao
import com.example.mmm_mobile.room.db.RecipeDataBase
import com.example.mmm_mobile.room.entity.Ingredient
import com.example.mmm_mobile.room.entity.IngredientWithAmountAndUnit
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

    fun findAllIngredients() = recipeIngredientDao.getIngredients()

    @WorkerThread
    suspend fun getIngredientsForRecipe(recipeId: Long): List<IngredientWithAmountAndUnit> {
        return withContext(defaultDispatcher) {
            recipeIngredientDao.getIngredientsForRecipe(recipeId)
        }
    }

    @WorkerThread
    suspend fun insertIngredientIfNotExists(ingredient: Ingredient): Long {
        return withContext(defaultDispatcher) {
            val existingIngredient = recipeIngredientDao.getIngredientByName(ingredient.name)
            return@withContext existingIngredient?.id ?: recipeIngredientDao.insertRecipeIngredient(
                ingredient
            )
        }
    }

    @WorkerThread
    suspend fun deleteOrphanRecipeIngredients() {
        withContext(defaultDispatcher) {
            recipeIngredientDao.deleteOrphanRecipeIngredients()
        }
    }
}