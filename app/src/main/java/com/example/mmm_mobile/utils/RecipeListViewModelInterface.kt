package com.example.mmm_mobile.utils

import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.models.RecipeFilter

interface RecipeListViewModelInterface {
    var state: ScreenState<Recipe>


    fun loadNextItems()
    fun filterRecipes(filter: RecipeFilter)
}
