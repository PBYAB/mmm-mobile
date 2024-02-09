package com.example.mmm_mobile.room.entity

import androidx.room.Entity

@Entity(primaryKeys = ["recipeId", "ingredientId"])
data class RecipeIngredientCrossRef(
    val recipeId: Long,
    val ingredientId: Long,
    val amount: Double,
    val unit: IngredientUnit
)