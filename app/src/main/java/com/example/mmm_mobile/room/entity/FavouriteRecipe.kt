package com.example.mmm_mobile.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.mmm_mobile.screens.RecipeDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.openapitools.client.models.Recipe
import org.openapitools.client.models.RecipeIngredientDTO

@Entity(tableName = "favourite_recipe")
@TypeConverters(Converters::class)
data class FavouriteRecipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val servings: Int,
    val image: ByteArray,
    val instructions: String,
    val kcalPerServing: Double,
    val totalTime: Int,
    val ingredients: List<Ingredient>
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
        if (totalTime != other.totalTime) return false
        return ingredients == other.ingredients
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + servings
        result = 31 * result + image.contentHashCode()
        result = 31 * result + instructions.hashCode()
        result = 31 * result + kcalPerServing.hashCode()
        result = 31 * result + totalTime
        result = 31 * result + ingredients.hashCode()
        return result
    }

    fun mapToRecipeTDetails() : RecipeDetails {
        return RecipeDetails(
            id = id,
            name = name,
            servings = servings,
            totalTime = totalTime,
            kcalPerServing = kcalPerServing,
            instructions = instructions,
            image = image,
            ingredients = ingredients.map { ingredient ->
                RecipeIngredientDTO(
                    name = ingredient.name,
                    amount = ingredient.amount,
                    unit = RecipeIngredientDTO.Unit.valueOf(ingredient.unit.name)
                )
            }
        )
    }
}


class Converters {
    @TypeConverter
    fun fromIngredientList(value: List<Ingredient>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIngredientList(value: String): List<Ingredient> {
        val gson = Gson()
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(value, type)
    }
}