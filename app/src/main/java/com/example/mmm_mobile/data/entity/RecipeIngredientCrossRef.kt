package com.example.mmm_mobile.data.entity

import androidx.room.Entity
import androidx.room.Index
import com.example.mmm_mobile.models.IngredientUnit

@Entity(
    primaryKeys = ["recipeId", "ingredientId"],
    indices = [Index("ingredientId")]
)
data class RecipeIngredientCrossRef(
    val recipeId: Long,
    val ingredientId: Long,
    val amount: Double,
    val unit: IngredientUnit
)