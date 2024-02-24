package com.example.mmm_mobile.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "recipe_ingredient")
data class Ingredient(
    @PrimaryKey(autoGenerate = false) val id: Long = 0,
    val name: String
)
    data class IngredientWithAmountAndUnit(
        @Embedded val ingredient: Ingredient,
        @Relation(
            parentColumn = "id",
            entityColumn = "ingredientId"
        )
        val crossRef: RecipeIngredientCrossRef
    )
