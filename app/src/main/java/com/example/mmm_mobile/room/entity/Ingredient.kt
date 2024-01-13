package com.example.mmm_mobile.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_ingredient")
data class Ingredient(
    @PrimaryKey(autoGenerate = false) val id: Long = 0,
    val name: String,
    val amount: Double,
    val unit: IngredientUnit
)