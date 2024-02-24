package com.example.mmm_mobile.di

import android.content.Context
import com.example.mmm_mobile.data.dao.FavouriteRecipeDao
import com.example.mmm_mobile.data.dao.RecipeIngredientDao
import com.example.mmm_mobile.data.db.RecipeDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

    @InstallIn(SingletonComponent::class)
    @Module
    class DatabaseModule {

        @Singleton
        @Provides
        fun provideRecipeDatabase(@ApplicationContext context: Context): RecipeDataBase {
            return RecipeDataBase.getInstance(context)
        }

        @Provides
        fun provideFavouriteRecipeDao(appDatabase: RecipeDataBase): FavouriteRecipeDao {
            return appDatabase.favouriteRecipeDao()
        }

        @Provides
        fun provideRecipeIngredientDao(appDatabase: RecipeDataBase): RecipeIngredientDao {
            return appDatabase.recipeIngredientDao()
        }
    }
