package com.example.mmm_mobile.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["recipeId", "ingredientId"])
data class RecipeIngredientCrossRef(
    val recipeId: Long,
    val ingredientId: Long
)