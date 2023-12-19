package com.example.mmm_mobile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mmm_mobile.viewmodel.RecipeViewModel

@Composable
fun RecipeDetailScreen(recipeId: Long?) {

    val recipeViewModel: RecipeViewModel = viewModel()
    val recipe = recipeViewModel.findRecipeById(recipeId!!).observeAsState(initial = null)
    val recipeIngredients = recipeViewModel.findRecipeIngredientsByRecipeId(recipeId).observeAsState(initial = emptyList())



    Column {
        Text(text = recipeId.toString())
        recipe.value?.let { Text(text = it.name) }
        recipe.value?.let { Text(text = it.image) }
        recipe.value?.let { Text(text = it.instructions) }
        Text(text = recipe.value?.servings.toString())
        Text(text = recipe.value?.kcalPerServings.toString())
        Text(text = recipe.value?.id.toString())

        recipeIngredients.value.forEach {
            Text(text = it.name)
            Text(text = it.unit.name)
            Text(text = it.amount.toString())
            Text(text = it.ingredientId.toString())
            Text(text = it.recipeId.toString())
        }
    }
}