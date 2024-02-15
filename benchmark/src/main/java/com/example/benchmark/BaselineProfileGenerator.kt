package com.example.benchmark

import android.util.Log
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineRule = BaselineProfileRule()

    var counter = 0

    @Test
    fun generateBaselineProfile() = baselineRule.collect(
        packageName = "com.example.mmm_mobile",
        maxIterations = 10,
    ){
        pressHome()
        startActivityAndWait()
        Log.i("BaselineProfileGenerator", "Iteration: ${counter++}")


        login()

        pressMoreOptions()
        pressBack()

        pressSearch()
        searchScreenTest()

        productListScroll()
        pressBack()

        pressFAB()
        addProductScreenTest()
        pressBack()

        navigateToRecipeList()
        recipeListScroll()
        pressBack()

        pressFAB()
        addRecipeScreenTest()
        pressBack()

        scrollUpRecipeList()

        addFavoriteRecipes()

        navigateToFavoriteRecipes()
        favoriteRecipeListScroll()
        pressBack()
    }
}