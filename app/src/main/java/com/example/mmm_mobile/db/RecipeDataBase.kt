package com.example.mmm_mobile.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mmm_mobile.dao.FavouriteRecipeDao
import com.example.mmm_mobile.dao.RecipeIngredientDao
import com.example.mmm_mobile.entity.FavouriteRecipe
import com.example.mmm_mobile.entity.IngredientUnit
import com.example.mmm_mobile.entity.RecipeIngredient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [FavouriteRecipe::class, RecipeIngredient::class],
    version = 1,
    exportSchema = true
)
abstract class RecipeDataBase : RoomDatabase() {

    abstract fun favouriteRecipeDao(): FavouriteRecipeDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao


    companion object {
        @Volatile
        private var INSTANCE: RecipeDataBase? = null

        @OptIn(DelicateCoroutinesApi::class)
        fun getDatabase(context: Context): RecipeDataBase {
            val roomDatabaseCallback = object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    INSTANCE?.let { database ->
                        GlobalScope.launch {
                            populateDatabase(database.favouriteRecipeDao())
                        }
                    }
                    Log.d("RecipeDataBase", "onCreate: ")
                }
            }

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDataBase::class.java,
                    "favourite_recipe_database"
                )
                    .addCallback(roomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }



        suspend fun populateDatabase(favouriteRecipeDao: FavouriteRecipeDao) {
            val recipes = listOf(
                FavouriteRecipe(0, "Pierogi z kapustą", 1, "https://picsum.photos/200/300", "Pierogi z kapustą", 100.0),
                FavouriteRecipe(0, "Pierogi z mięsem", 1, "https://picsum.photos/200/300", "Pierogi z mięsem", 100.0),
                FavouriteRecipe(0, "Pierogi z serem", 2, "https://picsum.photos/200/300", "Pierogi z serem", 200.0),
                FavouriteRecipe(0, "Pierogi z truskawkami", 3, "https://picsum.photos/200/300", "Pierogi z truskawkami", 300.0),
                FavouriteRecipe(0, "Pierogi z jagodami", 4, "https://picsum.photos/200/300", "Pierogi z jagodami", 400.0),
                FavouriteRecipe(0, "Pierogi z borówkami", 5, "https://picsum.photos/200/300", "Pierogi z borówkami", 500.0),
                FavouriteRecipe(0, "Pierogi z malinami", 6, "https://picsum.photos/200/300", "Pierogi z malinami", 600.0),

                )


            RecipeIngredient(0, "Kapusta", 500.0, IngredientUnit.G, 1)
            RecipeIngredient(0, "Ciasto", 1000.0, IngredientUnit.G, 1)
            RecipeIngredient(0, "Mięso", 500.0, IngredientUnit.ML, 2)
            RecipeIngredient(0, "Ser", 500.0, IngredientUnit.ML, 3)
            RecipeIngredient(0, "Truskawki", 500.0, IngredientUnit.ML, 4)
            RecipeIngredient(0, "Jagody", 500.0, IngredientUnit.ML, 5)
            RecipeIngredient(0, "Borówki", 500.0, IngredientUnit.ML, 6)
            RecipeIngredient(0, "Maliny", 500.0, IngredientUnit.ML, 7)


            recipes.forEach {
                favouriteRecipeDao.insertFavouriteRecipe(it)
            }
        }

    }
}