package com.example.mmm_mobile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RecipeDetailScreen(recipeId: Long?) {

    Text(text = "Recipe ID: $recipeId")
}