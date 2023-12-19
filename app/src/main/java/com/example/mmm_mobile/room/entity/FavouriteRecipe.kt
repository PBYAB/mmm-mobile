package com.example.mmm_mobile.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_recipe")
data class FavouriteRecipe(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val servings: Int,
    val image: String,
    val instructions: String,
    val kcalPerServings: Double
)