package com.example.mmm_mobile.room.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mmm_mobile.room.dao.FavouriteRecipeDao
import com.example.mmm_mobile.room.dao.RecipeIngredientDao
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.entity.Ingredient
import com.example.mmm_mobile.room.entity.RecipeIngredientCrossRef
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(
    entities = [FavouriteRecipe::class, Ingredient::class, RecipeIngredientCrossRef::class],
    version = 7
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
                            populateDatabase(database.favouriteRecipeDao(), database.recipeIngredientDao())
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
                    .setQueryCallback({ sql, bindArgs ->
                        Log.d("Room", "Executing SQL: $sql")
                        Log.d("Room", "With bindArgs: $bindArgs")
                    }, Executors.newSingleThreadExecutor())
                    .addCallback(roomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }



        suspend fun populateDatabase(favouriteRecipeDao: FavouriteRecipeDao, recipeIngredientDao: RecipeIngredientDao) {
//            val recipes = listOf(
//                FavouriteRecipe(1, "Pierogi z kapustą", 1, "https://picsum.photos/200/300", "Pierogi z kapustą", 100.0, 1),
//                FavouriteRecipe(2, "Pierogi z mięsem", 1, "https://picsum.photos/200/300", "Pierogi z mięsem", 100.0,2),
//                FavouriteRecipe(3, "Pierogi z serem", 2, "https://picsum.photos/200/300", "Pierogi z serem", 200.0,3),
//                FavouriteRecipe(4, "Pierogi z truskawkami", 3, "https://picsum.photos/200/300", "Pierogi z truskawkami", 300.0,4),
//                FavouriteRecipe(5, "Pierogi z jagodami", 4, "https://picsum.photos/200/300", "Pierogi z jagodami", 400.0,5),
//                FavouriteRecipe(6, "Pierogi z borówkami", 5, "https://picsum.photos/200/300", "Pierogi z borówkami", 500.0,6),
//                FavouriteRecipe(7, "Pierogi z malinami", 6, "https://picsum.photos/200/300", "Pierogi z malinami", 600.0,7),
//                )
//
//            recipes.forEach() {
//                favouriteRecipeDao.insertFavouriteRecipe(it)
//            }
//
//            val ingredients = listOf(
//                RecipeIngredient(1, "Kapusta", 500.0, IngredientUnit.G),
//                RecipeIngredient(2, "Ciasto", 1000.0, IngredientUnit.G),
//                RecipeIngredient(3, "Mięso", 500.0, IngredientUnit.ML),
//                RecipeIngredient(4, "Ser", 500.0, IngredientUnit.ML),
//                RecipeIngredient(5, "Truskawki", 500.0, IngredientUnit.ML),
//                RecipeIngredient(6, "Jagody", 500.0, IngredientUnit.ML),
//                RecipeIngredient(7, "Borówki", 500.0, IngredientUnit.ML),
//                RecipeIngredient(8, "Maliny", 500.0, IngredientUnit.ML),
//            )
//
//            ingredients.forEach() {
//                recipeIngredientDao.insertRecipeIngredient(it)
//            }
//
//            val recipeIngredientCrossRefs = listOf(
//                RecipeIngredientCrossRef(0,1, 1),
//                RecipeIngredientCrossRef(0,1, 2),
//                RecipeIngredientCrossRef(0,2, 3),
//                RecipeIngredientCrossRef(0,2, 2),
//                RecipeIngredientCrossRef(0,3, 4),
//                RecipeIngredientCrossRef(0,3, 2),
//                RecipeIngredientCrossRef(0,4, 5),
//                RecipeIngredientCrossRef(0,4, 2),
//                RecipeIngredientCrossRef(0,5, 6),
//                RecipeIngredientCrossRef(0,5, 2),
//                RecipeIngredientCrossRef(0,6, 7),
//                RecipeIngredientCrossRef(0,6, 2),
//                RecipeIngredientCrossRef(0,7, 8),
//                RecipeIngredientCrossRef(0,7, 2),
//            )
//
//            recipeIngredientCrossRefs.forEach() {
//                favouriteRecipeDao.insertRecipeIngredientCrossRef(it)
//            }
        }

    }
}