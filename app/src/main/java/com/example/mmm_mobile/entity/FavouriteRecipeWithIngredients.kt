package com.example.mmm_mobile.entity

import androidx.room.Embedded
import androidx.room.Relation

data class FavouriteRecipeWithIngredients(
    @Embedded val ingredient: RecipeIngredient,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "id"
    )
    val recipe: FavouriteRecipe
)