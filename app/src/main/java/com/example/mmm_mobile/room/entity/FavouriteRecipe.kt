package com.example.mmm_mobile.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "favourite_recipe")
data class FavouriteRecipe(
    @PrimaryKey(autoGenerate = false) val id: Long = 0,
    val name: String,
    val servings: Int,
    val image: ByteArray,
    val instructions: String,
    val kcalPerServing: Double,
    val totalTime: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FavouriteRecipe

        if (id != other.id) return false
        if (name != other.name) return false
        if (servings != other.servings) return false
        if (!image.contentEquals(other.image)) return false
        if (instructions != other.instructions) return false
        if (kcalPerServing != other.kcalPerServing) return false
        return totalTime == other.totalTime
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + servings
        result = 31 * result + image.contentHashCode()
        result = 31 * result + instructions.hashCode()
        result = 31 * result + kcalPerServing.hashCode()
        result = 31 * result + totalTime
        return result
    }
}

data class RecipeWithIngredients(
    @Embedded val recipe: FavouriteRecipe,
    @Relation(
        parentColumn = "id",
        entity = Ingredient::class,
        entityColumn = "id",
        associateBy = Junction(
            value = RecipeIngredientCrossRef::class,
            parentColumn = "recipeId",
            entityColumn = "ingredientId"
        )
    )
    val ingredients: List<Ingredient>
)
