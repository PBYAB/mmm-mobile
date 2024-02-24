package com.example.mmm_mobile.data.db

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mmm_mobile.data.dao.FavouriteRecipeDao
import com.example.mmm_mobile.data.dao.RecipeIngredientDao
import com.example.mmm_mobile.data.entity.FavouriteRecipe
import com.example.mmm_mobile.data.entity.Ingredient
import com.example.mmm_mobile.data.entity.RecipeIngredientCrossRef
import java.util.concurrent.Executors

@Database(
    entities = [FavouriteRecipe::class, Ingredient::class, RecipeIngredientCrossRef::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3)
    ]
)
abstract class RecipeDataBase : RoomDatabase() {

    abstract fun favouriteRecipeDao(): FavouriteRecipeDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao



    companion object {
        @Volatile private var instance: RecipeDataBase? = null

        fun getInstance(context: Context): RecipeDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RecipeDataBase {
            return Room.databaseBuilder(context, RecipeDataBase::class.java, "favourite_recipe_database")
                .setQueryCallback({ sql, bindArgs ->
                    Log.d("Room", "Executing SQL: $sql")
                    Log.d("Room", "With bindArgs: $bindArgs")
                }, Executors.newSingleThreadExecutor())
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("RecipeDataBase", "onCreate: ")
                        }
                    }
                )
                .build()
        }
    }
}