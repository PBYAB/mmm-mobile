package com.example.mmm_mobile.di

import android.content.Context
import com.example.mmm_mobile.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.openapitools.client.apis.Class1AuthenticationApi
import org.openapitools.client.apis.ProductApi
import org.openapitools.client.apis.RecipeApi
import org.openapitools.client.apis.RecipeReviewApi
import org.openapitools.client.apis.UserApi
import org.openapitools.client.infrastructure.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideProductApi(): ProductApi {
        return ProductApi()
    }

    @Provides
    @Singleton
    fun provideRecipeApi(): RecipeApi {
        return RecipeApi()
    }

    @Provides
    @Singleton
    fun provideClass1AuthenticationApi(): Class1AuthenticationApi {
        return Class1AuthenticationApi()
    }

    @Provides
    @Singleton
    fun provideRecipeReviewApi(): RecipeReviewApi {
        return RecipeReviewApi()
    }

    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        return UserApi()
    }
}