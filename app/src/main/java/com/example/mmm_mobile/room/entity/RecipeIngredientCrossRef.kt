package com.example.mmm_mobile.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeIngredientCrossRef(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeId: Long,
    val ingredientId: Long
)