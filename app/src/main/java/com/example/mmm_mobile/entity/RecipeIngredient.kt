package com.example.mmm_mobile.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipe_ingredient",
    foreignKeys = [ForeignKey(
        entity = FavouriteRecipe::class,
        parentColumns = ["id"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RecipeIngredient(
    @PrimaryKey(autoGenerate = true) val ingredientId: Long,
    val name: String,
    val amount: Double,
    val unit: IngredientUnit,
    val recipeId: Long
)